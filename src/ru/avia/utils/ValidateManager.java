package ru.avia.utils;

import javafx.scene.control.TextField;

public class ValidateManager {

    public static void makeNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[1-9][0-9]*")) {
                textField.setText(newValue.replaceAll("[^1-9]", ""));
            }
        });
    }

    public static void makeCharacter(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("/[a-zA-Zа-яА-Я ]+$/")) {
                textField.setText(newValue.replaceAll("[^a-zA-Zа-яА-Я ]", ""));
            }
        });
    }
}
