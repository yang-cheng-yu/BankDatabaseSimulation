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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayTransactionController implements Initializable {

    public Button goBackButton;
    public Button langButton;
    public Label tranTitleLabel;
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

    private DataSingleton data;

    private String name;
    @FXML
    void goBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(name));
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
        data = DataSingleton.getInstance();



        String langText = (data.getLang().equals(Locale.CANADA)) ? "FR" : "EN";
        langButton.setText(langText);

        tranIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        accountIdCol.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        tranAmountCol.setCellValueFactory(new PropertyValueFactory<>("tranAmount"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("tranDescription"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("tranDate"));
    }

    public void setTransaction(List<Transaction> transaction) {
        transactionTable.setItems(FXCollections.observableArrayList(transaction));
    }

    private void updateLang() {
        ResourceBundle rb = ResourceBundle.getBundle("messages", data.getLang());


        tranTitleLabel.setText(rb.getString("interestT"));
        goBackButton.setText(rb.getString("goBack"));
        tranIdCol.setText(rb.getString("tranId"));
        accountIdCol.setText(rb.getString("accId"));
        tranAmountCol.setText(rb.getString("amountCol"));

    }

    @FXML
    void toggleLang(ActionEvent event) {
        data.setLang((data.getLang().equals(Locale.CANADA)) ? Locale.CANADA_FRENCH : Locale.CANADA);
        String langText = (langButton.getText().equals("EN")) ? "FR" : "EN";
        langButton.setText(langText);
        updateLang();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

