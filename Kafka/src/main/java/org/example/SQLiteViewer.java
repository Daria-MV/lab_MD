package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteViewer {
    public static void main(String[] args) {
        String sqliteUrl = "jdbc:sqlite:F:/Kafka/user_data.db"; // Укажите правильный путь к вашей базе данных

        try (Connection connection = DriverManager.getConnection(sqliteUrl);
             Statement statement = connection.createStatement()) {

            // Выполнение SQL-запроса
            String query = "SELECT * FROM user_actions";
            ResultSet resultSet = statement.executeQuery(query);

            // Вывод данных на экран
            System.out.println("Данные из таблицы user_actions:");
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String action = resultSet.getString("action");
                String timestamp = resultSet.getString("timestamp");
                System.out.printf("UserID: %s, Action: %s, Timestamp: %s%n", userId, action, timestamp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
