package org.example.bankdatabasesimulation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
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
            accountTable.setItems(FXCollections.observableArrayList(DatabaseHelper.getEveryAccount()));
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
        List<Account> accounts = DatabaseHelper.getEveryAccount();
        accounts = accounts.stream()
                .filter(account -> account.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());

        accountTable.setItems(FXCollections.observableArrayList(accounts));
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
    void displayallUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayUsers.fxml"));
            Parent root = loader.load();

            DisplayUsersController controller = loader.getController();
            List<User> data = DatabaseHelper.getAllUsers();
            controller.setUsers(data);

            Scene scene = new Scene(root, 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) accountTable.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        List<Account> testing =  accountTable.getItems();
        List<Account> accounts = DatabaseHelper.getEveryAccount();
        testing = testing.stream()
                .sorted(Comparator.comparing(account -> account.getBalance()))
                .collect(Collectors.toList());

        accountTable.setItems(FXCollections.observableArrayList(testing));
    }

    public void displayUserTransaction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayTransactions.fxml"));
            Parent root = loader.load();

            int userId = Integer.parseInt(userIdTextBox.getText());

            DisplayTransactionController controller = loader.getController();
            List<Transaction> data = DatabaseHelper.getAllTransactions(userId);
            controller.setTransaction(data);

            Scene scene = new Scene(root, 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) accountTable.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Credentials Error");
            alert.setContentText("Please check your credentials");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    public void displayUserAccounts(ActionEvent actionEvent) {
        try {
            int userId = Integer.parseInt(userIdTextBox.getText());
            accountTable.setItems(FXCollections.observableArrayList(DatabaseHelper.getAllAccounts(userId)));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Credentials Error");
            alert.setContentText("Please check your credentials");
            alert.show();
        }
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

