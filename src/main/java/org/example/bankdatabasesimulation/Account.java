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

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountTypeDescription() {
        return accountType.toString();
    }
}
