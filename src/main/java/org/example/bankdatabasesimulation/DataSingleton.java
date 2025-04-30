package org.example.bankdatabasesimulation;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSingleton {
    private ObservableList<Manager> managers;
    private ObservableList<Customer> customers;
    private ObservableList<Account> accounts;
    private ObservableList<Transaction> transaction;
    private static DataSingleton instance;
    private static Connection connection;
    private User currentUser;

    private DataSingleton() {
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
}