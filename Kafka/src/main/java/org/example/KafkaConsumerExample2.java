package org.example;



import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample2 {
    public static void main(String[] args) {
        // Настройки для Consumer
        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put("group.id", "invalid-group"); // Отдельная группа для некорректных
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("auto.offset.reset", "earliest"); // Начинаем с начала, если нет оффсета

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList("user_actions")); // Подписываемся на топик

        // Настройки для Producer (для отправки в DLT)
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

        System.out.println("Ожидание некорректных сообщений из Kafka...");

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();

                    // Проверяем, некорректно ли сообщение
                    if (!isValidMessage(message)) {
                        System.out.println("Некорректное сообщение: " + message);
                        sendToDLT(producer, message); // Отправляем некорректное сообщение в DLT
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
            producer.close();
        }
    }

    // Метод проверки корректности сообщения
    private static boolean isValidMessage(String message) {
        return message.contains("user_id") && message.contains("action") && message.contains("timestamp");
    }

    // Метод отправки некорректных сообщений в DLT
    private static void sendToDLT(KafkaProducer<String, String> producer, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>("user_actions_dlt", message);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Некорректное сообщение отправлено в DLT: " + message);
            } else {
                exception.printStackTrace();
            }
        });
    }
}


