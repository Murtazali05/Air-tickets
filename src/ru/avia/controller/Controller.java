package ru.avia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

    protected void showDialog(Button btn, String path, String title){

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка! Не удалось открыть окно.");
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initStyle(StageStyle.DECORATED);
        dialogStage.setMinHeight(650);
        dialogStage.setMinWidth(1100);
        dialogStage.setScene(new Scene(root));

        dialogStage.show();

        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    protected FXMLLoader showModalityDialog(ActionEvent actionEvent, String path, String title, boolean resizable){

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка! Не удалось открыть окно.");
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.setResizable(resizable);
        dialogStage.setScene(new Scene(root));

        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());

        dialogStage.show();

        return loader;
    }
}
