package com.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static Connection connection = null;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    
    static {
        String railwayUrl = System.getenv("DATABASE_URL");
        String railwayUser = System.getenv("DATABASE_USERNAME");
        String railwayPass = System.getenv("DATABASE_PASSWORD");
        
        if (railwayUrl != null && !railwayUrl.isEmpty()) {
            System.out.println("✅ Using Railway Database");
            if (railwayUrl.startsWith("mysql://")) {
                railwayUrl = railwayUrl.replace("mysql://", "jdbc:mysql://");
            }
            URL = railwayUrl + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            USER = railwayUser != null ? railwayUser : "";
            PASSWORD = railwayPass != null ? railwayPass : "";
        } else {
            System.out.println("✅ Using Local MySQL");
            URL = "jdbc:mysql://localhost:3306/laundry_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            USER = "root";
            PASSWORD = "";
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL Driver loaded.");
            System.out.println("✅ Database URL: " + URL);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found: " + e.getMessage());
        }
    }
    
    private DatabaseConnection() {}
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("❌ Error closing: " + e.getMessage());
            }
        }
    }
}