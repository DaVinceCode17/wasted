package com.resources.service;

import com.resources.dao.CustomerDAO;
import com.resources.dao.OrderDAO;
import com.resources.dao.PricingDAO;
import com.resources.model.Customer;
import com.resources.model.Order;
import com.resources.model.Pricing;
import java.sql.SQLException;
import java.util.List;

public class LaundryService {
    
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private PricingDAO pricingDAO;
    
    public LaundryService() {
        customerDAO = new CustomerDAO();
        orderDAO = new OrderDAO();
        pricingDAO = new PricingDAO();
    }
    
    // ===== CUSTOMER METHODS =====
    public Customer login(String contact, String password) throws SQLException {
        return customerDAO.findByContactAndPassword(contact, password);
    }
    
    public boolean register(Customer customer) throws SQLException {
        if (customerDAO.findByContact(customer.getContact()) != null) {
            throw new IllegalArgumentException("Contact already registered");
        }
        return customerDAO.save(customer);
    }
    
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.findAll();
    }
    
    public Customer getCustomerById(int id) throws SQLException {
        return customerDAO.findById(id);
    }
    
    public Customer getCustomerByContact(String contact) throws SQLException {
        return customerDAO.findByContact(contact);
    }
    
    // ===== ORDER METHODS =====
    public Order createOrder(Order order) throws SQLException {
        order.setQueueNumber(orderDAO.getNextQueueNumber());
        order.setStatus("set_pricing");
        return orderDAO.saveToSetPricing(order);
    }
    
    public boolean updateSetPricingWeightAndPrice(int queueNumber, double weight, double price) throws SQLException {
        return orderDAO.updateSetPricingWeightAndPrice(queueNumber, weight, price);
    }
    
    public Order moveToPending(int queueNumber) throws SQLException {
        return orderDAO.moveToPending(queueNumber);
    }
    
    public Order getSetPricingByQueueNumber(int queueNumber) throws SQLException {
        return orderDAO.getSetPricingByQueueNumber(queueNumber);
    }
    
    public Order getPendingByQueueNumber(int queueNumber) throws SQLException {
        return orderDAO.getPendingByQueueNumber(queueNumber);
    }
    
    public List<Order> getSetPricingOrders() throws SQLException {
        return orderDAO.getSetPricingOrders();
    }
    
    public List<Order> getOrdersByTable(String tableName) throws SQLException {
        return orderDAO.getOrdersByTable(tableName);
    }
    
    public List<Order> getOrdersByCustomer(int customerId) throws SQLException {
        return orderDAO.getOrdersByCustomer(customerId);
    }
    
    public Order moveToWash(int queueNumber) throws SQLException {
        return orderDAO.moveToWash(queueNumber);
    }
    
    public Order moveToDry(int queueNumber) throws SQLException {
        return orderDAO.moveToDry(queueNumber);
    }
    
    public Order moveToIron(int queueNumber) throws SQLException {
        return orderDAO.moveToIron(queueNumber);
    }
    
    public Order moveToFold(int queueNumber) throws SQLException {
        return orderDAO.moveToFold(queueNumber);
    }
    
    public Order moveToForPickup(int queueNumber) throws SQLException {
        return orderDAO.moveToForPickup(queueNumber);
    }
    
    public Order moveToDeliver(int queueNumber) throws SQLException {
        return orderDAO.moveToDeliver(queueNumber);
    }
    
    public Order moveToClaimed(int queueNumber) throws SQLException {
        return orderDAO.moveToClaimed(queueNumber);
    }
    
    public Order moveToClaimedFromPickup(int queueNumber) throws SQLException {
        return orderDAO.moveToClaimedFromPickup(queueNumber);
    }
    
    // ===== PRICING METHODS =====
    public Pricing getPricing() throws SQLException {
        Pricing p = pricingDAO.getPricing();
        if (p == null) {
            pricingDAO.insertDefaultPricing();
            p = pricingDAO.getPricing();
        }
        return p;
    }
    
    public boolean updatePricing(Pricing pricing) throws SQLException {
        return pricingDAO.updatePricing(pricing);
    }
}