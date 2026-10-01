package org.example.tests;

import org.example.api.RestApiBuilder;
import org.example.api.Urls;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PatchGoodTest {

    @Test
    @Tag("api")
    void testPatchGood_200() {
        // Создаём товар
        String uniqueName = "СтароеИмя_" + System.currentTimeMillis();
        String createBody = """
        {
          "name": "%s",
          "price": 100.00
        }
        """.formatted(uniqueName);
        int id = given()
                .spec(RestApiBuilder.getBuilder())
                .body(createBody)
                .when()
                .post(Urls.GOODS_ADD)
                .jsonPath().getInt("data.id");

        // Обновляем
        String patchBody = """
        {
          "name": "НовоеИмя_%d",
          "price": 200.00
        }
        """.formatted(System.currentTimeMillis());

        given()
                .spec(RestApiBuilder.getBuilder())
                .pathParam("id", id)
                .body(patchBody)
                .log().all()
                .when()
                .patch(Urls.GOODS_PATCH)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @Tag("api")
    void testPatchGood_404() {
        String patchBody = """
            {
              "name": "НовоеИмя",
              "price": 200.00
            }
            """;

        given()
                .spec(RestApiBuilder.getBuilder())
                .pathParam("id", 99999)
                .body(patchBody)
                .log().all()
                .when()
                .patch(Urls.GOODS_PATCH)
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    @Tag("api")
    void testPatchGood_400_duplicateName() {
        // Создаём два товара
        String body1 = """
            {"name": "ТоварПатчА_%d", "price": 10.00}
            """.formatted(System.currentTimeMillis());
        int id1 = given()
                .spec(RestApiBuilder.getBuilder())
                .body(body1)
                .when()
                .post(Urls.GOODS_ADD)
                .jsonPath().getInt("data.id");

        String body2 = """
            {"name": "ТоварПатчБ_%d", "price": 20.00}
            """.formatted(System.currentTimeMillis());
        int id2 = given()
                .spec(RestApiBuilder.getBuilder())
                .body(body2)
                .when()
                .post(Urls.GOODS_ADD)
                .jsonPath().getInt("data.id");

        // Переименовываем ТоварБ в имя ТоварА — ожидаем 400
        String duplicateName = body1.replaceAll(".*\"name\": \"([^\"]+)\".*", "$1");

        String patchBody = """
            {"name": "%s", "price": 20.00}
            """.formatted(duplicateName);

        given()
                .spec(RestApiBuilder.getBuilder())
                .pathParam("id", id2)
                .body(patchBody)
                .log().all()
                .when()
                .patch(Urls.GOODS_PATCH)
                .then()
                .log().all()
                .statusCode(400);
    }
}