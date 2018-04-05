package ru.avia.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.avia.utils.Hibernate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("Авторизация");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
