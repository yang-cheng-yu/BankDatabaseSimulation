package org.example.bankdatabasesimulation;

import java.sql.Date;

public class Transaction {
    private int transactionId;
    private int accountId;
    private double tranAmount;
    private String tranDescription;
    private Date tranDate;

    public Transaction(int transactionId, int accountId, double tranAmount, String tranDescription, Date tranDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.tranAmount = tranAmount;
        this.tranDescription = tranDescription;
        this.tranDate = tranDate;
    }
}
