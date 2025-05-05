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
    public Button langButton;
    public Button displayAllAccountsButton;
    public Label managerLabel;
    public Label filterLabel;
    public Label maxLabel;
    public Label minLabel;
    public Label accountIdLabel;
    public Label userIdLabel;
    @FXML
    private HBox tableContainer;

    @FXML
    private TextField accountIdTextBox;

    @FXML
    private Button displayAccountsButton;

    @FXML
    private Button displayAllUsersButton;

    @FXML
    private Button displayButton;

    @FXML
    private Button displayOnlyActiveAccountsButton;

    @FXML
    private Button displayTransactionButton;

    @FXML
    private Button freezeAccountButton;


    @FXML
    private TextField maxTextBox;

    @FXML
    private TextField minTextBox;


    @FXML
    private Button sortAccountsByBalanceButton;



    @FXML
    private TextField userIdTextBox;


    private DataSingleton data;

    @FXML
    void displayAccounts(ActionEvent event) {
        try {
            //display every account
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
    void displayOnlyActiveAccounts(ActionEvent event) {
        //list of accounts, loading every account
        List<Account> accounts = DatabaseHelper.getEveryAccount();
        //using streams, filter the accounts list by only getting accounts with active status
        accounts = accounts.stream()
                .filter(account -> account.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());

        //set the table to accounts
        accountTable.setItems(FXCollections.observableArrayList(accounts));
    }

    @FXML
    void displayRange(ActionEvent event) {
        try {
            //get fields min and max
            double min = Double.parseDouble(minTextBox.getText());
            double max = Double.parseDouble(maxTextBox.getText());

            //list of every account
            List<Account> accounts = DatabaseHelper.getEveryAccount();
            //using streams, filter accounts by min and max range
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
            //to display all users, make new form with a User type table to display
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
            //get and parse accountid field from text box
            int accountId = Integer.parseInt(accountIdTextBox.getText());
            //call freeze method
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
        //get every account from the current table
        List<Account> testing =  accountTable.getItems();
        //using streams sort the table by comparing balance
        testing = testing.stream()
                .sorted(Comparator.comparing(account -> account.getBalance()))
                .collect(Collectors.toList());

        accountTable.setItems(FXCollections.observableArrayList(testing));
    }

    public void displayUserTransaction(ActionEvent actionEvent) {
        try {
            //load transaction form to display information
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayTransactions.fxml"));
            Parent root = loader.load();

            int userId = Integer.parseInt(userIdTextBox.getText());

            DisplayTransactionController controller = loader.getController();
            List<Transaction> data = DatabaseHelper.getAllTransactions(userId);
            controller.setName("manager-client.fxml");
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
            //get and parse field from text box
            int userId = Integer.parseInt(userIdTextBox.getText());
            //set accountTable to getAllAccounts of the userId
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

        data = DataSingleton.getInstance();



        String langText = (data.getLang().equals(Locale.CANADA)) ? "FR" : "EN";
        langButton.setText(langText);

        accountIdCol.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        accountTypeCol.setCellValueFactory(new PropertyValueFactory<>("accountTypeDescription"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void updateLang() {
        ResourceBundle rb = ResourceBundle.getBundle("messages", data.getLang());


        managerLabel.setText(rb.getString("managerClient"));
        filterLabel.setText(rb.getString("filter"));
        displayAccountsButton.setText(rb.getString("displayAcc"));
        displayTransactionButton.setText(rb.getString("displayTran"));
        displayAllUsersButton.setText(rb.getString("displayU"));
        displayAllAccountsButton.setText(rb.getString("displayAAcc"));
        displayOnlyActiveAccountsButton.setText(rb.getString("displayOAC"));
        freezeAccountButton.setText(rb.getString("freeze"));
        sortAccountsByBalanceButton.setText(rb.getString("sortAcc"));
        accountIdCol.setText(rb.getString("accId"));
        userIdCol.setText(rb.getString("userIdCol"));
        accountTypeCol.setText(rb.getString("accountTypeCol"));
        balanceCol.setText(rb.getString("balance"));
        statusCol.setText(rb.getString("status"));
        displayButton.setText(rb.getString("display"));

    }

    @FXML
    void toggleLang(ActionEvent event) {
        data.setLang((data.getLang().equals(Locale.CANADA)) ? Locale.CANADA_FRENCH : Locale.CANADA);
        String langText = (langButton.getText().equals("EN")) ? "FR" : "EN";
        langButton.setText(langText);
        updateLang();
    }
}

