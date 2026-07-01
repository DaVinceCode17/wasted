package com.resources.controller;

import com.resources.model.Customer;
import com.resources.service.LaundryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {
    
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
            if (path != null && path.startsWith("/customer/by-contact/")) {
                String contact = path.substring("/customer/by-contact/".length());
                Customer c = service.getCustomerByContact(contact);
                
                if (c != null) {
                    out.write("{\"success\":true,\"customer\":{"
                             + "\"id\":" + c.getId() + ","
                             + "\"firstName\":\"" + escape(c.getFirstName()) + "\","
                             + "\"lastName\":\"" + escape(c.getLastName()) + "\","
                             + "\"contact\":\"" + escape(c.getContact()) + "\","
                             + "\"role\":\"" + escape(c.getRole()) + "\""
                             + "}}");
                } else {
                    out.write("{\"success\":false,\"message\":\"Customer not found\"}");
                }
            } else {
                out.write("{\"success\":false,\"message\":\"Invalid GET endpoint\"}");
            }
        } catch (Exception e) {
            out.write("{\"success\":false,\"message\":\"" + escape(e.getMessage()) + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        
        String json = readBody(req);
        
        try {
            if ("/login".equals(path)) {
                String contact = extract(json, "contact");
                String password = extract(json, "password");
                Customer c = service.login(contact, password);
                if (c != null) {
                    out.write("{\"success\":true,\"user\":{"
                             + "\"id\":" + c.getId() + ","
                             + "\"firstName\":\"" + escape(c.getFirstName()) + "\","
                             + "\"lastName\":\"" + escape(c.getLastName()) + "\","
                             + "\"contact\":\"" + escape(c.getContact()) + "\","
                             + "\"role\":\"" + escape(c.getRole()) + "\""
                             + "}}");
                } else {
                    out.write("{\"success\":false,\"message\":\"Invalid credentials\"}");
                }
            } else if ("/register".equals(path)) {
                Customer c = new Customer();
                c.setFirstName(extract(json, "firstName"));
                c.setLastName(extract(json, "lastName"));
                c.setMiddleInitial(extract(json, "middleInitial"));
                c.setAddress(extract(json, "address"));
                c.setContact(extract(json, "contact"));
                c.setNickname(extract(json, "nickname"));
                c.setPassword(extract(json, "password"));
                c.setRole("customer");
                boolean success = service.register(c);
                out.write("{\"success\":" + success + ",\"message\":\"" + (success ? "Account created successfully" : "Registration failed") + "\"}");
            } else {
                out.write("{\"success\":false,\"message\":\"Invalid POST endpoint\"}");
            }
        } catch (Exception e) {
            out.write("{\"success\":false,\"message\":\"" + escape(e.getMessage()) + "\"}");
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
    
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}