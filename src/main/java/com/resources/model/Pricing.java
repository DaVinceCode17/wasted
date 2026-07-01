package com.resources.model;

public class Pricing {
    private int id;
    private double washPrice;
    private double dryPrice;
    private double ironPrice;
    private double foldPrice;
    
    public Pricing() {}
    
    public Pricing(double washPrice, double dryPrice, double ironPrice, double foldPrice) {
        this.washPrice = washPrice;
        this.dryPrice = dryPrice;
        this.ironPrice = ironPrice;
        this.foldPrice = foldPrice;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getWashPrice() { return washPrice; }
    public void setWashPrice(double washPrice) { this.washPrice = washPrice; }
    public double getDryPrice() { return dryPrice; }
    public void setDryPrice(double dryPrice) { this.dryPrice = dryPrice; }
    public double getIronPrice() { return ironPrice; }
    public void setIronPrice(double ironPrice) { this.ironPrice = ironPrice; }
    public double getFoldPrice() { return foldPrice; }
    public void setFoldPrice(double foldPrice) { this.foldPrice = foldPrice; }
}