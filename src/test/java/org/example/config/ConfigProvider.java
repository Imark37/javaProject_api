package org.example.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigProvider {

    public static final AppConfig CONFIG = ConfigFactory.create(AppConfig.class, System.getProperties());

    public static void printConfig() {
        System.out.println("=== CONFIG ===");
        System.out.println("baseUrl         = " + CONFIG.baseUrl());
        System.out.println("apiUrl          = " + CONFIG.apiUrl());
        System.out.println("elementTimeout  = " + CONFIG.elementTimeout());
        System.out.println("logging.enabled = " + CONFIG.loggingEnabled());
        System.out.println("logging.level   = " + CONFIG.loggingLevel());
        System.out.println("startGood.name  = " + CONFIG.startGoodName());
        System.out.println("startGood.price = " + CONFIG.startGoodPrice());
        System.out.println("==============");
    }
}