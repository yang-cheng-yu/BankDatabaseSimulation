package org.example.bankdatabasesimulation;

public class AccountType {
    private int accTypeId;
    private String accTypeDescription;
    private double interestRate;

    private AccountType(int accTypeId, String accTypeDescription, double interestRate) {
        this.accTypeId = accTypeId;
        this.accTypeDescription = accTypeDescription;
        this.interestRate = interestRate;
    }

    //these are the only constructors for accountType
    public static final AccountType DEBIT = new AccountType(1,"Debit Account",0.002);
    public static final AccountType CREDIT = new AccountType(2,"Credit Account",0.01);
    public static final AccountType INVESTMENT = new AccountType(3,"Investment Account",0.08);

    public int getAccTypeId() {
        return accTypeId;
    }

    public String getAccTypeDescription() {
        return accTypeDescription;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return accTypeDescription;
    }

    public void setAccTypeId(int accTypeId) {
        this.accTypeId = accTypeId;
    }

    public void setAccTypeDescription(String accTypeDescription) {
        this.accTypeDescription = accTypeDescription;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
