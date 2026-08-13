package org.example.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JDBCExample {
    // Credentials come from environment variables so they are never committed to source.
    // Optional fallbacks make local development convenient; set the vars to override.
    private static final String JDBC_URL =
            getEnv("DB_URL", "jdbc:mysql://localhost:3306/mydatabase");
    private static final String USERNAME =
            getEnv("DB_USERNAME", "root");
    private static final String PASSWORD =
            getEnv("DB_PASSWORD", "");

    public static void main(String[] args) {
        String query = "SELECT * FROM mytable";

        // try-with-resources guarantees every JDBC object is closed, even on error.
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            printResultSet(resultSet);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private static String getEnv(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }


    private static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        int rowNumber = 0;
        while (resultSet.next()) {
            rowNumber++;
            System.out.println("--- Row " + rowNumber + " ---");
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                Object value = resultSet.getObject(i);
                System.out.println(columnName + ": " + (value == null ? "NULL" : value));
            }
        }

        if (rowNumber == 0) {
            System.out.println("No rows returned.");
        }
    }
}
