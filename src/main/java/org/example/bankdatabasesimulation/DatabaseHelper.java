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
    public DatabaseHelper(){
        connection = DataSingleton.getConnection();
        createUsersTable();
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
                userType TEXT NOT NULL
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
                                      String email, String phonenum, String DOB, String address, userType userTypes){
        String sql = """
                INSERT INTO users(accountPass,fname,lname,email,phonenum,DOB,address,userType) VALUES(?,?,?,?,?,?,?,?);
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
            stmt.setObject(8,userTypes);
            stmt.executeUpdate();
            System.out.println("Customer Inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertManager(String accountPass, String fname, String lname,
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
            System.out.println("Manager Inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*public static void insertAccount(int typeID, double balance){


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
            System.out.println("Manager Inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/




    public static void createCustomerTrigger(){
        String sql = """
                CREATE TRIGGER TRG_CUS_USER_ADD
                AFTER INSERT ON users
                WHEN NEW.userType = 'CUSTOMER'
                BEGIN
                    INSERT INTO customers(userId)
                    VALUES (NEW.userId);
                END;
                """;
        execute(sql,"TRG_CUS_USER_ADD Trigger created");
    }

    public static void createManagerTrigger(){
        String sql = """
                CREATE TRIGGER TRG_MANAGER_USER_ADD
                AFTER INSERT ON users
                WHEN NEW.userType = 'MANAGER'
                BEGIN
                    INSERT INTO managers(userId)
                    VALUES (NEW.userId);
                END;
                """;
        execute(sql,"TRG_MANAGER_USER_ADD Trigger created");
    }




    public static void printAllUsers()
    {
        String sql="SELECT * FROM users";
        StringBuilder builder=new StringBuilder();
        //StringBuilder is a class used to build (or append) strings efficiently

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
                builder.append(String.format("accountpass: %s, fName: %s, lname: %s," +
                        " email: %s, phoneNumber: %s, DOB: %s, Address: %s",
                        accountpass,fname,lname,email,phonenum,DOB,address));
                System.out.println(builder);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


   /* public static boolean login(String email, String password) {
        String sql = "SELECT userId, fname, lname, email, phonenum, DOB, address , address, userType "
                + "FROM users WHERE email = ? AND accountPass = ?";

        try (PreparedStatement ps = DataSingleton
                .getInstance()
                .getConnection()
                .prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // build your User (or Customer/Manager) object
                    int userId      = rs.getInt("userId");
                    String fn       = rs.getString("fname");
                    String ln       = rs.getString("lname");
                    String em = rs.getString("email");
                    String pn = rs.getString("phonenum");
                    String DOB = rs.getString("DOB");
                    String ad = rs.getString("address");
                    String userType = rs.getString("userType");
                    User user;
                    if ("CUSTOMER".equals(userType)) {
                        user = new Customer(userId, fn, ln, em, pn, DOB, ad, userType, );
                    } else {
                        user = new Manager(userId, fn, ln, );
                    }
                    DataSingleton.getInstance().setCurrentUser(user);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return false;
    }
    */
    private static Status computeStatus(int typeId, double balance) {
        // suppose you have an enum or constants for typeId:
        // 1 = CREDIT, 2 = DEBIT, 3 = INVESTMENT
        if (balance < 0) {
            return Status.IN_DEBT;
        } else if (typeId == AccountTypes.INVESTMENT.ordinal()) {
            return Status.FROZEN;
        } else {
            return Status.ACTIVE;
        }
    }

//    public static void insertAccount(int typeId, double balance) {
//        User current = DataSingleton.getInstance().getCurrentUser();
//        if (current == null) {
//            System.err.println("No user is logged in. Cannot create account.");
//            return;
//        }
//
//        int userId = current.getUserId();
//        Status status = computeStatus(typeId, balance);
//
//        String sql = """
//        INSERT INTO accounts(userId, typeId, balance, status)
//        VALUES(?, ?, ?, ?);
//        """;
//
//        try (Connection conn = DataSingleton.getInstance().getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, userId);
//            ps.setInt(2, typeId);
//            ps.setDouble(3, balance);
//            ps.setString(4, status.name());
//
//            ps.executeUpdate();
//            System.out.println("Account created for user #" + userId
//                    + " with status " + status);
//        } catch (SQLException e) {
//            System.err.println("insertAccount failed: " + e.getMessage());
//        }
//    }
}



