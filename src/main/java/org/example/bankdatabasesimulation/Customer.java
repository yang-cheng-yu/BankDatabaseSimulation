package org.example.bankdatabasesimulation;

public class Customer extends User {
    private int customerId;

    public Customer(int userId, String accountPass, String fName, String lName, String email, String phoneNum, String DOB, String address, int customerId) {
        this.userId = userId;
        this.accountPass = accountPass;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.DOB = DOB;
        this.address = address;
        this.customerId = customerId;
    }

    public Customer(int userId, String fName, String lName, String email, String phoneNum, String dob, String address) {
        super();
    }
}
