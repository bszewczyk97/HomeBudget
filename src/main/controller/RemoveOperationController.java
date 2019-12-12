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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.DBConnection;
import main.model.Operation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveOperationController {
    private Operation selectedOperation;
    private Window previousWindow;

    void setSelectedOperation(Operation selectedOperation) {
        this.selectedOperation = selectedOperation;
    }

    void setPreviousWindow(Window window) {
        this.previousWindow = window;
    }

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
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Do you want to remove selected operation?");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        Button submitButton = new Button("Remove");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        submitButton.setDefaultButton(true);
        gridPane.add(submitButton, 0, 2, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selectedOperation == null) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Removing Error!", "You need to chose any operation");
                    return;
                }
                Operation operation = selectedOperation;
                removeOperation(operation, gridPane, event);
            }
        });

    }

    private void removeOperation(Operation operation, GridPane gridPane, ActionEvent event) {
        DBConnection con = new DBConnection();
        PreparedStatement preparedStatement = null;
        int resultSet;
        String sql = "{call removeOperation(?)}";

        try {
            preparedStatement = con.getConn().prepareStatement(sql);

            preparedStatement.setInt(1, operation.getId());

            resultSet = preparedStatement.executeUpdate();
            switchMainView(event);
            showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Removing Successful!", "Operation was removed");

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

    private void switchMainView(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/sample.fxml"));
            root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Home Budget");
            stage.setScene(new Scene(root, 800, 500));
            stage.show();

            // Hide this current window (if this is what you want)
            ((Node) (event.getSource())).getScene().getWindow().hide();
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

    public void initialize() {
        Stage stage = new Stage();
        stage.setTitle("Remove operation");

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
