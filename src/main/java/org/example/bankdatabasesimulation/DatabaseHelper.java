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
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                insertManagerHelper(userId);
            }

            System.out.println("Customer Inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertCustomerHelper(int userId){
        String sql = """
                INSERT INTO customers(userId) VALUES(?);
                """;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,userId);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("Insert Customer Helper error: "+e.getMessage());
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

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                insertManagerHelper(userId);
            }
            System.out.println("Manager Inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertManagerHelper(int userId){
        String sql = """
                INSERT INTO managers(userId) VALUES(?);
                """;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,userId);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("Insert Manager Helpter error: "+e.getMessage());
        }
    }

    //DELETE THESE TRIGGERS BEFORE YOU WANT TO RUN THE PROGRAM
    //I IMPLEMENTED A WAY TO ADD INTO CUSTOMER AND MANAGER TABLES WITHOUT
    //THE NEED OF TRIGGERS, INSTEAD I USED HELPER METHODS

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


    public static boolean login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND accountPass = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    int userId = rs.getInt("userId");
                    String fName = rs.getString("fName");
                    String lName = rs.getString("lName");
                    String phoneNum = rs.getString("phoneNum");
                    String dob = rs.getString("DOB");
                    String address = rs.getString("Address");

                    String sql2 = "SELECT * FROM customers WHERE userId = ? LIMIT 1";
                    try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                        ps2.setInt(1, userId);

                        try (ResultSet rs2 = ps2.executeQuery()) {
                            if (rs2.next()) {
                                // It's a customer
                                Customer customer = new Customer(userId, fName, lName, email, phoneNum, dob, address);
                                DataSingleton.getInstance().setCurrentUser(customer);
                                return true;
                            } else {
                                // Otherwise, it's a manager
                                Manager manager = new Manager(userId, fName, lName, email, phoneNum, dob, address);
                                DataSingleton.getInstance().setCurrentUser(manager);
                                return true;
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
        return false;
    }

    private static Status computeStatus(AccountType accountType, double balance) {
        if (balance < 0) {
            return Status.IN_DEBT;
        } else if (accountType.equals(AccountType.INVESTMENT)) {
            return Status.FROZEN;
        } else {
            return Status.ACTIVE;
        }
    }

    public static void insertAccount(AccountType accountType, double balance) {
        User current = DataSingleton.getInstance().getCurrentUser();
        if (current == null) {
            System.err.println("No user is logged in. Cannot create account.");
            return;
        }

        int userId = current.getUserId();
        Status status = computeStatus(accountType, balance);

        String sql = """
        INSERT INTO accounts(userId, typeId, balance, status)
        VALUES(?, ?, ?, ?);
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setObject(2, accountType);
            ps.setDouble(3, balance);
            ps.setString(4, status.name());

            ps.executeUpdate();
            System.out.println("Account created for user #" + userId
                    + " with status " + status);
        } catch (SQLException e) {
            System.err.println("insertAccount failed: " + e.getMessage());
        }
    }
}



