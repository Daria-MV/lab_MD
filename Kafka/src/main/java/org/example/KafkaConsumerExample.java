package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    public static void main(String[] args) {
        // Настройки для Consumer
        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put("group.id", "valid-group"); // Группа для консистентности
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("auto.offset.reset", "earliest"); // Начало чтения с первого сообщения

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList("user_actions")); // Подписываемся на топик

        System.out.println("Ожидание сообщений из Kafka...");

        // Параметры SQLite
        String sqliteUrl = "jdbc:sqlite:user_data.db";
        // Файл базы данных SQLite

        try (Connection connection = DriverManager.getConnection(sqliteUrl)) {
            // Создаем таблицу, если ее еще нет
            String createTableSQL = "CREATE TABLE IF NOT EXISTS user_actions (" +
                    "user_id TEXT, " +
                    "action TEXT, " +
                    "timestamp TEXT)";
            connection.createStatement().execute(createTableSQL);

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    // Проверяем корректность сообщения
                    if (isValidMessage(message)) {
                        System.out.println("Корректное сообщение: " + message);
                        saveToDatabase(connection, message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    // Метод проверки корректности сообщения
    private static boolean isValidMessage(String message) {
        return message.contains("user_id") && message.contains("action") && message.contains("timestamp");
    }

    // Метод для сохранения данных в базу данных SQLite
    private static void saveToDatabase(Connection connection, String message) {
        String[] parts = message.split(","); // Предполагаем, что данные разделены запятой
        String userId = parts[0].split(":")[1].trim();
        String action = parts[1].split(":")[1].trim();
        String timestamp = parts[2].split(":")[1].trim();

        String sql = "INSERT INTO user_actions (user_id, action, timestamp) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, action);
            preparedStatement.setString(3, timestamp);
            preparedStatement.executeUpdate();
            System.out.println("Данные успешно сохранены в SQLite: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

