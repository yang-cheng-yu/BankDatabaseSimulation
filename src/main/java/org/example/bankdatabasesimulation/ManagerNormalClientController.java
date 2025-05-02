package org.example.bankdatabasesimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerNormalClientController implements Initializable {

    @FXML
    private Button depositButton;

    @FXML
    private TableView<?> managerTable;

    @FXML
    private TextField sendAmountTextField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField sendemailTextField;


    @FXML
    private Button displayTransactionButton;

    @FXML
    private TextField transactionAmountTextField;

    @FXML
    private Button withdrawButton;

    @FXML
    private Label accountBalanceLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateBalance();
    }


    @FXML
    void deposit(ActionEvent event) {
        double money = Integer.parseInt(transactionAmountTextField.getText());
        DatabaseHelper.deposit(DataSingleton.getInstance().getCurrentAccount().getAccountType(),money);
        updateBalance();
    }

    @FXML
    void send(ActionEvent event) {
        double money = Integer.parseInt(sendAmountTextField.getText());
        String email = sendemailTextField.getText();
        DatabaseHelper.transfer(DataSingleton.getInstance().getCurrentAccount().getAccountType(),money,email,AccountType.DEBIT);
        updateBalance();
    }

    @FXML
    void withdraw(ActionEvent event) {
        double money = Integer.parseInt(transactionAmountTextField.getText());
        DatabaseHelper.withdraw(DataSingleton.getInstance().getCurrentAccount().getAccountType(),money);
        updateBalance();
    }

    @FXML
    void displayTransactions(ActionEvent event) {

    }
    private void updateBalance(){
        double money = DataSingleton.getInstance().getCurrentAccount().getBalance();
        String temp =String.valueOf(money);
        String temp2 = temp + "$";
        accountBalanceLabel.setText(temp2);
    }
}

