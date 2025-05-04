package org.example.bankdatabasesimulation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Bank");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DataSingleton.getInstance().setLang(Locale.CANADA);
        launch();
    }
}