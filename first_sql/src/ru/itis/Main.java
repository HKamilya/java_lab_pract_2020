package ru.itis;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/java_lab_pract_2020";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qAzXcvbnm";


    public static void main(String[] args) throws SQLException {
        SimpleDataSource dataSource = new SimpleDataSource();
        Connection connection = dataSource.openConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from customer");

        while (resultSet.next()) {
            System.out.println("ID " + resultSet.getInt("id"));
            System.out.println("First Name " + resultSet.getString("first_name"));
            System.out.println("Last Name " + resultSet.getString("last_name"));
            System.out.println("Age " + resultSet.getInt("age"));
            System.out.println("City " + resultSet.getString("city"));
        }
        System.out.println("-------------------");

        resultSet.close();

        connection.close();
    }
}

