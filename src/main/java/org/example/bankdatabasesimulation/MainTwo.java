package org.example.bankdatabasesimulation;

import java.sql.Date;

public class MainTwo {
    public static void main(String[] args) {
//        new DatabaseHelper();
//        DatabaseHelper.deleteManagerTrigger();
//        DatabaseHelper.deleteCustomerTrigger();
        new DatabaseHelper();
        System.out.println(DatabaseHelper.getAllTransactions(1));
    }
}
