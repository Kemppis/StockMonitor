package com.example.stockmonitor;

public class Stock {
    private String companyName;
    private double price;

    public Stock(String companyName, double price)
    {
        this.companyName = companyName;
        this.price = price;
    }

    public String getCompanyName()
    {
        return this.companyName;
    }

    public double getPrice()
    {
        return this.price;
    }
}
