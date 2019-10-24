package main.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.DBConnection;
import main.model.Expense;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

public class MainViewController {
    String clickedItem;
    String itemName;

    public MenuItem dupa;
    public MenuBar menubar;
    public ListView<String> list;
    @FXML public TableView<Expense> table;
    @FXML TableColumn<Expense, Date> date;
    @FXML TableColumn<Expense, String> what;
    @FXML TableColumn<Expense, String> category;
    @FXML TableColumn<Expense, String> who;
    @FXML TableColumn<Expense, Double> cost;


    public MainViewController(){

    }
    public MainViewController(String clickedItem, String itemName){
         this.clickedItem=clickedItem;
         this.itemName=itemName;
    }

    ObservableList<String> items = FXCollections.observableArrayList();

    public void initialize() {
        dupa.setOnAction(e -> {

                System.out.println("Program will now display line numbers");

        });

        Label label = new Label("Add op");
//        label.setOnMouseClicked(mouseEvent->{AddOperationController.display()});
        label.setOnMouseClicked((event) -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("../../resources/fxml/addOperation.fxml"));
                Stage stage = new Stage();
                stage.setTitle("My New Stage Title");
                stage.setScene(new Scene(root, 450, 450));
                stage.show();
                // Hide this current window (if this is what you want)
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("../resources/fxml/addOperation.fxml"));
//                fxmlLoader.setController(new AddOperationController());
//                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//                Stage stage = new Stage();
//                stage.setTitle("Add Operation");
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                System.out.println("Failed to create new Window.");
//            }
        });
        Menu addOperation = new Menu("", label);
        menubar.getMenus().add(addOperation);

        items.addAll("Shopping", "Sport", "Restaurants", "Health", "Entertainment", "Transportation", "Salary", "Travel", "Gifts");
        list.setItems(items);
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                new main.controller.Category(list.getSelectionModel().getSelectedItem());
            }
        });


        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        what.setCellValueFactory(new PropertyValueFactory<>("what"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        who.setCellValueFactory(new PropertyValueFactory<>("who"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        loadData();

    }

    private void loadData() {
        ObservableList<Expense> test = getExpenses();
        this.table.setItems(test);
    }

    public ObservableList<Expense> getExpenses(){
        DBConnection con = new DBConnection();
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        main.model.Category shopping = new main.model.Category("Shopping");
        expenses.add(new Expense(new Date(), "Mleko", shopping.getCategoryName(), "Basia", 6.73));
        expenses.add(new Expense(new Date(), "Chleb", shopping.getCategoryName(), "Basia", 2.73));
        return expenses;
    }

}
