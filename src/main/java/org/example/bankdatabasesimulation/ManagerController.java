package org.example.bankdatabasesimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ManagerController {


    @FXML
    private TextField accountIdTextBox;

    @FXML
    private TextField amountTextBox;

    @FXML
    private Button createAccountButton;

    @FXML
    private RadioButton creditRadioButton;

    @FXML
    private RadioButton debitRadioButton;

    @FXML
    private Button depositButton;

    @FXML
    private Button displayAccountsButton;

    @FXML
    private Button displayAllCustomerButton;

    @FXML
    private Button displayAllManagersButton;

    @FXML
    private Button displayAllUsersButton;

    @FXML
    private Button displayButton;

    @FXML
    private Button displayOnlyActiveAccountsButton;

    @FXML
    private Button displayTransactionButton;

    @FXML
    private Button displayYourAccountsButton;

    @FXML
    private Button displayYourTransactionsButton;

    @FXML
    private TextField emailTextBox;

    @FXML
    private Button freezeAccountButton;

    @FXML
    private TextField initialBalanceTextBox;

    @FXML
    private RadioButton investmentRadioButton;

    @FXML
    private TableView<?> managerTable;

    @FXML
    private TextField maxTextBox;

    @FXML
    private TextField minTextBox;

    @FXML
    private Button sendButton;

    @FXML
    private Button sortAccountsByBalanceButton;

    @FXML
    private Button sortUsersByNameButton;

    @FXML
    private TextField userIdTextBox;

    @FXML
    private Button withdrawButton;

    @FXML
    void createAccount(ActionEvent event) {

    }

    @FXML
    void deposit(ActionEvent event) {

    }

    @FXML
    void displayAccounts(ActionEvent event) {

    }

    @FXML
    void displayAllCustomers(ActionEvent event) {

    }

    @FXML
    void displayAllManagers(ActionEvent event) {

    }

    @FXML
    void displayOnlyActiveAccounts(ActionEvent event) {

    }

    @FXML
    void displayRange(ActionEvent event) {

    }

    @FXML
    void displayTransactions(ActionEvent event) {

    }

    @FXML
    void displayYourAccounts(ActionEvent event) {

    }

    @FXML
    void displayYourTransactions(ActionEvent event) {

    }

    @FXML
    void displayallUsers(ActionEvent event) {

    }

    @FXML
    void freezeAccount(ActionEvent event) {
        try {
            int accountId = Integer.parseInt(accountIdTextBox.getText());
            DatabaseHelper.freezeAccount(accountId);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Credentials Error");
            alert.setContentText("Please check your credentials");
            alert.show();
        }

    }

    @FXML
    void send(ActionEvent event) {

    }

    @FXML
    void sortAccountsByBalance(ActionEvent event) {

    }

    @FXML
    void sortUsersByName(ActionEvent event) {

    }

    @FXML
    void withdraw(ActionEvent event) {

    }

}

