package org.example;


public class Main {
    public static void main(String[] args) {
        SmartphoneFactory basicFactory = new BasicSmartphoneFactory();
        Smartphone basicPhone = basicFactory.createSmartphone();
        System.out.println("Базовая модель смартфона:");
        System.out.println("Модель: " + basicPhone.getModel());
        System.out.println("Процессор: " + basicPhone.getProcessor());
        System.out.println("Камера: " + basicPhone.getCamera());

        SmartphoneFactory advancedFactory = new AdvancedSmartphoneFactory();
        Smartphone advancedPhone = advancedFactory.createSmartphone();
        System.out.println("\nПродвинутая модель смартфона:");
        System.out.println("Модель: " + advancedPhone.getModel());
        System.out.println("Процессор: " + advancedPhone.getProcessor());
        System.out.println("Камера: " + advancedPhone.getCamera());
    }
}


