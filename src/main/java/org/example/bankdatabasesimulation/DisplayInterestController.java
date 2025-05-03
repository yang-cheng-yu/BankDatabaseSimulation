package org.example.bankdatabasesimulation;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayInterestController implements Initializable {

    @FXML
    private Label interestRateLabel;

    @FXML
    private TableView<InterestObject> interestRateTable;

    @FXML
    private TableColumn<InterestObject, Double> moneytable;

    @FXML
    private TableColumn<InterestObject, Integer> yearTable;

    @FXML
    void goBack(ActionEvent event) {
        try {
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
}


