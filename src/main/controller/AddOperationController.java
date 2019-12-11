package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class AddOperationController {

    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Add operation");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        Label dateLabel = new Label("Date: ");
        gridPane.add(dateLabel, 0,1);

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefHeight(40);
        gridPane.add(datePicker, 1,1);

        Label productLabel = new Label("Product : ");
        gridPane.add(productLabel, 0, 2);

        TextField productField = new TextField();
        productField.setPrefHeight(40);
        gridPane.add(productField, 1, 2);

        Label amountLabel = new Label("Amount : ");
        gridPane.add(amountLabel, 0, 3);

        Spinner<Integer> amountSpinner = new Spinner<>(0, 10, 1, 1);
        amountSpinner.setPrefHeight(40);
        gridPane.add(amountSpinner, 1, 3);

        Label categoryLabel = new Label("Category : ");
        gridPane.add(categoryLabel, 0, 4);

        ComboBox categoryCombox = new ComboBox();
        ObservableList<String> categories = FXCollections.observableArrayList();
        categories.addAll("Shopping", "Sport", "Restaurants", "Health", "Entertainment", "Transportation", "Salary", "Travel", "Gifts");
        categoryCombox.setItems(categories);
        categoryCombox.setPrefHeight(40);
        gridPane.add(categoryCombox, 1, 4);

        Label personLabel = new Label("Person : ");
        gridPane.add(personLabel, 0, 5);

        ComboBox personCombox = new ComboBox();
        ObservableList<String> persons = FXCollections.observableArrayList();
        persons.addAll("Basia", "Maciej");
        personCombox.setItems(persons);
        personCombox.setPrefHeight(40);
        gridPane.add(personCombox, 1, 5);

        Label costLabel = new Label("Cost : ");
        gridPane.add(costLabel, 0, 6);

        TextField costField = new TextField();
        costField.setPrefHeight(40);
        gridPane.add(costField, 1, 6);

        Label descriptionLabel = new Label("Description : ");
        gridPane.add(descriptionLabel, 0, 7);

        TextField descriptionField = new TextField();
        descriptionField.setPrefHeight(40);
        gridPane.add(descriptionField, 1, 7);

        Label doneLabel = new Label("Done : ");
        gridPane.add(doneLabel, 0, 8);

        ComboBox doneCombox = new ComboBox();
        ObservableList<String> truFalse = FXCollections.observableArrayList();
        truFalse.addAll("True", "False");
        doneCombox.setItems(truFalse);
        doneCombox.setPrefHeight(40);
        gridPane.add(doneCombox, 1, 8);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 9, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(datePicker.getValue()==null) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a date");
                    return;
                }
                if(productField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter product name");
                    return;
                }
                if(amountSpinner.getValue()==0) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter amount more than 0");
                    return;
                }
                if(categoryCombox.getSelectionModel().getSelectedItem()==null) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter category");
                    return;
                }
                if(personCombox.getSelectionModel().getSelectedItem()==null) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter person");
                    return;
                }
                if(costField.getText().isEmpty() || !costField.getText().matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter cost in numbers");
                    return;
                }

                if(doneCombox.getSelectionModel().getSelectedItem()==null) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter done value");
                    return;
                }
                switchMainView(event);
                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Adding Successful!", "Operation was added");
            }

            private void switchMainView(ActionEvent event) {
                Parent root;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/sample.fxml"));
                    root = (Parent)loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Home Budget");
                    stage.setScene(new Scene(root, 800, 500));
                    stage.show();

                    // Hide this current window (if this is what you want)
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void initialize(){
        Stage stage = new Stage();
        stage.setTitle("Add operation");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage
        stage.setScene(scene);

        stage.show();
    }

}
