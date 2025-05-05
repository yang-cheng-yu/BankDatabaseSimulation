package org.example.bankdatabasesimulation;

import javafx.collections.FXCollections;
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayInterestController implements Initializable {

    public Label interestRateTextLabel;
    public Button goBackButton;
    public Button langButton;
    public Label titleLabel;
    @FXML
    private Label interestRateLabel;

    @FXML
    private TableView<InterestObject> interestRateTable;

    @FXML
    private TableColumn<InterestObject, Double> moneytable;

    @FXML
    private TableColumn<InterestObject, Integer> yearTable;

    private DataSingleton data;

    //go back method
    @FXML
    void goBack(ActionEvent event) {
        try {
            //creates form for the past form
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("customer-client.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) interestRateTable.getScene().getWindow();
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


        yearTable.setCellValueFactory(new PropertyValueFactory<>("year"));
        moneytable.setCellValueFactory(new PropertyValueFactory<>("money"));
        double interest = DataSingleton.getInstance().getCurrentAccount().getAccountType().getInterestRate();
        double newInterest = interest * 100;
        String word = newInterest + "%";
        interestRateLabel.setText(word);
    }

    public void setInterest(List<InterestObject> interest) {
        interestRateTable.setItems(FXCollections.observableArrayList(interest));
    }
    private void updateLang() {
        ResourceBundle rb = ResourceBundle.getBundle("messages", data.getLang());


        titleLabel.setText(rb.getString("interestT"));
        interestRateTextLabel.setText(rb.getString("interest"));
        moneytable.setText(rb.getString("money"));
        yearTable.setText(rb.getString("year"));
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


