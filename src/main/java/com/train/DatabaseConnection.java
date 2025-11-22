package com.train;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/RAILWAY_SYSTEM";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // change this

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection(); // test connection
    }
}
