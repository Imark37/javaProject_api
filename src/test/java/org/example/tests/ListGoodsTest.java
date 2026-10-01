package org.example.tests;

import org.example.api.RestApiBuilder;
import org.example.api.Urls;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ListGoodsTest {

    @Test
    @Tag("api")
    void testListGoods_200() {
        given()
                .spec(RestApiBuilder.getBuilder())
                .log().all()
                .when()
                .get(Urls.GOODS_LIST)
                .then()
                .log().all()
                .statusCode(200);
    }
}