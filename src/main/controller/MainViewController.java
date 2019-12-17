package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.DBConnection;
import main.model.Operation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public Label sumValue;
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
    private ObservableList<Operation> expens;

    public void initialize() {
        FilterController filterController = new FilterController();
        filterController.addUIControls(gridPane, this);
        prepareMenuItems();
        prepareTableColumns();
        gridPane = filterController.createFilterOperationFormPane(gridPane, expens);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Operation>() {
            @Override
            public void changed(ObservableValue<? extends Operation> observable, Operation oldValue, Operation newValue) {
                removeOperation.setDisable(false);
                addRemoveOperationPopup(removeOperation);

            }
        });

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
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            RemoveOperationController controller = loader.getController();
            controller.setPreviousWindow(table.getScene().getWindow());
            controller.setSelectedOperation(table.getSelectionModel().getSelectedItem());

        });
    }

    private void addEditOperationPopup(MenuItem menuItem) {
        menuItem.setOnAction((event) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/editOperation.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            EditOperationController controller = loader.getController();
            controller.setPreviousWindow(table.getScene().getWindow());
            controller.setSelectedOperation(table.getSelectionModel().getSelectedItem());

        });
    }

    private void addEventHandling(MenuItem menuItem, String resource) {
        menuItem.setOnAction((event) -> {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
                root = loader.load();
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
        expens = getExpenses();
        this.table.setItems(expens);
        setSumValue(expens);
    }

    public void setSumValue(ObservableList<Operation> expens) {
        Double sum = 0D;
        for (Operation operation : expens) {
            sum += operation.getCost();
        }

        this.sumValue.setText("Suma: " + sum.toString() + " zł");
    }

    private ObservableList<Operation> getExpenses() {
        DBConnection con = new DBConnection();

        ObservableList<Operation> expens = FXCollections.observableArrayList();
        expens.addAll(downloadOperations(con));
        return expens;
    }

//    class MyEventHandler implements EventHandler<MouseEvent> {
//
//        @Override
//        public void handle(MouseEvent t) {
//            TableCell c = (TableCell) t.getSource();
//            int index = c.getIndex();
//            System.out.println("id = " + expens.get(index).getId());
//        }
//    }


    private List<Operation> downloadOperations(DBConnection con) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select *, category.name as category_name," +
                " person.name as person_name from operation " +
                "LEFT JOIN category on category.id = category_id " +
                "LEFT JOIN person on person.id = person_id";
        List<Operation> expens = new ArrayList<>();


        try {
            preparedStatement = con.getConn().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                expens.add(Operation.builder()
                        .id(resultSet.getInt("id"))
                        .date(resultSet.getString("date"))
                        .product(resultSet.getString("product"))
                        .amount(resultSet.getInt("amount"))
                        .category(resultSet.getString("category_name"))
                        .person(resultSet.getString("person_name"))
                        .cost(resultSet.getDouble("cost"))
                        .description(resultSet.getString("description"))
                        .done(resultSet.getBoolean("done"))
                        .build());
            }
            return expens;
        } catch (SQLException e) {
            e.printStackTrace();
            expens.add(createExpense());
        } finally {
            return expens;
        }

    }

    private Operation createExpense() {
        return Operation.builder()
                .date(formatter.format(new Date()))
                .product("ERR")
                .amount(99999999)
                .category("ERR")
                .person("ERR")
                .cost(999999D)
                .description("ERR")
                .done(Boolean.TRUE)
                .build();
    }

}
