package org.example;

// Импорты для работы с XML валидацией
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XMLValidator {

    public static void validate(String xmlFile, String xsdFile) {
        try {
            // Создание фабрики схем для работы с XSD
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Создание валидатора на основе XSD-файла
            Validator validator = schemaFactory.newSchema(new File(xsdFile)).newValidator();

            // Валидация XML-файла с использованием валидатора
            validator.validate(new StreamSource(new File(xmlFile)));

            // Вывод результата успешной валидации
            System.out.println("XML-документ валиден!");
        } catch (Exception e) {
            // Обработка ошибок валидации
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }
}


