package org.example.bankdatabasesimulation;

import java.sql.Date;

public class Customer extends User {
    private int customerId;

    public Customer(int userId, int customerId, String accountPass, String fName, String lName, String email, String phoneNum, Date dob, String address) {
        super(userId, accountPass, fName, lName, email, phoneNum, dob, address);
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
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
