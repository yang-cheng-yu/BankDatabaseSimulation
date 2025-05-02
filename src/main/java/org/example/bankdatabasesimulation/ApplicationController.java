package org.example.bankdatabasesimulation;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private TextField emailBox;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button createCustomerButton;

    @FXML
    void createCustomer(MouseEvent event) {
        DatabaseHelper.insertCustomer("customer", "customer", "customer", "customer@example.com", "999-999-9999", System.currentTimeMillis(), "123 Dummy-Address st.");
        createCustomerButton.setVisible(false);
    }

    @FXML
    void login(ActionEvent event) {
        System.out.println("login");
        if (DatabaseHelper.login(emailBox.getText(), passwordBox.getText())) {
            System.out.println("if");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("customer-select.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                Stage window = (Stage) emailBox.getScene().getWindow();
                window.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("else");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Credentials Error");
            alert.setContentText("Please check your credentials");

            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSingleton.getInstance();
        new DatabaseHelper();

        DatabaseHelper.createUsersTable();
        DatabaseHelper.createAccountsTable();
        DatabaseHelper.createCustomersTable();
        DatabaseHelper.createManagersTable();
        DatabaseHelper.createTransactionTable();
    }
}
