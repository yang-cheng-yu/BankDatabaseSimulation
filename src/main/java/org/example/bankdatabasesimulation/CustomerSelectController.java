package org.example.bankdatabasesimulation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerSelectController implements Initializable {

    @FXML
    private ListView<Account> accountListBox;

    @FXML
    private ComboBox<AccountType> accountTypeComboBox;

    @FXML
    private Button createAccountButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSingleton data = DataSingleton.getInstance();

        List<Account> accountList = DatabaseHelper.getAllAccounts();
        List<AccountType> typeList = new ArrayList<>();

        for (Account a : accountList) {
            AccountType temp = a.getAccountType();
            if (!typeList.contains(temp)) {
                typeList.add(temp);
            }
        }

        accountListBox.setItems(FXCollections.observableArrayList(accountList));
        accountListBox.setOnMouseClicked(mouseEvent -> {
            try {
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
        });
    }

    @FXML
    void createAccount(ActionEvent event) {
        AccountType type = accountTypeComboBox.getValue();


    }
}
