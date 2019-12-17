package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import main.DBConnection;
import main.model.Operation;
import main.model.OperationFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilterController {
    DBQueryService dbQueryService = new DBQueryService();

    GridPane createFilterOperationFormPane(GridPane gridPane) {
        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(0, 20, 0, 20));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        return gridPane;
    }

    void addUIControls(GridPane gridPane, MainViewController mainViewController) {
        Label dateLabel = new Label("Date range : ");
        gridPane.add(dateLabel, 0, 0);

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPrefHeight(20);
        startDatePicker.setPrefWidth(200);
        gridPane.add(startDatePicker, 0, 1);

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPrefHeight(20);
        endDatePicker.setPrefWidth(200);
        gridPane.add(endDatePicker, 1, 1);

        Label productLabel = new Label("Product : ");
        gridPane.add(productLabel, 0, 2);

        TextField productField = new TextField();
        productField.setPrefHeight(20);
        gridPane.add(productField, 1, 2);

        Label categoryLabel = new Label("Category : ");
        gridPane.add(categoryLabel, 0, 3);

        ComboBox categoryCombox = new ComboBox();
        categoryCombox.setItems(dbQueryService.getCategories());
        categoryCombox.setPrefHeight(20);
        gridPane.add(categoryCombox, 1, 3);

        Label personLabel = new Label("Person : ");
        gridPane.add(personLabel, 0, 4);

        ComboBox personCombox = new ComboBox();
        personCombox.setItems(dbQueryService.getPersons());
        personCombox.setPrefHeight(20);
        gridPane.add(personCombox, 1, 4);

        Label costLabel = new Label("Cost range: ");
        gridPane.add(costLabel, 0, 5);

        TextField minCostField = new TextField();
        minCostField.setPrefHeight(20);
        gridPane.add(minCostField, 0, 6);

        TextField maxCostField = new TextField();
        maxCostField.setPrefHeight(20);
        gridPane.add(maxCostField, 1, 6);

        Label descriptionLabel = new Label("Description : ");
        gridPane.add(descriptionLabel, 0, 7);

        TextField descriptionField = new TextField();
        descriptionField.setPrefHeight(20);
        gridPane.add(descriptionField, 1, 7);

        Label doneLabel = new Label("Done : ");
        gridPane.add(doneLabel, 0, 8);

        ComboBox doneCombox = new ComboBox();
        ObservableList<String> truFalse = FXCollections.observableArrayList();
        truFalse.addAll("True", "False");
        doneCombox.setItems(truFalse);
        doneCombox.setPrefHeight(20);
        gridPane.add(doneCombox, 1, 8);

        Button filterButton = new Button("Filter");
        filterButton.setPrefHeight(40);
        filterButton.setDefaultButton(true);
        filterButton.setPrefWidth(100);
        filterButton.setDefaultButton(true);
        gridPane.add(filterButton, 0, 9, 2, 1);
        GridPane.setHalignment(filterButton, HPos.CENTER);
        GridPane.setMargin(filterButton, new Insets(20, 0, 20, 0));


        filterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!(minCostField.getText().matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$") || minCostField.getText().matches("^$"))) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter min cost in numbers");
                    return;
                }
                if(!(maxCostField.getText().matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")|| minCostField.getText().matches("^$"))) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter max cost in numbers");
                    return;
                }
                OperationFilter operationFilter = OperationFilter.builder()
                        .startDate(startDatePicker.getValue().toString())
                        .endDate(endDatePicker.getValue().toString())
                        .product(productField.getText())
                        .category(categoryCombox.getValue().toString())
                        .person(personCombox.getValue().toString())
                        .minCost(Double.parseDouble(minCostField.getText()))
                        .maxCost(Double.parseDouble(maxCostField.getText()))
                        .description(descriptionField.getText())
                        .done(Boolean.parseBoolean(doneCombox.getValue().toString()))
                        .build();
                ObservableList<Operation> filteredOperations = getFilteredOperations(operationFilter);
                mainViewController.table.setItems(filteredOperations);
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

    private ObservableList<Operation> getFilteredOperations(OperationFilter operationFilter) {
        DBConnection con = new DBConnection();
        ObservableList<Operation> operations = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "{call filterOperations(?,?,?,?,?,?,?,?,?)}";

        try {
            preparedStatement = con.getConn().prepareStatement(sql);

            preparedStatement.setString(1, operationFilter.getStartDate());
            preparedStatement.setString(2, operationFilter.getEndDate());
            preparedStatement.setString(3, operationFilter.getProduct());
            preparedStatement.setInt(4, getCategoryId(operationFilter.getCategory()));
            preparedStatement.setInt(5, getPersonId(operationFilter.getPerson()));
            preparedStatement.setDouble(6, operationFilter.getMinCost());
            preparedStatement.setDouble(7, operationFilter.getMaxCost());
            preparedStatement.setString(8, operationFilter.getDescription());
            preparedStatement.setBoolean(9, operationFilter.isDone());

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                operations.add(Operation.builder()
                        .id(resultSet.getInt("id"))
                        .date(resultSet.getString("date"))
                        .product(resultSet.getString("product"))
                        .amount(resultSet.getInt("amount"))
                        .category(resultSet.getString("category"))
                        .person(resultSet.getString("person"))
                        .cost(resultSet.getDouble("cost"))
                        .description(resultSet.getString("description"))
                        .done(resultSet.getBoolean("done"))
                        .build());
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

        return operations;
    }

    Integer getCategoryId(String categoryName) {
        DBConnection con = new DBConnection();
        Integer categoryId=0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select category.id from category where categorty.name=?";

        try {
            preparedStatement = con.getConn().prepareStatement(sql);
            preparedStatement.setString(1, categoryName);

            resultSet = preparedStatement.executeQuery();
            categoryId = resultSet.getInt("id");

        } catch (SQLException ex) {
            System.out.println(ex);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            con.close();
        } finally {
            con.close();
        }

        return categoryId;
    }

    Integer getPersonId(String personName) {
        DBConnection con = new DBConnection();
        Integer personId=0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select person.id from person where person.name=?";

        try {
            preparedStatement = con.getConn().prepareStatement(sql);
            preparedStatement.setString(1, personName);

            resultSet = preparedStatement.executeQuery();
            personId = resultSet.getInt("id");

        } catch (SQLException ex) {
            System.out.println(ex);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            con.close();
        } finally {
            con.close();
        }

        return personId;
    }

}
