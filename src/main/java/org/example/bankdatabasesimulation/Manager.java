package org.example.bankdatabasesimulation;

public class Manager extends User {
    private int managerId;

    public Manager(int userId, String accountPass, String fName, String lName, String email, String phoneNum, String DOB, String address, int managerId) {
        this.userId = userId;
        this.accountPass = accountPass;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.DOB = DOB;
        this.address = address;
        this.managerId = managerId;
    }

    public Manager(int userId, String fName, String lName, String email, String phoneNum, String dob, String address) {
        super();
    }
}
