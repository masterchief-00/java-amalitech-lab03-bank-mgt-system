package com.kwizera.javaamalilabs03bankmgtsystem.controllers;

import com.kwizera.javaamalilabs03bankmgtsystem.models.Account;
import com.kwizera.javaamalilabs03bankmgtsystem.session.SessionManager;
import com.kwizera.javaamalilabs03bankmgtsystem.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;


public class MainMenuController {
    // formatter to help in formatting numbers to human friendly formats
    DecimalFormat formatter = new DecimalFormat("#,###.00");
    Utils utils = new Utils();

    @FXML
    private Button withDrawBtn, depositBtn, historyBtn, logoutBtn;

    @FXML
    private Label helloLabel, accNumberLabel, accBalanceLabel;

    @FXML
    private void onHistoryClicked() {
        try {
            // navigate to history page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kwizera/javaamalilabs03bankmgtsystem/views/history_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) depositBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("History");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onWithdrawClicked() {
        try {
            // navigate to withdraws page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kwizera/javaamalilabs03bankmgtsystem/views/withdraw_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) depositBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Withdraw");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDepositClicked() {
        try {
            // navigate to deposits page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kwizera/javaamalilabs03bankmgtsystem/views/deposit_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) depositBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Deposit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogoutClicked() throws IOException {
        // clear data and navigate back to login page
        SessionManager.clear();
        utils.switchScene("/com/kwizera/javaamalilabs03bankmgtsystem/views/create_account_page.fxml", logoutBtn, "Create account");
    }

    @FXML
    public void initialize() {
        Account acc = SessionManager.getAccount();
        if (acc == null) {
            // handle session initialization failure
            utils.displayError("Account details could not be loaded, try again later");
            return;
        } else {
            // fetch and display account details from the session
            String formattedBalance = formatter.format(acc.getBalance());

            helloLabel.setText("Hello, " + acc.getAccountHolder() + "!");
            accNumberLabel.setText("Account: " + acc.getAccountNumber());
            accBalanceLabel.setText("Balance: RWF " + formattedBalance);
        }
    }
}
