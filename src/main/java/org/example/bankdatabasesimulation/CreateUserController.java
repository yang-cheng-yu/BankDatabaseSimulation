package org.example.bankdatabasesimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {

    @FXML
    private Label addressLabel;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button createUserButton;

    @FXML
    private Label createUserLabel;

    @FXML
    private RadioButton customerRadioButton;

    @FXML
    private TextField dateBirthTextField;

    @FXML
    private Label dobLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField fNameTextField;

    @FXML
    private Label fnameLabel;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField lNameTextField;

    @FXML
    private Label lnameLabel;

    @FXML
    private RadioButton managerRadioButton;

    @FXML
    private TextField passTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField phoneNumTextField;

    @FXML
    private ToggleGroup userToggleGroup;

    @FXML
    private Button langButton;

    private DataSingleton data;

    @FXML
    void createUser(ActionEvent event) {
        try {
            //toggle for either manager or customer
            Toggle userType = userToggleGroup.getSelectedToggle();
            if (userType == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Credentials Error");
                alert.setContentText("Please choose a user type");
                alert.show();
            }
            //get every text field
            String pass = passTextField.getText();
            String fname = fNameTextField.getText();
            String lname = lNameTextField.getText();
            String email = emailTextField.getText();
            String phonenum = phoneNumTextField.getText();
            long DOB = Long.parseLong(dateBirthTextField.getText());
            String address = addressTextField.getText();

            //check to see if any field is left empty and throw error
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
            //if selected radio button is customer then make a customer
            if (userType == customerRadioButton) {
                DatabaseHelper.insertCustomer(pass, fname, lname, email, phonenum, DOB, address);
                goBackToMainPage();
            } else {
                //else must be manager
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
    //go back method used to go back to the main page, this is used a lot in the code so it is often seen
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = DataSingleton.getInstance();

        String langText = (data.getLang().equals(Locale.CANADA)) ? "FR" : "EN";
        langButton.setText(langText);

        updateLang();
    }

    private void updateLang() {
        ResourceBundle rb = ResourceBundle.getBundle("messages", data.getLang());


        //for localisation
        createUserLabel.setText(rb.getString("createUser"));
        createUserButton.setText(rb.getString("createUser"));
        customerRadioButton.setText(rb.getString("customer"));
        managerRadioButton.setText(rb.getString("manager"));
        passwordLabel.setText(rb.getString("password") + " : ");
        fnameLabel.setText(rb.getString("fname") + " : ");
        lnameLabel.setText(rb.getString("lname") + " : ");
        emailLabel.setText(rb.getString("email") + " : ");
        phoneLabel.setText(rb.getString("phone") + " : ");
        dobLabel.setText(rb.getString("dob") + " : ");
        addressLabel.setText(rb.getString("address") + " : ");
        goBackButton.setText(rb.getString("goBack"));
    }

    @FXML
    void toggleLang(ActionEvent event) {
        data.setLang((data.getLang().equals(Locale.CANADA)) ? Locale.CANADA_FRENCH : Locale.CANADA);
        String langText = (langButton.getText().equals("EN")) ? "FR" : "EN";
        langButton.setText(langText);
        updateLang();
    }
}
