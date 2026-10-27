package org.example.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.config.ConfigProvider;

public class RestApiBuilder {

    public static RequestSpecification getBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigProvider.CONFIG.apiUrl())
                .setContentType(ContentType.JSON)
                .setAuth(RestAssured.preemptive().basic(
                        ConfigProvider.CONFIG.adminUsername(),
                        ConfigProvider.CONFIG.adminPassword()))
                .build();
    }
}