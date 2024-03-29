package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.DBConnection;
import main.model.Category;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCategoryController {

    private GridPane createAddCategoryFormPane() {
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
        Label headerLabel = new Label("Add category");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        Label categorylabel = new Label("Category : ");
        gridPane.add(categorylabel, 0, 1);

        TextField categoryField = new TextField();
        categoryField.setPrefHeight(40);
        gridPane.add(categoryField, 1, 1);

        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        submitButton.setDefaultButton(true);
        gridPane.add(submitButton, 0, 2, 2, 1);
        GridPane.setHalignment(submitButton, HPos.LEFT);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefHeight(40);
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefWidth(100);
        gridPane.add(cancelButton, 1, 2, 2, 1);
        GridPane.setHalignment(cancelButton, HPos.RIGHT);
        GridPane.setMargin(cancelButton, new Insets(20, 0,20,0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(categoryField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter category name");
                    return;
                }
                Category category = new Category(categoryField.getText());
                addCategory(category, gridPane, event);
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchMainView(event);
            }
        });
    }

    private void addCategory(Category category, GridPane gridPane, ActionEvent event) {
        DBConnection con = new DBConnection();
        String err = "";
        PreparedStatement preparedStatement = null;
        int resultSet;
        String sql = "{call addCategory(?)}";

        try {
            preparedStatement = con.getConn().prepareStatement(sql);

            if (ifThisCategoryExists(category.getCategoryName(), con)) {
                err += "Given category already exists";
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", err);

            } else {
                preparedStatement.setString(1, category.getCategoryName());

                resultSet = preparedStatement.executeUpdate();
                switchMainView(event);
                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Adding Successful!", "Category was added");

            }

        } catch (SQLException ex) {
            System.out.println(ex);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            con.close();
        } finally {
            con.close();
        }

    }

    private boolean ifThisCategoryExists(String categoryName, DBConnection con)
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql="select * from category where name=?";


        try {
            preparedStatement = con.getConn().prepareStatement(sql);
            preparedStatement.setString(1, categoryName);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        stage.setTitle("Add category");

        // Create the registration form grid pane
        GridPane gridPane = createAddCategoryFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 500, 500);
        // Set the scene in primary stage
        stage.setScene(scene);

        stage.show();
    }
}
