package org.example.bankdatabasesimulation;

public class AccountType {
    private int AccTypeId;
    private String AccTypeDescription;
    private double interestRate;

    private AccountType(int accTypeId, String accTypeDescription, double interestRate) {
        AccTypeId = accTypeId;
        AccTypeDescription = accTypeDescription;
        this.interestRate = interestRate;
    }

    public static final AccountType DEBIT = new AccountType(1,"Debit Account",0.002);
    public static final AccountType CREDIT = new AccountType(2,"Credit Account",0.01);
    public static final AccountType INVESTMENT = new AccountType(3,"Investment Account",0.8);

    public int getAccTypeId() {
        return AccTypeId;
    }

    public String getAccTypeDescription() {
        return AccTypeDescription;
    }

    public double getInterestRate() {
        return interestRate;
    }

}
