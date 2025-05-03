package org.example.bankdatabasesimulation;

import java.sql.Date;

public class Manager extends User {
    private int managerId;

    public Manager(int userId, String accountPass, String fName, String lName, String email, String phoneNum, Date DOB, String address, int managerId) {
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


    public Manager(int userId, int managerId, String accountPass, String fName, String lName, String email, String phoneNum, Date dob, String address) {
        super(userId, accountPass, fName, lName, email, phoneNum, dob, address);
        this.managerId = managerId;
    }
    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", userId=" + userId +
                ", accountPass='" + accountPass + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", DOB=" + DOB +
                ", address='" + address + '\'' +
                '}';
    }
}
