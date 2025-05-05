package org.example.bankdatabasesimulation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CustomerSelectController implements Initializable {

    public Label createNewLabel;
    public Button viewTransactionsButton;
    public Label yourAccountsLabel;
    public Label welcomeBackLabel;
    @FXML
    private ListView<Account> accountListBox;

    @FXML
    private ComboBox<AccountType> accountTypeComboBox;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button langButton;

    private DataSingleton data;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = DataSingleton.getInstance();

        refreshAccounts();
        String langText = (data.getLang().equals(Locale.CANADA)) ? "FR" : "EN";
        langButton.setText(langText);

        updateLang();
        List<AccountType> typeList = new ArrayList<>();
        typeList.add(AccountType.DEBIT);
        typeList.add(AccountType.CREDIT);
        typeList.add(AccountType.INVESTMENT);
        accountTypeComboBox.setItems(FXCollections.observableArrayList(typeList));

    }

    @FXML
    void viewAllTransactions(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayTransactions.fxml"));
            Parent root = loader.load();

            DisplayTransactionController controller = loader.getController();
            List<Transaction> data = DatabaseHelper.getAllTransactions();
            controller.setName("customer-select.fxml");
            controller.setTransaction(data);

            Scene scene = new Scene(root, 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) createAccountButton.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshAccounts() {
        List<Account> accountList = DatabaseHelper.getAllAccounts();
        accountListBox.setItems(FXCollections.observableArrayList(accountList));
    }

    @FXML
    void createAccount(ActionEvent event) {
        System.out.println("Create Account");
        AccountType type = accountTypeComboBox.getValue();
        DatabaseHelper.insertAccount(type, 0);
        refreshAccounts();
    }

    @FXML
    void loadAccount(MouseEvent event) {
        try {
            Account selected = accountListBox.getSelectionModel().getSelectedItem();
            DataSingleton data = DataSingleton.getInstance();
            data.setCurrentAccount(selected);


            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("customer-client.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) createAccountButton.getScene().getWindow();
            window.close();
        }
        catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Critical Error");
            alert.setContentText("The application will now exit");

            alert.showAndWait().ifPresent(response -> {
                Platform.exit();
            });
        }
    }

    private void updateLang() {
        ResourceBundle rb = ResourceBundle.getBundle("messages", data.getLang());

        yourAccountsLabel.setText(rb.getString("yourAccounts"));
        createNewLabel.setText(rb.getString("createNew"));
        welcomeBackLabel.setText(rb.getString("welcomeBack"));
        viewTransactionsButton.setText(rb.getString("viewAllAccountsTransactions"));
        createAccountButton.setText(rb.getString("createButton"));
        accountTypeComboBox.setPromptText(rb.getString("chooseType"));
    }

    @FXML
    void toggleLang(ActionEvent event) {
        data.setLang((data.getLang().equals(Locale.CANADA)) ? Locale.CANADA_FRENCH : Locale.CANADA);
        String langText = (langButton.getText().equals("EN")) ? "FR" : "EN";
        langButton.setText(langText);
        updateLang();
    }
}
