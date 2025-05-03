package org.example.bankdatabasesimulation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManagerController implements Initializable {

    public TableView<Account> accountTable;
    public TableColumn<Account, Integer> accountIdCol;
    public TableColumn<Account, Integer> userIdCol;
    public TableColumn<Account, AccountType> accountTypeCol;
    public TableColumn<Account, Double> balanceCol;
    public TableColumn<Account, Integer> statusCol;
    @FXML
    private HBox tableContainer;

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
    private TableView<Object> managerTable;

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
    void displayAccounts(ActionEvent event) {
        try {
            int userId = Integer.parseInt(accountIdTextBox.getText());
            DatabaseHelper.getAllAccounts(userId);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Credentials Error");
            alert.setContentText("Please check your credentials");
            alert.show();
        }
    }

    @FXML
    void displayAllCustomers(ActionEvent event) {
        try {
            DatabaseHelper.getAllCustomers();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("System Error");
            alert.setContentText("Error in the system.");
            alert.show();
        }
    }

    @FXML
    void displayAllManagers(ActionEvent event) {
        try {

            List<User> list = DatabaseHelper.getAllManagers();
            ObservableList<User> observableList = FXCollections.observableArrayList(list);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("System Error");
            alert.setContentText("Error in the system.");
            alert.show();
        }
    }


    @FXML
    void displayOnlyActiveAccounts(ActionEvent event) {

    }

    @FXML
    void displayRange(ActionEvent event) {
        try {
            double min = Double.parseDouble(minTextBox.getText());
            double max = Double.parseDouble(maxTextBox.getText());

            List<Account> accounts = DatabaseHelper.getEveryAccount();
            accounts = accounts.stream()
                            .filter(account -> account.getBalance() >= min && account.getBalance() <= max)
                                    .collect(Collectors.toList());


            accountTable.setItems(FXCollections.observableArrayList(accounts));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Credentials Error");
            alert.setContentText("Please check your credentials");
            alert.show();
        }
    }

    @FXML
    void displayTransactions(ActionEvent event) {
        try {
            int userId = Integer.parseInt(accountIdTextBox.getText());
            DatabaseHelper.getAllTransactions(userId);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Credentials Error");
            alert.setContentText("Please check your credentials");
            alert.show();
        }
    }

    @FXML
    void displayYourAccounts(ActionEvent event) {
        try {
            DatabaseHelper.getAllAccounts();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("System Error");
            alert.setContentText("Error in the system.");
            alert.show();
        }
    }

    @FXML
    void displayYourTransactions(ActionEvent event) {
        try {
            DatabaseHelper.getAllTransactions();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("System Error");
            alert.setContentText("Error in the system.");
            alert.show();
        }
    }

    @FXML
    void displayallUsers(ActionEvent event) {
        try {
            DatabaseHelper.getAllUsers();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("System Error");
            alert.setContentText("Error in the system.");
            alert.show();
        }
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
    void sortAccountsByBalance(ActionEvent event) {

    }

    @FXML
    void sortUsersByName(ActionEvent event) {

    }

    @FXML
    void viewInterest(ActionEvent event) {

    }

    public void displayUserTransaction(ActionEvent actionEvent) {
    }

    public void displayUserAccounts(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountIdCol.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        accountTypeCol.setCellValueFactory(new PropertyValueFactory<>("accountTypeDescription"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}

