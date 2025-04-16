package org.example.bankdatabasesimulation;
import java.sql.*;

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
            System.out.println("successfully connected");
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
                email TEXT UNIQUE NOT NULL,
                phoneNum TEXT NOT NULL,
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

    public static void insertCustomer(String accountPass, String fname, String lname,
                                      String email, String phonenum, String DOB, String address){
        String sql = """
                INSERT INTO users(accountPass,fname,lname,email,phonenum,DOB,address) VALUES(?,?,?,?,?,?,?);
                """;
        try {
             PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,accountPass);
            stmt.setString(2,fname);
            stmt.setString(3,lname);
            stmt.setString(4,email);
            stmt.setString(5,phonenum);
            stmt.setString(6,DOB);
            stmt.setString(7,address);
            stmt.executeUpdate();
            System.out.println("message");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createCustomerTrigger(){
        String sql = """
                CREATE TRIGGER TRG_CUS_USER_ADD
                AFTER INSERT ON users
                BEGIN
                    INSERT INTO customers(userId)
                    VALUES (NEW.userId);
                END;
                """;
        execute(sql,"Trigger created");
    }

    public static void display(){
        String sql = """
                SELECT * FROM users;
                SELECT * FROM customers;
                """;
        execute(sql,"message");
    }

    public static void selectPlainText()
    {
        String sql="SELECT * FROM users";
        StringBuilder builder=new StringBuilder();
        //StringBuilder is aclass used to build (or append) strings efficiently

        try{
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                String accountpass=rs.getString("accountPass");
                String fname=rs.getString("fname");
                String lname=rs.getString("lname");
                String email=rs.getString("email");
                String phonenum=rs.getString("phonenum");
                String DOB=rs.getString("DOB");
                String address=rs.getString("address");
                builder.append(String.format("accountpass: %s, fName: %s, lname: %s" +
                        " email: %s ",accountpass,fname,lname,email));
                System.out.println(builder);
            }

        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}



