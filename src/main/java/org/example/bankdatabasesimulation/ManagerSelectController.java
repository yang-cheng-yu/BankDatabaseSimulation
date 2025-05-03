package org.example.bankdatabasesimulation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerSelectController implements Initializable{

    @FXML
    private ListView<Account> accountListView;

    @FXML
    private ComboBox<AccountType> accountTypeComboBox;

    @FXML
    void createAccount(ActionEvent event) {
        System.out.println("Create Account");
        AccountType type = accountTypeComboBox.getValue();
        DatabaseHelper.insertAccount(type, 0);
        refreshAccounts();
    }

    @FXML
    void viewTransactions(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayTransactions.fxml"));
            Parent root = loader.load();

            DisplayTransactionController controller = loader.getController();
            List<Transaction> data = DatabaseHelper.getAllTransactions();
            controller.setTransaction(data);

            Scene scene = new Scene(root, 933, 619);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) accountListView.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void loadAccounts(MouseEvent event) {
        try {
            Account selected = accountListView.getSelectionModel().getSelectedItem();
            DataSingleton data = DataSingleton.getInstance();
            data.setCurrentAccount(selected);


            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("manager-normalClient.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) accountListView.getScene().getWindow();
            window.close();
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Critical Error");
            alert.setContentText("The application will now exit");

            alert.showAndWait().ifPresent(response -> {
                Platform.exit();
            });
        }
    }

    @FXML
    void manageSystem(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("manager-client.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) accountListView.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Critical Error");
            alert.setContentText("The application will now exit");

            alert.showAndWait().ifPresent(response -> {
                Platform.exit();
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSingleton data = DataSingleton.getInstance();

        refreshAccounts();

        List<AccountType> typeList = new ArrayList<>();
        typeList.add(AccountType.DEBIT);
        typeList.add(AccountType.CREDIT);
        typeList.add(AccountType.INVESTMENT);
        accountTypeComboBox.setItems(FXCollections.observableArrayList(typeList));
    }

    private void refreshAccounts() {
        List<Account> accountList = DatabaseHelper.getAllAccounts();
        accountListView.setItems(FXCollections.observableArrayList(accountList));
    }
}
