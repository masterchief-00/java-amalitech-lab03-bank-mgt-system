package com.kwizera.javaamalilabs03bankmgtsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kwizera/javaamalilabs03bankmgtsystem/views/create_account_page.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Create account");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}