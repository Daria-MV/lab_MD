package org.example;

public class BasicSmartphoneFactory extends SmartphoneFactory {
    @Override
    public Smartphone createSmartphone() {
        return new BasicSmartphone();
    }
}

