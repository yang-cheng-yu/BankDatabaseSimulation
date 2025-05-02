package org.example.bankdatabasesimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerController {

    @FXML
    private TableView<?> customerTable;

    @FXML
    private Button depositButton;

    @FXML
    private Button displayTransactionButton;

    @FXML
    private TextField sendAmountTextField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField sendemailTextField;

    @FXML
    private TextField transactionAmoundTextField;

    @FXML
    private Button withdrawButton;

    @FXML
    void deposit(ActionEvent event) {
        double money = Integer.parseInt(transactionAmoundTextField.getText());
        DatabaseHelper.deposit(DataSingleton.getInstance().getCurrentAccount().getAccountType(),money);
    }

    @FXML
    void displayTransactions(ActionEvent event) {

    }

    @FXML
    void send(ActionEvent event) {
        double money = Integer.parseInt(sendAmountTextField.getText());
        String email = sendemailTextField.getText();
        DatabaseHelper.transfer(DataSingleton.getInstance().getCurrentAccount().getAccountType(),money,email,AccountType.DEBIT);
    }

    @FXML
    void withdraw(ActionEvent event) {
        double money = Integer.parseInt(transactionAmoundTextField.getText());
        DatabaseHelper.withdraw(DataSingleton.getInstance().getCurrentAccount().getAccountType(),money);
    }

}
