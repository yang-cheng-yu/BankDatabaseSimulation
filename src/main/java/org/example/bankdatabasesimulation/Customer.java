package org.example.bankdatabasesimulation;

import java.util.Date;

public class Customer extends User {
    private int customerId;

    public Customer(int userId, String accountPass, String fName, String lName, String email, Date phoneNum, String DOB, String address, int customerId) {
        super();
        this.customerId = customerId;
    }

    public Customer(int userId, String fName, String lName, String email, String phoneNum, Date dob, String address) {
        super();
    }
}
