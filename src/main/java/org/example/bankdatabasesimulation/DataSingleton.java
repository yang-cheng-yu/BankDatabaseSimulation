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
    private Connection connection;
    private User currentUser;

    private DataSingleton() {
        connection = DatabaseHelper.connect();
    }

    public static synchronized DataSingleton getInstance() {
        if (instance == null) instance = new DataSingleton();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public static void main(String[] args) {

        
        DatabaseHelper.connect();
        DatabaseHelper.createUsersTable();
        DatabaseHelper.createCustomersTable();
        DatabaseHelper.insertCustomer("123","fname","lname","email","123","today","123 street", userType.CUSTOMER);
        DatabaseHelper.printAllUsers();
    }

}