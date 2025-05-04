package org.example.bankdatabasesimulation;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DataSingleton {
    private ObservableList<User> users;
    private ObservableList<Account> accounts;
    private ObservableList<Transaction> transaction;
    private static DataSingleton instance;
    private static Connection connection;
    private User currentUser;
    private Account currentAccount;
    private Locale lang;

    private DataSingleton() {
    }

    public void loadLists(){

    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/database.db");
                System.out.println("successfully connected");
            } catch (SQLException e) {
                System.out.println("Connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

    public static synchronized DataSingleton getInstance() {
        if (instance == null)
            instance = new DataSingleton();
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public Locale getLang() {
        return lang;
    }

    public void setLang(Locale lang) {
        this.lang = lang;
    }
}