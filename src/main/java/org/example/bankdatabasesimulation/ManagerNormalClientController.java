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
        try {
            double money = Double.parseDouble(transactionAmountTextField.getText());
            DatabaseHelper.deposit(DataSingleton.getInstance().getCurrentAccount().getAccountType(), money);
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
            double money = Double.parseDouble(sendAmountTextField.getText());
            String email = sendemailTextField.getText();
            DatabaseHelper.transfer(DataSingleton.getInstance().getCurrentAccount().getAccountType(), money, email, AccountType.DEBIT);
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
            double money = Double.parseDouble(transactionAmountTextField.getText());
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

    private void updateBalance(){
        double money = DataSingleton.getInstance().getCurrentAccount().getBalance();
        String temp =String.valueOf(money);
        String temp2 = temp + "$";
        accountBalanceLabel.setText(temp2);
    }
}

