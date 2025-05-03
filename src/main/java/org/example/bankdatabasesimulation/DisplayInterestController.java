package org.example.bankdatabasesimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yearTable.setCellValueFactory(new PropertyValueFactory<>("year"));
        moneytable.setCellValueFactory(new PropertyValueFactory<>("money"));
    }


}


