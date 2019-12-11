package main.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.DBConnection;
import main.model.Expense;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainViewController {
    String clickedItem;
    String itemName;

    public MenuItem dupa;
    public MenuBar menubar;
    public ListView<String> list;
    @FXML
    public TableView<Expense> table;
    @FXML
    TableColumn<Expense, Date> date;
    @FXML
    TableColumn<Expense, String> product;
    @FXML
    TableColumn<Expense, Integer> amount;
    @FXML
    TableColumn<Expense, String> category;
    @FXML
    TableColumn<Expense, String> person;
    @FXML
    TableColumn<Expense, Double> cost;
    @FXML
    TableColumn<Expense, String> description;
    @FXML
    TableColumn<Expense, Boolean> done;


    public MainViewController() {

    }

    public MainViewController(String clickedItem, String itemName) {
        this.clickedItem = clickedItem;
        this.itemName = itemName;
    }

    ObservableList<String> items = FXCollections.observableArrayList();

    public void initialize() {
        dupa.setOnAction(e -> {
            System.out.println("Program will now display line numbers");
        });

        createMenu();
        createCategoryList();
        prepareTableColumns();
    }

    private void createMenu() {
        Label addOperationLabel = createLabel("Add operation", "../../resources/fxml/addOperation.fxml");
        Label addCategoryLabel = createLabel("Add category", "../../resources/fxml/addCategory.fxml");
        Label addPersonLabel = createLabel("Add person", "../../resources/fxml/addPerson.fxml");
        Label filterLabel = createLabel("Filter", "../../resources/fxml/addOperation.fxml");
        Menu addOperation = new Menu("", addOperationLabel);
        Menu addCategory = new Menu("", addCategoryLabel);
        Menu addPerson = new Menu("", addPersonLabel);
        Menu filter = new Menu("", filterLabel);
        menubar.getMenus().add(addOperation);
        menubar.getMenus().add(addCategory);
        menubar.getMenus().add(addPerson);
        menubar.getMenus().add(filter);
    }

    private Label createLabel(String name, String resource) {
        Label label = new Label(name);
//        addOperation.setOnMouseClicked(mouseEvent->{AddOperationController.display()});
        label.setOnMouseClicked((event) -> {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
                root = (Parent)loader.load();
//                Stage stage = new Stage();
//                stage.setTitle(name);
//                stage.setScene(new Scene(root, width, height));
//                stage.show();

                // Hide this current window (if this is what you want)
                ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return label;
    }

    private void prepareTableColumns() {
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        product.setCellValueFactory(new PropertyValueFactory<>("product"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        person.setCellValueFactory(new PropertyValueFactory<>("person"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        done.setCellValueFactory(new PropertyValueFactory<>("done"));
        loadData();
    }


    private void loadData() {
        ObservableList<Expense> test = getExpenses();
        this.table.setItems(test);
    }

    public ObservableList<Expense> getExpenses() {
        DBConnection con = new DBConnection();

        ObservableList<Expense> expenses = FXCollections.observableArrayList();
//        expenses.addAll(isAdded("chleb", con));
        expenses.add(createExpense());
        expenses.add(createExpense());
        expenses.add(createExpense());
        return expenses;
    }

    private List<Expense> isAdded(String product, DBConnection con) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from operation where what=?";
        List<Expense> expenses = Collections.emptyList();


        try {
            preparedStatement = con.getConn().prepareStatement(sql);
            preparedStatement.setString(1, product);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                expenses.add(Expense.builder()
                                .date(resultSet.getDate("data"))
                                .product(resultSet.getString("product"))
                                .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    private Expense createExpense() {
        return Expense.builder()
                .date(new Date())
                .product("masło")
                .amount(2)
                .category("Shopping")
                .person("Maciej")
                .cost(9.5)
                .description("")
                .done(Boolean.TRUE)
                .build();
    }

    private void createCategoryList() {
        items.addAll("Shopping", "Sport", "Restaurants", "Health", "Entertainment", "Transportation", "Salary", "Travel", "Gifts");
        list.setItems(items);
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                new main.controller.Category(list.getSelectionModel().getSelectedItem());
            }
        });
    }

}
