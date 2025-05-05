package org.example.bankdatabasesimulation;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ManagerNormalClientController implements Initializable {

    public TableColumn tranIdTable;
    public TableColumn accountIdTable;
    public TableColumn tranAmountTable;
    public TableColumn tranDescTable;
    public TableColumn tranDateTable;
    public Label accountBalanceTextLabel;
    public Label emailLabel;
    public Label amountLabel2;
    public Label amountLabel1;
    public Label managerLabel;
    public Button viewInterestButton;

    public Button langButton;
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

    private DataSingleton data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = DataSingleton.getInstance();

        updateBalance();


        String langText = (data.getLang().equals(Locale.CANADA)) ? "FR" : "EN";
        langButton.setText(langText);

        updateLang();

        tranIdTable.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        accountIdTable.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        tranAmountTable.setCellValueFactory(new PropertyValueFactory<>("tranAmount"));
        tranDescTable.setCellValueFactory(new PropertyValueFactory<>("tranDescription"));
        tranDateTable.setCellValueFactory(new PropertyValueFactory<>("tranDate"));
    }


    @FXML
    void deposit(ActionEvent event) {
        try {
            //get and parse text field input
            double money = Double.parseDouble(transactionAmountTextField.getText());
            //call deposit method
            DatabaseHelper.deposit(DataSingleton.getInstance().getCurrentAccount().getAccountType(), money);
            //update the balance label
            updateBalance();
        } catch (Exception e) {
            System.err.println("Input all fields" + e.getMessage());
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Transfer Failed");
                alert.setHeaderText("Missing Fields");
                alert.setContentText("Please input all missing fields to transfer");
                alert.showAndWait();
            });
        }
    }

    @FXML
    void send(ActionEvent event) {
        try {
            //get and parse money and email fields
            double money = Double.parseDouble(sendAmountTextField.getText());
            String email = sendemailTextField.getText();
            //call transfer method
            DatabaseHelper.transfer(DataSingleton.getInstance().getCurrentAccount().getAccountType(), money, email, AccountType.DEBIT);
            //update account balance label
            updateBalance();
        } catch (Exception e) {
            System.err.println("Input all fields" + e.getMessage());
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Transfer Failed");
                alert.setHeaderText("Missing Fields");
                alert.setContentText("Please input all missing fields to transfer");
                alert.showAndWait();
            });
        }
    }

    @FXML
    void withdraw(ActionEvent event) {
        try {
            //get and parse money field
            double money = Double.parseDouble(transactionAmountTextField.getText());
            //call withdraw method
            DatabaseHelper.withdraw(DataSingleton.getInstance().getCurrentAccount().getAccountType(), money);
            updateBalance();
        } catch (Exception e) {
            System.err.println("Input all fields" + e.getMessage());
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Transfer Failed");
                alert.setHeaderText("Missing Fields");
                alert.setContentText("Please input all missing fields to transfer");
                alert.showAndWait();
            });
        }
    }

    @FXML
    void displayTransactions(ActionEvent event) {
        managerTable.setItems(FXCollections.observableArrayList(DatabaseHelper.getCurrentTransactions()));
    }

    @FXML
    void viewInterest(ActionEvent event) {
        try {
            //create displayinterest form to load the interest table
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayInterest.fxml"));
            Parent root = loader.load();

            DisplayInterestController controller = loader.getController();
            List<InterestObject> data = DatabaseHelper.viewInterest(DataSingleton.getInstance().getCurrentAccount());
            controller.setInterest(data);

            Scene scene = new Scene(root, 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) accountBalanceLabel.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //method used for updating accountBalance label to display balance
    private void updateBalance(){
        double money = DataSingleton.getInstance().getCurrentAccount().getBalance();
        String temp =String.valueOf(money);
        String temp2 = temp + "$";
        accountBalanceLabel.setText(temp2);
    }
    private void updateLang() {
        ResourceBundle rb = ResourceBundle.getBundle("messages", data.getLang());


        amountLabel1.setText(rb.getString("amount"));
        amountLabel2.setText(rb.getString("amount"));
        withdrawButton.setText(rb.getString("withdraw"));
        depositButton.setText(rb.getString("deposit"));
        accountBalanceTextLabel.setText(rb.getString("accBal"));
        sendButton.setText(rb.getString("send"));
        sendemailTextField.setPromptText(rb.getString("emailL"));
        tranIdTable.setText(rb.getString("tranId"));
        accountIdTable.setText(rb.getString("accId"));
        tranAmountTable.setText(rb.getString("amountCol"));
        viewInterestButton.setText(rb.getString("viewInterest"));
        displayTransactionButton.setText(rb.getString("displayAllTransactions"));
        managerLabel.setText(rb.getString("manager"));
    }

    @FXML
    void toggleLang(ActionEvent event) {
        data.setLang((data.getLang().equals(Locale.CANADA)) ? Locale.CANADA_FRENCH : Locale.CANADA);
        String langText = (langButton.getText().equals("EN")) ? "FR" : "EN";
        langButton.setText(langText);
        updateLang();
    }
}

