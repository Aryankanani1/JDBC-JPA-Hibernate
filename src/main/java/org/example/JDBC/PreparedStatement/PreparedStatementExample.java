package org.example.JDBC.PreparedStatement;

import java.sql.*;

public class PreparedStatementExample {
    private static final String JDBC_URL =
            getEnv("DB_URL", "jdbc:mysql://localhost:3306/mydatabase");

    private static final String DB_USERNAME = getEnv("DB_USERNAME", "root");

    private static final String DB_PASSWORD = getEnv("DB_PASSWORD", "");

    public static void main(String[] args) throws SQLException {
        Connection connection  = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
            String name = "Robert";
            String query = "SELECT * FROM mytable WHERE name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
             resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String Username = resultSet.getString("name");
                System.out.println("ID: " + id + "--" + "Username: " + Username);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null) connection.close();
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) preparedStatement.close();
        }

    }

    private static String getEnv(String name,String defaultValue){
        String value = System.getenv(name);
        if(value == null || value.isEmpty()){
            return defaultValue;
        }else {
            return value;
        }
    }
}
