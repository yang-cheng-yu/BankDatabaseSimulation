package org.example.bankdatabasesimulation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // ===== INITIALIZE METHOD =====
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
                accountTypeId INTEGER NOT NULL,
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
                insertCustomerHelper(userId);
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




    public static List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        String sql="SELECT * FROM users";
        try{
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                users.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static List<User> getAllCustomers(){
        List<User> customers = new ArrayList<>();

        String sql = """
                SELECT * FROM users WHERE userId IN (SELECT userId
                FROM Customers);
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                customers.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public static List<User> getAllManagers(){
        List<User> managers = new ArrayList<>();

        String sql = """
                SELECT * FROM users WHERE userId IN (SELECT userId
                FROM Managers);
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                managers.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return managers;
    }


    public static List<Transaction> getAllTransactions()
    {
        User current = DataSingleton.getInstance().getCurrentUser();
        List<Transaction> transactions = new ArrayList<>();

        String sql="SELECT accountId FROM accounts WHERE UserId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,current.getUserId());
            ResultSet rs = ps.executeQuery();
            int accountId1 = rs.getInt("accountId");
            int accountId2 = rs.getInt("accountId");
            int accountId3 = rs.getInt("accountId");

            String sql2= """
                    SELECT * FROM transactions WHERE accountId = ? OR ? OR ?;
                    """;
            try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                ps2.setInt(1,accountId1);
                ps2.setInt(2,accountId2);
                ps2.setInt(3,accountId3);

                ResultSet result = ps2.executeQuery();
                while(rs.next())
                {
                    transactions.add(new Transaction(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getDouble(3),
                            rs.getString(4),
                            rs.getDate(5)));
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return transactions;
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

        int accountTypeId = accountType.getAccTypeId();

        int userId = current.getUserId();
        Status status = computeStatus(accountType, balance);

        String sql = """
        INSERT INTO accounts(userId, accountTypeId, balance, status)
        VALUES(?, ?, ?, ?);
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, accountTypeId);
            ps.setDouble(3, balance);
            ps.setString(4, status.name());

            ps.executeUpdate();
            System.out.println("Account created for user #" + userId
                    + " with status " + status);
        } catch (SQLException e) {
            System.err.println("insertAccount failed: " + e.getMessage());
        }
    }

    public static void withdraw(AccountType accountType, double money){

        User current = DataSingleton.getInstance().getCurrentUser();
        if (current == null) {
            System.err.println("No user is logged in. Cannot create account.");
            return;
        }
        int userId = current.getUserId();
        int accountTypeInt = accountType.getAccTypeId();
        String sql = """
                SELECT balance, accountId FROM accounts
                        WHERE userId = ? AND accountTypeId = ?;
                """;

        try (PreparedStatement selectStmt = connection.prepareStatement(sql)) {
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, accountTypeInt);

            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                int accountId = rs.getInt("accountId");


                money = Math.abs(money);
                double newBalance = currentBalance - money;
                money *= -1;
                createTransaction(accountId,money,"Withdraw");

                String updateSql = """
                UPDATE accounts SET balance = ?
                WHERE userId = ? AND accountTypeId = ?;
                """;

                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setDouble(1, newBalance);
                    updateStmt.setInt(2, userId);
                    updateStmt.setInt(3, accountTypeInt);

                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Withdrawal successful. New balance: " + newBalance);
                    } else {
                        System.err.println("Withdrawal failed. No account found.");
                    }
                }
            } else {
                System.err.println("Account not found for user and type.");

            }
        } catch (SQLException e) {
            System.err.println("withdraw failed: " + e.getMessage());
        }
    }

    public static void deposit(AccountType accountType, double money){
        User current = DataSingleton.getInstance().getCurrentUser();
        if (current == null) {
            System.err.println("No user is logged in. Cannot create account.");
            return;
        }
        int userId = current.getUserId();
        int accountTypeInt = accountType.getAccTypeId();
        String sql = """
                SELECT balance, accountId FROM accounts
                        WHERE userId = ? AND accountTypeId = ?;
                """;

        try (PreparedStatement selectStmt = connection.prepareStatement(sql)) {
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, accountTypeInt);

            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                int accountId = rs.getInt("accountId");

                money = Math.abs(money);
                double newBalance = currentBalance + money;
                createTransaction(accountId,money,"Deposit");

                String updateSql = """
                UPDATE accounts SET balance = ?
                WHERE userId = ? AND accountTypeId = ?;
                """;

                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setDouble(1, newBalance);
                    updateStmt.setInt(2, userId);
                    updateStmt.setInt(3, accountTypeInt);

                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Deposit successful. New balance: " + newBalance);
                    } else {
                        System.err.println("Deposit failed. No account found.");
                    }
                }

            } else {
                System.err.println("Account not found for user and type.");
            }
        } catch (SQLException e) {
            System.err.println("Deposit failed: " + e.getMessage());
        }
    }
    private static void createTransaction(int accountId, double amount, String description) {
        String sql2 = """
                        INSERT INTO transactions(accountId, tranAmount, tranDescription, tranDate) VALUES(?,?,?,?);
                        """;

        try (PreparedStatement ps = connection.prepareStatement(sql2)) {
            ps.setInt(1,accountId);
            ps.setDouble(2,amount);
            ps.setString(3,description);
            ps.setDate(4,new Date(System.currentTimeMillis()));
        }
        catch (SQLException e) {
            System.err.println("Error with Inserting Transaction" + e.getMessage());
        }
    }

    public static void transfer(AccountType accountType, double money, String email, AccountType receivingAccountType) {
        money = Math.abs(money);
        withdraw(accountType,money);
        sendMoney(money,email,receivingAccountType);
    }
    private static void sendMoney(double money, String email, AccountType receivingAccountType) {
        String sql = """
                SELECT userId FROM users WHERE email = ?;
                """;
        try (PreparedStatement select = connection.prepareStatement(sql)) {
            select.setString(1,email);

            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("userId");

                String sql2 = """
                        SELECT accountId , balance FROM account WHERE userId = ? AND AccountTypeId = ?;
                        """;
                try (PreparedStatement select2 = connection.prepareStatement(sql2)){
                    select2.setInt(1,userId);
                    select2.setInt(2,receivingAccountType.getAccTypeId());

                    ResultSet result = select2.executeQuery();
                    if (result.next()) {
                        double balance = result.getDouble("balance");
                        int accountId = result.getInt("accountId");

                        balance += money;

                        String sql3 = """
                                UPDATE accounts SET balance = ?
                                WHERE userId = ? AND accountTypeId = ?;
                                """;
                        try (PreparedStatement select3 = connection.prepareStatement(sql3)){
                            select3.setDouble(1,balance);
                            select3.setInt(2,userId);
                            select3.setInt(3,receivingAccountType.getAccTypeId());
                            select3.executeUpdate();

                            createTransaction(accountId,money,"Deposit");

                            int rowsAffected = select3.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Money sent Successfuly");
                            } else {
                                System.err.println("Transaction failed");
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("error: " +e.getMessage());
        }
    }

}