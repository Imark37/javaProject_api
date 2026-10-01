package org.example.tests;

import org.example.api.RestApiBuilder;
import org.example.api.Urls;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetGoodTest {

    @Test
    @Tag("api")
    void testGetGood_200() {
        given()
                .spec(RestApiBuilder.getBuilder())
                .pathParam("id", 4)   // id товара "Сыр"
                .log().all()
                .when()
                .get(Urls.GOODS_GET)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @Tag("api")
    void testGetGood_404() {
        // БАГ №1: API возвращает 500 вместо 404 на несуществующий id
        given()
                .spec(RestApiBuilder.getBuilder())
                .pathParam("id", 99999)
                .log().all()
                .when()
                .get(Urls.GOODS_GET)
                .then()
                .log().all()
                .statusCode(404);   // ожидаем 404, но получаем 500 — баг
    }
}