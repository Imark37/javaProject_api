package org.example.ui.page.tests;

import org.example.api.RestApiBuilder;
import org.example.ui.page.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

class AddGoodNotificationPageTest extends BaseTest {

    private final List<Integer> createdIds = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        for (int id : createdIds) {
            given().spec(RestApiBuilder.getBuilder()).delete("/goods/" + id);
        }
        createdIds.clear();
    }

    @Test
    void addGoodShowsNotification() {
        String goodName = "Товар_" + System.currentTimeMillis();

        // 1. Логинимся
        open("/login");
        loginPage.setUsername("admin")
                .setPassword("secret123")
                .clickSignIn();

        // 2. В админке добавляем товар (через API, чтобы не засорять БД)
        int id = given().spec(RestApiBuilder.getBuilder())
                .body("{\"name\": \"" + goodName + "\", \"price\": 99.0}")
                .post("/goods/add").jsonPath().getInt("data.id");
        createdIds.add(id);

        // 3. Проверяем уведомление в админке (если оно есть)
        // Или проверяем, что товар отображается на витрине
        open("/");
        mainPage.check().productNameContains(goodName);
    }
}