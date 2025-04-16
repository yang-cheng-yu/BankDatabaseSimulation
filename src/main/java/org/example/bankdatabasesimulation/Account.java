package org.example.bankdatabasesimulation;

public class Account {
    private int accountId;
    private int userId;
    private int typeId;
    private double balance;
    private Status status;

    public Account(int accountId, int userId, int typeId, double balance, Status status) {
        this.accountId = accountId;
        this.userId = userId;
        this.typeId = typeId;
        this.balance = balance;
        this.status = status;
    }
}
