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

    public static void main(String[] args) {

        
        DatabaseHelper.connect();
        DatabaseHelper.createUsersTable();
        DatabaseHelper.createCustomersTable();
        DatabaseHelper.insertCustomer("123","fname","lname","email","123","today","123 street");
        DatabaseHelper.display();
        DatabaseHelper.selectPlainText();
    }
}