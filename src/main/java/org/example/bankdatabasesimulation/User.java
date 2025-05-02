package org.example.bankdatabasesimulation;

import java.sql.Date;

public class User {
    protected int userId;
    protected String accountPass;
    protected String fName;
    protected String lName;
    protected String email;
    protected String phoneNum;
    protected Date DOB;
    protected String address;

    public User(int userId, String accountPass, String fName, String lName, String email, String phoneNum, Date DOB, String address) {
        this.userId = userId;
        this.accountPass = accountPass;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.DOB = DOB;
        this.address = address;
    }

    public User() {

    }

    public int getUserId() {
        return userId;
    }


}


