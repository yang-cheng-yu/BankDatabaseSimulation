package org.example.bankdatabasesimulation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/database.db";

    private static Connection connection;

    private static void execute(String sql, String message){
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            System.out.println(message);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // ===== CONNECT METHOD =====
    public static void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }


    public static void createUsersTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS users(
                userId INTEGER PRIMARY KEY AUTOINCREMENT,
                accountPass TEXT NOT NULL,
                fName TEXT NOT NULL,
                lName TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL
                phoneNum INTEGER UNIQUE NOT NULL,
                DOB DATE NOT NULL,
                Address TEXT NOT NULL
                );
                """;
        execute(sql, "Users table created");
    }

    public static void createCustomersTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS customers(
                customerId INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER NOT NULL,
                FOREIGN KEY (userId) REFERENCES users(userId)
                );
                """;
        execute(sql, "Customers table created");
    }

    public static void createManagersTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS managers(
                managerId INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER NOT NULL,
                FOREIGN KEY (userId) REFERENCES users(userId)
                );
                """;
        execute(sql, "Managers table created");
    }

    public static void createAccountsTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS accounts(
                accountId INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER NOT NULL,
                typeId INTEGER NOT NULL,
                balance DECIMAL(10,2) NOT NULL,
                status INTEGER NOT NULL,
                FOREIGN KEY (userId) REFERENCES users(userId)
                );
                """;
        execute(sql,"Accounts table created");
    }

    public static void createTransactionTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS transactions(
                transactionId INTEGER PRIMARY KEY AUTOINCREMENT,
                accountId INTEGER NOT NULL,
                tranAmount DECIMAL(10,2) NOT NULL,
                tranDescription TEXT NOT NULL,
                tranDate DATE NOT NULL
                FOREIGN KEY (accountId) REFERENCES accounts(accountId)
                );
                """;
        execute(sql, "Transaction table created");
    }

}


