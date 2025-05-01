package org.example.bankdatabasesimulation;

public class Customer extends User {
    private int customerId;

    public Customer(int userId, String accountPass, String fName, String lName, String email, String phoneNum, String DOB, String address, int customerId) {
        super();
        this.customerId = customerId;
    }

    public Customer(int userId, String fName, String lName, String email, String phoneNum, String dob, String address) {
        super();
    }
}
