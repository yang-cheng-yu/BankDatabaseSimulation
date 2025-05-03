package org.example.bankdatabasesimulation;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayTransactionController implements Initializable {

    @FXML
    private TableColumn<Transaction, Integer> accountIdCol;

    @FXML
    private TableColumn<Transaction, Date> dateCol;

    @FXML
    private TableColumn<Transaction, String> descriptionCol;

    @FXML
    private TableColumn<Transaction, Double> tranAmountCol;

    @FXML
    private TableColumn<Transaction, Integer> tranIdCol;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    void goBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("customer-select.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage window = (Stage) transactionTable.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tranIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        accountIdCol.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        tranAmountCol.setCellValueFactory(new PropertyValueFactory<>("tranAmount"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("tranDescription"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("tranDate"));
    }

    public void setTransaction(List<Transaction> transaction) {
        transactionTable.setItems(FXCollections.observableArrayList(transaction));
    }
}

