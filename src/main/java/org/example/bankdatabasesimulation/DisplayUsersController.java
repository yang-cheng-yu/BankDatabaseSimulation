package org.example.bankdatabasesimulation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class DisplayUsersController implements Initializable {

    public TableView<User> userTable;
    public Button sortButton;
    public Button goBackButton;
    public Button filterMButton;
    public Button filterCButton;
    public Button resetButton;
    public Button langButton;
    public Label usersTitleLabel;
    @FXML
    private TableColumn<User, String> addressCol;

    @FXML
    private TableColumn<User, Date> dobCol;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, String> fnameCol;

    @FXML
    private Button goBack;

    @FXML
    private TableColumn<User, String> lnameCol;

    @FXML
    private TableColumn<User, String> passCol;

    @FXML
    private TableColumn<User, String> phonenumCol;

    @FXML
    private TableColumn<User, Integer> userIdCol;

    private DataSingleton data;

    @FXML
    void filterManagers(ActionEvent event) {
        List<User> testing =  DatabaseHelper.getAllManagers();
        userTable.setItems(FXCollections.observableArrayList(testing));
    }

    @FXML
    void resetTable(ActionEvent event) {
        userTable.setItems(FXCollections.observableArrayList(DatabaseHelper.getAllUsers()));
    }

    @FXML
    void sortByName(ActionEvent event) {
        List<User> testing =  userTable.getItems();
        testing = testing.stream()
                .sorted(Comparator.comparing(account -> account.getFName()))
                .collect(Collectors.toList());

        userTable.setItems(FXCollections.observableArrayList(testing));
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("manager-client.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 933, 619);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) userTable.getScene().getWindow();
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


        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("accountPass"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("FName"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("LName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phonenumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    public void setUsers(List<User> users) {
        userTable.setItems(FXCollections.observableArrayList(users));
    }

    public void filterCustomers(ActionEvent actionEvent) {
        List<User> testing =  DatabaseHelper.getAllCustomers();
        userTable.setItems(FXCollections.observableArrayList(testing));
    }
    private void updateLang() {
        ResourceBundle rb = ResourceBundle.getBundle("messages", data.getLang());


        usersTitleLabel.setText(rb.getString("userT"));
        goBackButton.setText(rb.getString("goBack"));
        sortButton.setText(rb.getString("sortName"));
        filterCButton.setText(rb.getString("filterC"));
        filterMButton.setText(rb.getString("filterM"));
        filterMButton.setText(rb.getString("filterM"));
        resetButton.setText(rb.getString("reset"));
        userIdCol.setText(rb.getString("userIdCol"));
        passCol.setText(rb.getString("password"));
        lnameCol.setText(rb.getString("lname"));
        fnameCol.setText(rb.getString("fname"));
        emailCol.setText(rb.getString("email"));
        phonenumCol.setText(rb.getString("phone"));
        dobCol.setText(rb.getString("dobb"));
        addressCol.setText(rb.getString("address"));

    }

    @FXML
    void toggleLang(ActionEvent event) {
        data.setLang((data.getLang().equals(Locale.CANADA)) ? Locale.CANADA_FRENCH : Locale.CANADA);
        String langText = (langButton.getText().equals("EN")) ? "FR" : "EN";
        langButton.setText(langText);
        updateLang();
    }
}
