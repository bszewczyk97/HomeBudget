package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.DBConnection;
import main.model.Operation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainViewController {
    String clickedItem;
    String itemName;

    public MenuItem addOperation;
    public MenuItem addCategory;
    public MenuItem addPerson;

    public MenuItem editOperation;

    public MenuItem removeOperation;

    public MenuItem last7Days;
    public MenuItem thisMonth;
    public MenuItem lastMonth;

    public MenuItem predictionWeek;
    public MenuItem predictionMonth;
    public MenuItem predictionYear;

    public MenuBar menubar;
    public GridPane gridPane;
    @FXML
    public TableView<Operation> table;
    @FXML
    TableColumn<Operation, Date> date;
    @FXML
    TableColumn<Operation, String> product;
    @FXML
    TableColumn<Operation, Integer> amount;
    @FXML
    TableColumn<Operation, String> category;
    @FXML
    TableColumn<Operation, String> person;
    @FXML
    TableColumn<Operation, Double> cost;
    @FXML
    TableColumn<Operation, String> description;
    @FXML
    TableColumn<Operation, Boolean> done;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

//    String pattern = "MM-dd-yyyy";
//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


    public MainViewController() {

    }

    public MainViewController(String clickedItem, String itemName) {
        this.clickedItem = clickedItem;
        this.itemName = itemName;
    }

    ObservableList<String> items = FXCollections.observableArrayList();

    public void initialize() {
        FilterController filterController = new FilterController();
        gridPane = filterController.createAddOperationFormPane(gridPane);
        filterController.addUIControls(gridPane, this);
        prepareMenuItems();
        prepareTableColumns();
    }

    private void prepareMenuItems() {
        addEventHandling(addOperation, "../../resources/fxml/addOperation.fxml");
        addEventHandling(addCategory, "../../resources/fxml/addCategory.fxml");
        addEventHandling(addPerson, "../../resources/fxml/addPerson.fxml");
        addRemoveOperationPopup(removeOperation);
        addEditOperationPopup(editOperation);
    }

    private void addRemoveOperationPopup(MenuItem menuItem) {
        menuItem.setOnAction((event) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/removeOperation.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            RemoveOperationController controller = loader.<RemoveOperationController>getController();
            controller.setSelectedOperation(table.getSelectionModel().getSelectedItem());
        });
    }

    private void addEditOperationPopup(MenuItem menuItem) {
        menuItem.setOnAction((event) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/editOperation.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            EditOperationController controller = loader.<EditOperationController>getController();
            controller.setSelectedOperation(table.getSelectionModel().getSelectedItem());
        });
    }

    private void addEventHandling(MenuItem menuItem, String resource) {
        menuItem.setOnAction((event) -> {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
                root = (Parent)loader.load();
//                Stage stage = new Stage();
//                stage.setTitle(name);
//                stage.setScene(new Scene(root, width, height));
//                stage.show();

                // Hide this current window (if this is what you want)
                table.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        ObservableList<Operation> expens = getExpenses();
        this.table.setItems(expens);
    }

    private ObservableList<Operation> getExpenses() {
        DBConnection con = new DBConnection();

        ObservableList<Operation> expens = FXCollections.observableArrayList();
//        expens.addAll(isAdded("chleb", con));
        expens.add(createExpense());
        expens.add(createExpense());
        expens.add(createExpense());
        return expens;
    }

    private List<Operation> isAdded(String product, DBConnection con) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from operation where what=?";
        List<Operation> expens = Collections.emptyList();


        try {
            preparedStatement = con.getConn().prepareStatement(sql);
            preparedStatement.setString(1, product);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                expens.add(Operation.builder()
                                .date(resultSet.getDate("data").toString())
                                .product(resultSet.getString("product"))
                                .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expens;
    }

    private Operation createExpense() {
        return Operation.builder()
                .date(formatter.format(new Date()))
                .product("masło")
                .amount(2)
                .category("Shopping")
                .person("Maciej")
                .cost(9.5)
                .description("")
                .done(Boolean.TRUE)
                .build();
    }

}
