package org.example;

public class AdvancedSmartphoneFactory extends SmartphoneFactory {
    @Override
    public Smartphone createSmartphone() {
        return new AdvancedSmartphone();
    }
}

