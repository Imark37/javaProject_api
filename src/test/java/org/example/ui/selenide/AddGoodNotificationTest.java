package org.example.ui.selenide;

import com.codeborne.selenide.Configuration;
import org.example.api.RestApiBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.example.config.ConfigProvider;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

//Задание 3.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.
class AddGoodNotificationTest {

    private static final List<Integer> createdIds = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        ConfigProvider.printConfig();   // <-- ВЫВОД КОНФИГА

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = ConfigProvider.CONFIG.baseUrl();
        Configuration.timeout = ConfigProvider.CONFIG.elementTimeout();
    }

    @AfterEach
    void cleanUp() {
        for (int id : createdIds) {
            given()
                    .spec(RestApiBuilder.getBuilder())
                    .when()
                    .delete("/goods/" + id)
                    .then()
                    .statusCode(200);
        }
        createdIds.clear();
    }

    @Test
    void addGoodShowsNotification() {
        String goodName = "Товар_" + System.currentTimeMillis();

        // 1. Логинимся в админку
        open("/login");
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();

        // 2. Добавляем товар через UI
        $("#n-name").setValue(goodName);
        $("#n-price").setValue("99");
        $("#add-btn").click();

        // 3. Проверяем уведомление
        $$(".toast")
                .findBy(text("успешно"))
                .shouldBe(visible);

        // 4. Достаём id созданного товара через API и сохраняем для удаления
        List<java.util.Map<String, Object>> goods = given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .get("/goods/list")
                .jsonPath()
                .getList("goods");

        int id = goods.stream()
                .filter(g -> goodName.equals(g.get("name")))
                .map(g -> (Integer) g.get("id"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Товар '" + goodName + "' не найден через API"));

        createdIds.add(id);
    }
}