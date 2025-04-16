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

        String url = "jdbc:sqlite:src/main/resources/database/data.db"; // path to your DB file

        try (Connection conn = DriverManager.getConnection(url)) {
            Class.forName("org.sqlite.JDBC");
            if (conn != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable(){

    }


}
