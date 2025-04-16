package org.example.bankdatabasesimulation;

public class AccountType {
    private int AccTypeId;
    private AccountTypes AccType;
    private String AccTypeDescription;
    private double interestRate;

    public AccountType(int accTypeId, AccountTypes accType, String accTypeDescription, double interestRate) {
        AccTypeId = accTypeId;
        AccType = accType;
        AccTypeDescription = accTypeDescription;
        this.interestRate = interestRate;
    }
}
