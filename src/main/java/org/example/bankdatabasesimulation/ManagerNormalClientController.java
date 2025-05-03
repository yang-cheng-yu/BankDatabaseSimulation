package org.example.bankdatabasesimulation;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerNormalClientController implements Initializable {

    public TableColumn tranIdTable;
    public TableColumn accountIdTable;
    public TableColumn tranAmountTable;
    public TableColumn tranDescTable;
    public TableColumn tranDateTable;
    @FXML
    private Button depositButton;

    @FXML
    private TableView<Transaction> managerTable;

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

        tranIdTable.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        accountIdTable.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        tranAmountTable.setCellValueFactory(new PropertyValueFactory<>("tranAmount"));
        tranDescTable.setCellValueFactory(new PropertyValueFactory<>("tranDescription"));
        tranDateTable.setCellValueFactory(new PropertyValueFactory<>("tranDate"));
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
        managerTable.setItems(FXCollections.observableArrayList(DatabaseHelper.getCurrentTransactions()));
    }
    private void updateBalance(){
        double money = DataSingleton.getInstance().getCurrentAccount().getBalance();
        String temp =String.valueOf(money);
        String temp2 = temp + "$";
        accountBalanceLabel.setText(temp2);
    }
}

