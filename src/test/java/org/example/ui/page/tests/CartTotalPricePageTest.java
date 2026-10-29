package org.example.ui.page.tests;

import org.example.api.RestApiBuilder;
import org.example.ui.page.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

class CartTotalPricePageTest extends BaseTest {

    private final List<Integer> createdIds = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        for (int id : createdIds) {
            given().spec(RestApiBuilder.getBuilder()).delete("/goods/" + id);
        }
        createdIds.clear();
    }

    @Test
    void cartTotalPriceIsCorrect() {
        String ts = String.valueOf(System.currentTimeMillis());
        String name1 = "Товар_A_" + ts;
        String name2 = "Товар_B_" + ts;

        // 1. Создаём два товара через API
        int id1 = given().spec(RestApiBuilder.getBuilder())
                .body("{\"name\": \"" + name1 + "\", \"price\": 100.0}")
                .post("/goods/add").jsonPath().getInt("data.id");
        createdIds.add(id1);

        int id2 = given().spec(RestApiBuilder.getBuilder())
                .body("{\"name\": \"" + name2 + "\", \"price\": 50.0}")
                .post("/goods/add").jsonPath().getInt("data.id");
        createdIds.add(id2);

        // 2. Добавляем оба в корзину
        open("/");
        mainPage.addToCart(name1)
                .addToCart(name2)
                .clickCartButton();

        // 3. Проверяем общую сумму
        mainPage.check().totalPriceIs("150");
    }
}