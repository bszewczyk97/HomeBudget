package main.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddOperationController extends Application {

    Stage window;
    Scene mainScene;
    Scene addOperationScene;

    public AddOperationController(){

    }

//    public static void display(String title, String message) {
//        Stage window = new Stage();
//
//        //Block events to other windows
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle(title);
////        window.setMinWidth(250);
//        window.setHeight(500);
//        window.setWidth(500);
//
//        Label whatLabel = new Label("What");
//        Label categoryLabel = new Label("Category");
//        Label whoLabel = new Label("Who");
//        Label costLabel = new Label("Cost");
//
//        TextField what = new TextField();
//        ComboBox category = new ComboBox();
//        TextField who = new TextField();
//        TextField cost = new TextField();
//
//        Label label = new Label();
//        label.setText(message);
//        Button closeButton = new Button("Close this window");
//        closeButton.setOnAction(e -> window.close());
//
//        VBox layout = new VBox(10);
//        layout.getChildren().addAll(label, whatLabel, what, categoryLabel, category, whoLabel, who, costLabel, cost, closeButton);
//        layout.setAlignment(Pos.CENTER);
//
//        //Display window and wait for it to be closed before returning
//        Scene scene = new Scene(layout);
//        window.setScene(scene);
//        window.showAndWait();
//    }

    @Override
    public void start(Stage primaryStage) {
        window=primaryStage;

        Label whatLabel = new Label("What");
        Label categoryLabel = new Label("Category");
        Label whoLabel = new Label("Who");
        Label costLabel = new Label("Cost");

        Button addOperationButton = new Button("Add operation");
        addOperationButton.setOnAction(e -> window.setScene(mainScene));

        VBox addOperationLayout = new VBox(10);
        addOperationLayout.getChildren().addAll(whatLabel, categoryLabel, whoLabel, costLabel);
        addOperationScene = new Scene(addOperationLayout, 300, 300);

        window.setScene(mainScene);
        window.show();

    }

    public void initialize(){
        Label whatLabel = new Label("What");
        Label categoryLabel = new Label("Category");
        Label whoLabel = new Label("Who");
        Label costLabel = new Label("Cost");

        Button addOperationButton = new Button("Add operation");
        addOperationButton.setOnAction(e -> window.setScene(mainScene));

        VBox addOperationLayout = new VBox(10);
        addOperationLayout.getChildren().addAll(whatLabel, categoryLabel, whoLabel, costLabel);
        addOperationScene = new Scene(addOperationLayout, 300, 300);

        window.setScene(mainScene);
        window.show();
    }
}
