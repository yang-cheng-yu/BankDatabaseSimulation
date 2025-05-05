package org.example.bankdatabasesimulation;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private TextField emailBox;

    @FXML
    private Button createUserButton;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button langButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    private DataSingleton data;


    @FXML
    void login(ActionEvent event) {
        //log
        System.out.println("login");
        //if login method works
        if (DatabaseHelper.login(emailBox.getText(), passwordBox.getText())) {
            //log
            System.out.println("if");
            try {
                //if current user is = to a customer create customer form
                if (DataSingleton.getInstance().getCurrentUser().getClass() == Customer.class) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("customer-select.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    Stage window = (Stage) emailBox.getScene().getWindow();
                    window.close();
                } else {
                    //else it must be a manager
                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("manager-select.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    Stage window = (Stage) emailBox.getScene().getWindow();
                    window.close();
                }
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
        data = DataSingleton.getInstance();

        String langText = (data.getLang().equals(Locale.CANADA)) ? "FR" : "EN";
        langButton.setText(langText);

        new DatabaseHelper();

        DatabaseHelper.createUsersTable();
        DatabaseHelper.createAccountsTable();
        DatabaseHelper.createCustomersTable();
        DatabaseHelper.createManagersTable();
        DatabaseHelper.createTransactionTable();

        updateLang();
    }

    private void updateLang() {
        ResourceBundle rb = ResourceBundle.getBundle("messages", data.getLang());

        createUserButton.setText(rb.getString("createUser"));
        loginLabel.setText(rb.getString("login"));
        emailLabel.setText(rb.getString("email") + " : ");
        passwordLabel.setText(rb.getString("password") + " : ");
        loginButton.setText(rb.getString("login"));
    }

    @FXML
    void createUser(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("CreateUser.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) emailBox.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toggleLang(ActionEvent event) {
        data.setLang((data.getLang().equals(Locale.CANADA)) ? Locale.CANADA_FRENCH : Locale.CANADA);
        String langText = (langButton.getText().equals("EN")) ? "FR" : "EN";
        langButton.setText(langText);
        updateLang();
    }
}
