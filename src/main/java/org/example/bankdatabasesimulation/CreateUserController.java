package org.example.bankdatabasesimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateUserController {

    @FXML
    private TextField addressTextField;

    @FXML
    private Button createUserButton;

    @FXML
    private RadioButton customerRadioButton;

    @FXML
    private TextField dateBirthTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField fNameTextField;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField lNameTextField;

    @FXML
    private RadioButton managerRadioButton;

    @FXML
    private TextField phoneNumTextField;

    @FXML
    private ToggleGroup userToggleGroup;

    @FXML
    private TextField passTextField;

    @FXML
    void createUser(ActionEvent event) {
        try {
            Toggle userType = userToggleGroup.getSelectedToggle();
            if (userType == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Credentials Error");
                alert.setContentText("Please choose a user type");
                alert.show();
            }
            String pass = passTextField.getText();
            String fname = fNameTextField.getText();
            String lname = lNameTextField.getText();
            String email = emailTextField.getText();
            String phonenum = phoneNumTextField.getText();
            long DOB = Long.parseLong(dateBirthTextField.getText());
            String address = addressTextField.getText();

            if (pass.isEmpty()||fname.isEmpty()||lname.isEmpty()||email.isEmpty()||phonenum.isEmpty()||address.isEmpty()){
                System.err.println("Fill out every input field");
                javafx.application.Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Create User Failed");
                    alert.setHeaderText("Missing Input Fields");
                    alert.setContentText("Please fill out missing input fields to create user");
                    alert.showAndWait();
                });
            }
            if (userType == customerRadioButton) {
                DatabaseHelper.insertCustomer(pass, fname, lname, email, phonenum, DOB, address);
                goBackToMainPage();
            } else {
                DatabaseHelper.insertManager(pass, fname, lname, email, phonenum, DOB, address);
                goBackToMainPage();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        goBackToMainPage();
    }
    private void goBackToMainPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("app.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) addressTextField.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
