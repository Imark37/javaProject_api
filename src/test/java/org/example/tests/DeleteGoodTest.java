package org.example.tests;

import org.example.api.RestApiBuilder;
import org.example.api.Urls;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteGoodTest {

    @Test
    @Tag("api")
    void testDeleteGood_200() {
        // Создаём товар
        String body = """
                {
                  "name": "УдаляемыйТовар",
                  "price": 10.00
                }
                """;

        int id = given()
                .spec(RestApiBuilder.getBuilder())
                .body(body)
                .when()
                .post(Urls.GOODS_ADD)
                .jsonPath().getInt("data.id");

        // Удаляем его
        given()
                .spec(RestApiBuilder.getBuilder())
                .pathParam("id", id)
                .log().all()
                .when()
                .delete(Urls.GOODS_DELETE)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @Tag("api")
    void testDeleteGood_404() {
        // БАГ №? (проверим): возможно, вернёт 500 вместо 404
        given()
                .spec(RestApiBuilder.getBuilder())
                .pathParam("id", 99999)
                .log().all()
                .when()
                .delete(Urls.GOODS_DELETE)
                .then()
                .log().all()
                .statusCode(404);
    }
}