package org.example;

public class AdvancedSmartphone implements Smartphone {
    @Override
    public String getModel() {
        return "Advanced Model";
    }

    @Override
    public String getProcessor() {
        return "Octa-core Processor";
    }

    @Override
    public String getCamera() {
        return "48 MP Camera";
    }
}
