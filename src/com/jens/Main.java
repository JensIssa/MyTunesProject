package com.jens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/View/MainWindow.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add("file:css/style.css");
        primaryStage.setTitle("MyTunes");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
    public static void main(String[] args) {
        Application.launch();
    }
}
