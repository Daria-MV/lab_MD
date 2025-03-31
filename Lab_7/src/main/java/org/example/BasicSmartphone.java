package org.example;

public class BasicSmartphone implements Smartphone {
    @Override
    public String getModel() {
        return "Basic Model";
    }

    @Override
    public String getProcessor() {
        return "Quad-core Processor";
    }

    @Override
    public String getCamera() {
        return "8 MP Camera";
    }
}

