package org.example.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestApiBuilder {

    public static RequestSpecification getBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080")
                .setContentType(ContentType.JSON)
                .setAuth(RestAssured.preemptive().basic("admin", "secret123"))
                .build();
    }
}