package org.example.JDBC.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Transaction {
    private static final String JDBC_URL =
            getEnv("DB_URL", "jdbc:mysql://localhost:3306/mydatabase");
    private static final String USERNAME =
            getEnv("DB_USERNAME", "root");
    private static final String PASSWORD =
            getEnv("DB_PASSWORD", "");

    public static void main(String[] args) {
        String insertQuery = "INSERT INTO mytable (name, email) VALUES (?, ?)";
        String updateQuery = "UPDATE mytable SET name = ? WHERE id = ?";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Turn off auto-commit so both statements are one transaction
            connection.setAutoCommit(false);

            // Statement 1: insert a new row
            PreparedStatement insert = connection.prepareStatement(insertQuery);
            insert.setString(1, "Hulk");
            insert.setString(2, "hulk@example.com");
            insert.executeUpdate();

            // Statement 2: update an existing row
            PreparedStatement update = connection.prepareStatement(updateQuery);
            update.setString(1, "She-Hulk");
            update.setInt(2, 3);
            update.executeUpdate();

            // Both worked -> save them together
            connection.commit();
            System.out.println("Transaction committed.");

        } catch (Exception e) {
            // Something failed -> undo everything
            try {
                if (connection != null) connection.rollback();
                System.out.println("Transaction rolled back.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String getEnv(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }
}
