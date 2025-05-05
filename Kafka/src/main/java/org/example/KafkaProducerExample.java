package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class KafkaProducerExample {
    public static void main(String[] args) {
        // Настройки для Producer
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите JSON-сообщение (или введите некорректное сообщение для теста):");

        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();

            // Отправляем сообщение в Kafka
            ProducerRecord<String, String> record = new ProducerRecord<>("user_actions", message);
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("Сообщение отправлено в топик: " + message);
                } else {
                    exception.printStackTrace();
                }
            });
        }

        producer.close();
        scanner.close();
    }
}

