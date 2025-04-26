package org.example;

public class Main {
    public static void main(String[] args) {
        String xmlFile = "library.xml";
        String xsdFile = "library.xsd";

        // Валидация XML
        XMLValidator.validate(xmlFile, xsdFile);

        // Обработка XML
        LibraryProcessor.process(xmlFile);
    }
}

