package org.example.tests;

import org.example.api.RestApiBuilder;
import org.example.api.Urls;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AddGoodTest {

    @Test
    @Tag("api")
    void testAddGood_200() {
        String uniqueName = "Сыр_" + System.currentTimeMillis();
        String body = """
        {
          "name": "%s",
          "price": 250.00
        }
        """.formatted(uniqueName);
        given()
                .spec(RestApiBuilder.getBuilder())
                .body(body)
                .log().all()
                .when()
                .post(Urls.GOODS_ADD)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @Tag("api")
    void testAddGood_400_emptyName() {
        String body = """
                {
                  "name": "",
                  "price": 100.00
                }
                """;

        given()
                .spec(RestApiBuilder.getBuilder())
                .body(body)
                .log().all()
                .when()
                .post(Urls.GOODS_ADD)
                .then()
                .log().all()
                .statusCode(400);
    }
}