package org.example.bankdatabasesimulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class DataSingleton {
    private HashMap<Integer, Manager> managers;
    private HashMap<Integer, Customer> customers;
    private HashMap<Integer, Account> accounts;
    private HashMap<Integer, Transaction> transaction;

    public static void main(String[] args) {

        DatabaseHelper.connect();
        DatabaseHelper.createUsersTable();
        DatabaseHelper.createCustomersTable();
        DatabaseHelper.insertCustomer("123","fname","lname","email","123","today","123 street");
        DatabaseHelper.display();
        DatabaseHelper.selectPlainText();

    }
}
