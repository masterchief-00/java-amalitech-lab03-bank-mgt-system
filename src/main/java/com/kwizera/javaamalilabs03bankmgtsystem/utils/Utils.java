package com.kwizera.javaamalilabs03bankmgtsystem.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {

    // a utility method to generate account IDs
    public String generateAccountNumber(String accType) {
        String prefix = switch (accType) {
            case "Savings account" -> "SVG";
            case "Current account" -> "CURR";
            case "Fixed deposit account" -> "FXD";
            default -> "UNK";
        };

        int randomNum = (int) (Math.random() * 900) + 100;
        return "ACC-" + prefix + "-" + randomNum;
    }

    // a utility method to switch between pages
    public void switchScene(String fxmlFile, Button sourceButton, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) sourceButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    // a utility to render error messages
    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // utility method to render confirmation messages
    public void displayConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Completed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
