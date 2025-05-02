package org.example.bankdatabasesimulation;

public class Account {
    private int accountId;
    private int userId;
    private AccountType accountType;
    private double balance;
    private Status status;

    public Account(int accountId, int userId, AccountType accountType, double balance, Status status) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
    }
}
