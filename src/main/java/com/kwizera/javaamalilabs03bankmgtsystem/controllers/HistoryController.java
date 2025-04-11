package com.kwizera.javaamalilabs03bankmgtsystem.controllers;

import com.kwizera.javaamalilabs03bankmgtsystem.models.Account;
import com.kwizera.javaamalilabs03bankmgtsystem.session.SessionManager;
import com.kwizera.javaamalilabs03bankmgtsystem.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HistoryController {
    Utils utils = new Utils();
    DecimalFormat formatter = new DecimalFormat("#,###.00");
    Account bankAccount = SessionManager.getAccount();

    @FXML
    private TextField entriesInput;

    @FXML
    private ListView<String> transactionList;

    @FXML
    private Label selectedEntriesLabel, errorLabel;

    @FXML
    private Button exitBtn, fetchHistoryBtn;

    @FXML
    private void onFetchClicked() {
        String entriesToFetchText = entriesInput.getText();
        ArrayList<String> history = new ArrayList<>();
        boolean isValid = true;

        if (entriesToFetchText.isEmpty()) {
            entriesInput.setText("0");
            isValid = false;
        }

        int entriesToFetch = 0;
        try {
            entriesToFetch = Integer.parseInt(entriesToFetchText);
            if (entriesToFetch < 0) {
                errorLabel.setText("Number of entries can't be below zero");
                errorLabel.setVisible(true);
                entriesInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid number for entries");
            errorLabel.setVisible(true);
            entriesInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        }

        if (!isValid) {
            selectedEntriesLabel.setText("No entries specified, displaying all transactions");
            history = bankAccount.printHistory();

            transactionList.getItems().clear();

            if (!history.isEmpty()) {
                transactionList.getItems().addAll(history);
            } else {
                transactionList.getItems().add("No entries found for this account");
            }
        } else {

            history = bankAccount.printHistory(entriesToFetch);

            transactionList.getItems().clear();

            if (!history.isEmpty()) {
                selectedEntriesLabel.setText("Displaying the last " + entriesToFetch + " transactions:");
                transactionList.getItems().addAll(history);
            } else {
                transactionList.getItems().add("No entries found for this account");
            }
        }
    }

    @FXML
    private void onCancelClicked() throws IOException {
        utils.switchScene("/com/kwizera/javaamalilabs03bankmgtsystem/views/main_menu_page.fxml", exitBtn,"Main menu");
    }

    @FXML
    public void initialize() {
        if (bankAccount == null) {
            utils.displayError("Account details could not be loaded, try again later");
            return;
        }
    }
}
