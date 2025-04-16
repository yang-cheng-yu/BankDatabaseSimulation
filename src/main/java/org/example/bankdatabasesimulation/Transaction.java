package org.example.bankdatabasesimulation;

public class Transaction {
    private int transactionId;
    private int accountId;
    private double tranAmount;
    private String tranDescription;
    private String tranDate;

    public Transaction(int transactionId, int accountId, double tranAmount, String tranDescription, String tranDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.tranAmount = tranAmount;
        this.tranDescription = tranDescription;
        this.tranDate = tranDate;
    }
}
