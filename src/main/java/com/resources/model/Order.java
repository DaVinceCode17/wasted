package com.resources.model;

import java.util.Arrays;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private String customerName;
    private String services;
    private String serviceType;
    private String status;
    private int queueNumber;
    private double weight;
    private double price;
    private String createdAt;
    private String updatedAt;
    
    public Order() {}
    
    public Order(int customerId, String services, String serviceType) {
        this.customerId = customerId;
        this.services = services;
        this.serviceType = serviceType;
        this.status = "pending";
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getServices() { return services; }
    public void setServices(String services) { this.services = services; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getQueueNumber() { return queueNumber; }
    public void setQueueNumber(int queueNumber) { this.queueNumber = queueNumber; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    
    public List<String> getServicesList() {
        if (services == null || services.isEmpty()) return Arrays.asList();
        return Arrays.asList(services.split(",\\s*"));
    }
}