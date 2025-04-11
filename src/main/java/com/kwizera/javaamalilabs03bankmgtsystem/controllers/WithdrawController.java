package com.kwizera.javaamalilabs03bankmgtsystem.controllers;

import com.kwizera.javaamalilabs03bankmgtsystem.models.Account;
import com.kwizera.javaamalilabs03bankmgtsystem.models.CurrentAccount;
import com.kwizera.javaamalilabs03bankmgtsystem.models.FixedDepositAccount;
import com.kwizera.javaamalilabs03bankmgtsystem.models.SavingsAccount;
import com.kwizera.javaamalilabs03bankmgtsystem.session.SessionManager;
import com.kwizera.javaamalilabs03bankmgtsystem.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DecimalFormat;

public class WithdrawController {
    Utils utils = new Utils();
    DecimalFormat formatter = new DecimalFormat("#,###.00");
    Account bankAccount = SessionManager.getAccount();


    @FXML
    private Label currentBalLabel, maturityLabel, label1, errorLabel;

    @FXML
    private Button confirmBtn, cancelBtn;

    @FXML
    private TextField amountInput;

    @FXML
    private void onConfirmClicked() {
        String amountText = amountInput.getText();
        boolean isValid = true;

        // reset validation errors
        errorLabel.setText("");
        errorLabel.setVisible(false);
        amountInput.setStyle("");

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
                errorLabel.setText("Amount can't be below zero");
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

        if (isValid) {

            if (bankAccount.withdraw(amount)) {
                String formattedBalance = formatter.format(bankAccount.getBalance());

                confirmBtn.setDisable(true);
                utils.displayConfirmation("Amount withdrawn!");
                currentBalLabel.setText("Updated balance: RWF " + formattedBalance);
            } else {
                utils.displayError("Withdraw failed, the amount specified can not be withdrawn!");

                if (bankAccount instanceof SavingsAccount) {
                    errorLabel.setText("The specified amount would set the balance below minimum allowed balanced");
                    amountInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    errorLabel.setVisible(true);
                } else if (bankAccount instanceof CurrentAccount) {
                    errorLabel.setText("You have already used up the allowed overdraft");
                    amountInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    errorLabel.setVisible(true);
                } else if (bankAccount instanceof FixedDepositAccount) {
                    boolean accountMature = ((FixedDepositAccount) bankAccount).isMature();

                    if (!accountMature) {
                        errorLabel.setText("The previous deposit has not reached its maturity date");
                    } else {
                        errorLabel.setText("Insufficient funds");
                    }
                    amountInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    errorLabel.setVisible(true);

                }
            }

        }
        return;
    }

    @FXML
    private void onCancelClicked() throws IOException {
        utils.switchScene("/com/kwizera/javaamalilabs03bankmgtsystem/views/main_menu_page.fxml", cancelBtn, "Main menu");
    }

    @FXML
    private void initialize() {
        String formattedBalance = formatter.format(bankAccount.getBalance());
        if (bankAccount == null) {
            utils.displayError("Account details could not be loaded, try again later");
            return;
        } else {
            switch (bankAccount) {
                case CurrentAccount currentAccount -> {
                    String overdraftFormatted = formatter.format(currentAccount.getOverdraftLimit());
                    currentBalLabel.setText("Current balance: RWF " + formattedBalance + "\n[ with " + overdraftFormatted + " RWF in overdraft ]");
                }
                case FixedDepositAccount fixedDepositAccount -> {
                    long maturesMonths = fixedDepositAccount.maturesInMonths();
                    double interestAmount = fixedDepositAccount.calculateInterest();
                    double interestRate = fixedDepositAccount.getInterestRate();
                    double balancePlusInterest = bankAccount.getBalance() + interestAmount;

                    currentBalLabel.setText("Current balance: RWF " + formattedBalance);
                    amountInput.setText("" + balancePlusInterest);
                    amountInput.setEditable(false);

                    if (!fixedDepositAccount.isMature()) {
                        maturityLabel.setText("Matures for withdraw in " + maturesMonths + " month(s) plus " + formatter.format(interestAmount) + " RWF at " + interestRate + "% interest");
                        maturityLabel.setVisible(true);
                        confirmBtn.setDisable(true);

                        errorLabel.setText("Withdraw operations paused. The account hasn't reached maturity date.");
                        errorLabel.setVisible(true);
                    } else {
                        maturityLabel.setText("Mature for withdraw plus " + formatter.format(interestAmount) + " RWF at " + interestRate + "% interest");
                        maturityLabel.setVisible(true);
                    }
                }
                case SavingsAccount savingsAccount -> {
                    String miniBalFormatted = formatter.format(savingsAccount.getMinimumBalance());
                    currentBalLabel.setText("Current balance: RWF " + formattedBalance + "\n[ CONDITION: " + miniBalFormatted + " RWF minimum balance]");
                }
                default -> {
                    utils.displayError("Bank account details could not be loaded, please try again later");
                    return;
                }
            }

        }
    }
}
