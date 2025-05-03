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

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getTranDescription() {
        return tranDescription;
    }

    public void setTranDescription(String tranDescription) {
        this.tranDescription = tranDescription;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", tranAmount=" + tranAmount +
                ", tranDescription='" + tranDescription + '\'' +
                ", tranDate=" + tranDate +
                '}';
    }
}
