package org.example;

import org.w3c.dom.*; // Импорт для работы с DOM (Document Object Model)
import javax.xml.parsers.DocumentBuilderFactory; // Импорт для создания парсера XML-документа
import java.io.File; // Импорт для работы с файлами

public class LibraryProcessor {

    public static void process(String xmlFile) {
        try {
            // Парсинг XML-файла и создание DOM-документа
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlFile));

            // Нормализация документа (упрощение обработки вложенных элементов)
            document.getDocumentElement().normalize();

            // Получение списка всех элементов <book> из документа
            NodeList books = document.getElementsByTagName("book");
            double totalPrice = 0; // Переменная для хранения общей цены книг

            // Вывод списка книг в консоль
            System.out.println("Список книг:");
            for (int i = 0; i < books.getLength(); i++) {
                Node bookNode = books.item(i); // Получение текущего узла <book>
                if (bookNode.getNodeType() == Node.ELEMENT_NODE) { // Проверка, является ли узел элементом
                    Element bookElement = (Element) bookNode;

                    // Извлечение данных из дочерних элементов
                    String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                    String author = bookElement.getElementsByTagName("author").item(0).getTextContent();
                    int year = Integer.parseInt(bookElement.getElementsByTagName("year").item(0).getTextContent());
                    String genre = bookElement.getElementsByTagName("genre").item(0).getTextContent();
                    double price = Double.parseDouble(bookElement.getElementsByTagName("price").item(0).getTextContent());

                    // Добавление цены книги к общей сумме
                    totalPrice += price;

                    // Форматированный вывод данных книги
                    System.out.printf("Название: %s, Автор: %s, Год: %d, Жанр: %s, Цена: %.2f%n", title, author, year, genre, price);
                }
            }

            // Вычисление и вывод средней цены книг
            double avgPrice = totalPrice / books.getLength();
            System.out.printf("\nСредняя цена книг: %.2f%n", avgPrice);

            // Фильтрация книг по жанру "Роман" и вывод результата
            System.out.println("\nФильтрация по жанру 'Роман':");
            for (int i = 0; i < books.getLength(); i++) {
                Node bookNode = books.item(i); // Получение текущего узла <book>
                if (bookNode.getNodeType() == Node.ELEMENT_NODE) { // Проверка, является ли узел элементом
                    Element bookElement = (Element) bookNode;
                    String genre = bookElement.getElementsByTagName("genre").item(0).getTextContent();
                    if ("Роман".equals(genre)) { // Условие для фильтрации книг с жанром "Роман"
                        // Извлечение данных и вывод только для книг с жанром "Роман"
                        String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                        String author = bookElement.getElementsByTagName("author").item(0).getTextContent();
                        int year = Integer.parseInt(bookElement.getElementsByTagName("year").item(0).getTextContent());
                        double price = Double.parseDouble(bookElement.getElementsByTagName("price").item(0).getTextContent());
                        System.out.printf("Название: %s, Автор: %s, Год: %d, Цена: %.2f%n", title, author, year, price);
                    }
                }
            }
        } catch (Exception e) {
            // Обработка ошибок, например, отсутствие файла или некорректный формат данных
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}



