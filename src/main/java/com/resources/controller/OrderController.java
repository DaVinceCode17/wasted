package com.resources.controller;

import com.resources.model.Order;
import com.resources.service.LaundryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/orders/*")
public class OrderController extends HttpServlet {
    
    private LaundryService service;
    
    @Override
    public void init() {
        service = new LaundryService();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        
        try {
            if (path != null && path.startsWith("/customer/")) {
                int id = Integer.parseInt(path.substring(10));
                List<Order> orders = service.getOrdersByCustomer(id);
                out.write(toJsonOrders(orders));
            } else {
                out.write("{\"success\":false,\"message\":\"Invalid GET endpoint\"}");
            }
        } catch (Exception e) {
            out.write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        
        try {
            if ("/create".equals(path)) {
                String json = readBody(req);
                Order order = new Order();
                order.setCustomerId(Integer.parseInt(extract(json, "customerId")));
                order.setServices(extract(json, "services"));
                order.setServiceType(extract(json, "serviceType"));
                
                Order created = service.createOrder(order);
                out.write("{\"success\":true,\"order\":{"
                         + "\"id\":" + created.getId() + ","
                         + "\"queueNumber\":" + created.getQueueNumber() + ","
                         + "\"status\":\"" + created.getStatus() + "\""
                         + "}}");
            } else {
                out.write("{\"success\":false,\"message\":\"Invalid POST endpoint\"}");
            }
        } catch (Exception e) {
            out.write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
    
    private String readBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader r = req.getReader()) {
            String line;
            while ((line = r.readLine()) != null) sb.append(line);
        }
        return sb.toString();
    }
    
    private String extract(String json, String key) {
        String search = "\"" + key + "\":";
        int start = json.indexOf(search);
        if (start == -1) return "";
        start += search.length();
        if (json.charAt(start) == '"') {
            start++;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }
        int end = start;
        while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') end++;
        return json.substring(start, end).trim();
    }
    
    private String toJsonOrders(List<Order> orders) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true,\"orders\":[");
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            sb.append("{");
            sb.append("\"id\":").append(o.getId()).append(",");
            sb.append("\"queueNumber\":").append(o.getQueueNumber()).append(",");
            sb.append("\"customerName\":\"").append(escape(o.getCustomerName())).append("\",");
            sb.append("\"services\":\"").append(escape(o.getServices())).append("\",");
            sb.append("\"status\":\"").append(escape(o.getStatus())).append("\",");
            sb.append("\"serviceType\":\"").append(escape(o.getServiceType())).append("\",");
            sb.append("\"weight\":").append(o.getWeight()).append(",");
            sb.append("\"price\":").append(o.getPrice()).append(",");
            sb.append("\"createdAt\":\"").append(escape(o.getCreatedAt())).append("\"");
            sb.append("}");
            if (i < orders.size() - 1) sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }
    
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}