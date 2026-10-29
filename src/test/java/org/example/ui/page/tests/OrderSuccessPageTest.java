package org.example.ui.page.tests;

import org.example.api.RestApiBuilder;
import org.example.ui.page.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

class OrderSuccessPageTest extends BaseTest {

    private final List<Integer> createdIds = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        for (int id : createdIds) {
            given().spec(RestApiBuilder.getBuilder()).delete("/goods/" + id);
        }
        createdIds.clear();
    }

    @Test
    void orderThreeItemsUnder300() {
        String goodName = "Товар_" + System.currentTimeMillis();

        // 1. Создаём товар через API
        int id = given()
                .spec(RestApiBuilder.getBuilder())
                .body("{\"name\": \"" + goodName + "\", \"price\": 99.0}")
                .post("/goods/add")
                .jsonPath().getInt("data.id");
        createdIds.add(id);

        // 2. Открываем витрину, ставим 3 шт. и добавляем в корзину
        open("/");
        mainPage.setGoodCount(goodName, 3)
                .addToCart(goodName)
                .clickCartButton()
                .clickMakeOrder();

        // 3. Проверяем уведомление (через PageAssert)
        mainPage.check().toastContains("Заказ принят в обработку");
    }
}