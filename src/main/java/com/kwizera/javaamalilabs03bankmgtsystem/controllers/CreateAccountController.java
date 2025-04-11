package com.kwizera.javaamalilabs03bankmgtsystem.controllers;

import com.kwizera.javaamalilabs03bankmgtsystem.models.Account;
import com.kwizera.javaamalilabs03bankmgtsystem.models.CurrentAccount;
import com.kwizera.javaamalilabs03bankmgtsystem.models.FixedDepositAccount;
import com.kwizera.javaamalilabs03bankmgtsystem.models.SavingsAccount;
import com.kwizera.javaamalilabs03bankmgtsystem.session.SessionManager;
import com.kwizera.javaamalilabs03bankmgtsystem.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.time.LocalDateTime;

public class CreateAccountController {
    Utils utils = new Utils();
    String accId;

    @FXML
    private TextField accountHolderNamesInput;

    @FXML
    private TextField accountBalanceInput;

    @FXML
    private ComboBox<String> accountTypeInput;

    @FXML
    private Label accountNumberLabel;

    @FXML
    private Label errorLabel, errorAccTypeLabel, errorAccHolderLabel, errorBalLabel;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private void onConfirmBtnClicked() {
        String names = accountHolderNamesInput.getText();
        String accType = accountTypeInput.getValue();
        String balanceText = accountBalanceInput.getText().trim();
        boolean isValid = true;

        // reset validation errors
        errorLabel.setText("");
        errorLabel.setVisible(false);

        errorAccTypeLabel.setText("");
        errorAccTypeLabel.setVisible(false);

        errorAccHolderLabel.setText("");
        errorAccHolderLabel.setVisible(false);

        errorBalLabel.setText("");
        errorBalLabel.setVisible(false);


        if (invalidNames(names)) {
            errorAccHolderLabel.setText("Invalid names");
            errorAccHolderLabel.setVisible(true);
            accountHolderNamesInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        } else if (accType == null || accType.equals("None")) {
            errorAccTypeLabel.setText("Invalid account type");
            errorAccTypeLabel.setVisible(true);
            accountTypeInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        } else if (balanceText.isEmpty()) {
            errorBalLabel.setText("Invalid balance number");
            errorBalLabel.setVisible(true);
            accountBalanceInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        }

        double initBalance = 0;
        double MINIMUM_BALANCE = 50000;

        try {
            initBalance = Double.parseDouble(balanceText);
            if (initBalance < 0) {
                errorBalLabel.setText("Balance can't be below zero");
                errorBalLabel.setVisible(true);
                accountBalanceInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                isValid = false;
            } else if (accType != null && accType.equals("Savings account") && initBalance < MINIMUM_BALANCE) {
                errorBalLabel.setText("Minimum balance allowed is " + MINIMUM_BALANCE);
                errorBalLabel.setVisible(true);
                accountBalanceInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            errorBalLabel.setText("Invalid balance number");
            errorBalLabel.setVisible(true);
            accountBalanceInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        }

        if (!isValid) {
            return;
        } else {
            // if input is valid create the account
            Account bankAccount = null;
            double OVERDRAFT_LIMIT = 100000;
            long MATURITY_OFFSET_MONTHS = 6;
            double INTEREST_RATE = 8.5;

            switch (accType) {
                case "Current account" ->
                        bankAccount = new CurrentAccount(names, accId, initBalance, OVERDRAFT_LIMIT);
                case "Savings account" ->
                        bankAccount = new SavingsAccount(names, accId, initBalance, MINIMUM_BALANCE);
                case "Fixed deposit account" -> {
                    LocalDateTime maturityDate = LocalDateTime.now().plusMonths(MATURITY_OFFSET_MONTHS);
                    bankAccount = new FixedDepositAccount(names, accId, initBalance, maturityDate, INTEREST_RATE);
                }
                default -> {
                    errorLabel.setText("ERROR: account not created, please try again");
                    errorLabel.setVisible(true);
                    return;
                }
            }

            SessionManager.setAccount(bankAccount);
            utils.displayConfirmation("Account created successfully!");

            // navigate to main menu
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kwizera/javaamalilabs03bankmgtsystem/views/main_menu_page.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) accountHolderNamesInput.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Main menu");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onExitClicked() {
        // clear data and exit
        SessionManager.clear();
        Platform.exit();
    }

    @FXML
    private void initialize() {
        accountTypeInput.getItems().addAll("Current account", "Savings account", "Fixed deposit account");
        accountTypeInput.setValue("None");

        accountTypeInput.setOnAction(event -> {
            String selectedType = accountTypeInput.getValue();
            if (selectedType != null) {
                accId = utils.generateAccountNumber(selectedType);
                accountNumberLabel.setText("Account ID: " + accId);
                accountNumberLabel.setVisible(true);
            }
        });
    }

    private boolean invalidNames(String names) {
        return (!names.matches("[A-Za-z ]*") || names.length() < 2);
    }

}
