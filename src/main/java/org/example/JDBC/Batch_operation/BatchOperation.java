package org.example.JDBC.Batch_operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchOperation {

    private static final String JDBC_URL =
            getEnv("DB_URL", "jdbc:mysql://localhost:3306/mydatabase");
    private static final String USERNAME =
            getEnv("DB_USERNAME", "root");
    private static final String PASSWORD =
            getEnv("DB_PASSWORD", "");

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO mytable (name, email) VALUES (?, ?)";
        try
        {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            ps = connection.prepareStatement(query);
            // Row 1
            ps.setString(1, "Bob");
            ps.setString(2, "bob@example.com");
            ps.addBatch();

            // Row 2
            ps.setString(1, "Carol");
            ps.setString(2, "carol@example.com");
            ps.addBatch();

            // Row 3
            ps.setString(1, "Dave");
            ps.setString(2, "dave@example.com");
            ps.addBatch();

            // Send all rows to the database at once
            int[] result = ps.executeBatch();
            System.out.println("Inserted " + result.length + " rows.");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) connection.close();
            if(ps != null) ps.close();
        }
    }

    private static String getEnv(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }
}
