package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQueryService {

    ObservableList<String> getCategories() {
        DBConnection con = new DBConnection();
        ObservableList<String> categories = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select category.name from category";

        try {
            preparedStatement = con.getConn().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categories.add(resultSet.getString("name"));
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
        categories.add("");
        return categories;
    }

    ObservableList<String> getPersons() {
        DBConnection con = new DBConnection();
        ObservableList<String> persons = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sql = "select person.name from person";

        try {
            preparedStatement = con.getConn().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                persons.add(resultSet.getString("name"));
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
        persons.add("");
        return persons;
    }

}
