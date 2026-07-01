package com.resources.model;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String address;
    private String contact;
    private String nickname;
    private String password;
    private String role;
    private String createdAt;
    
    public Customer() {}
    
    public Customer(String firstName, String lastName, String contact, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.password = password;
        this.role = "customer";
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getMiddleInitial() { return middleInitial; }
    public void setMiddleInitial(String middleInitial) { this.middleInitial = middleInitial; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}