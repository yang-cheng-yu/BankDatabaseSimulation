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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
