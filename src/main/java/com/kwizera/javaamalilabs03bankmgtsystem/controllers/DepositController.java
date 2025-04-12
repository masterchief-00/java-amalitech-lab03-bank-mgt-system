package com.kwizera.javaamalilabs03bankmgtsystem.controllers;

import com.kwizera.javaamalilabs03bankmgtsystem.models.Account;
import com.kwizera.javaamalilabs03bankmgtsystem.models.FixedDepositAccount;
import com.kwizera.javaamalilabs03bankmgtsystem.session.SessionManager;
import com.kwizera.javaamalilabs03bankmgtsystem.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.text.DecimalFormat;

public class DepositController {
    Utils utils = new Utils();
    DecimalFormat formatter = new DecimalFormat("#,###.00");
    Account bankAccount = SessionManager.getAccount();

    @FXML
    private Label currentBalLabel, label1, errorLabel;

    @FXML
    private Button confirmBtn, cancelBtn;

    @FXML
    private TextField amountInput;

    @FXML
    private void onConfirmClicked() {
        String amountText = amountInput.getText();
        boolean isValid = true;

        // input validation
        if (amountText.isEmpty()) {
            errorLabel.setText("Invalid number for amount");
            errorLabel.setVisible(true);
            amountInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        }

        double amount = 0;
        try {
            amount = Double.parseDouble(amountText);
            if (amount < 0) {
                errorLabel.setText("Deposit amount can't be below zero");
                errorLabel.setVisible(true);
                amountInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid number for amount");
            errorLabel.setVisible(true);
            amountInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        }


        if (isValid) { // if input is valid, deposit amount
            if (bankAccount instanceof FixedDepositAccount) {
                if (((FixedDepositAccount) bankAccount).isMature()) {
                    bankAccount.deposit(amount);
                    String formattedBalance = formatter.format(bankAccount.getBalance());
                    confirmBtn.setDisable(true);
                    utils.displayConfirmation("Amount deposited!");
                    currentBalLabel.setText("Updated balance: RWF " + formattedBalance);
                } else {
                    // block deposits on fixed deposit account yet to reach maturity date
                    utils.displayError("Deposit and withdraw operations are paused for this account until its maturity date.");
                    return;
                }
            } else { // allow deposits for other accounts types
                bankAccount.deposit(amount);

                String formattedBalance = formatter.format(bankAccount.getBalance());
                confirmBtn.setDisable(true);
                utils.displayConfirmation("Amount deposited!");
                currentBalLabel.setText("Updated balance: RWF " + formattedBalance);
            }
        }
        return;
    }

    @FXML
    private void onCancelClicked() throws IOException {
        // on cancel, navigate back to main menu
        utils.switchScene("/com/kwizera/javaamalilabs03bankmgtsystem/views/main_menu_page.fxml", cancelBtn, "Main menu");
    }

    @FXML
    private void initialize() {
        String formattedBalance = formatter.format(bankAccount.getBalance());
        if (bankAccount == null) {
            // handle failed session initialization
            utils.displayError("Account details could not be loaded, try again later");
            return;
        } else {
            currentBalLabel.setText("Current balance: RWF " + formattedBalance);

            if (bankAccount instanceof FixedDepositAccount) {

                // block deposit attempts on fixed deposit accounts yet to reach maturity dates
                if (!((FixedDepositAccount) bankAccount).isMature()) {
                    confirmBtn.setDisable(true);
                    amountInput.setDisable(true);
                    errorLabel.setText("Deposit operations on this account have been paused. The previous deposit hasn't reached its maturity date.");
                    errorLabel.setVisible(true);
                }
            }

        }
    }
}
