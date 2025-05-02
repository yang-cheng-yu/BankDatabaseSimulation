package org.example.bankdatabasesimulation;

public class InterestObject {
    int year;
    double money;

    InterestObject(int year, double money){
        this.year = year;
        this.money = money;
    }
    @Override
    public String toString() {
        return "InterestObject{" +
                "year=" + year +
                ", money=" + money +
                '}';
    }
}
