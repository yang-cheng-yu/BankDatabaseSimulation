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

    @Override
    public String toString() {
        return accountType +  " " + userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public Status getStatus() {
        return status;
    }

}
