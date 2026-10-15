package org.example.ui.selenide;

import com.codeborne.selenide.Configuration;
import org.example.api.RestApiBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

//Задание 3.1. Добавить три единицы товара в корзину и оплатить их (общая стоимость не должна превышать 300 рублей). Проверить уведомление об обработке заказа.
class OrderSuccessTest {

    private static int createdGoodId;

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "http://localhost:8080";
    }

    @AfterEach
    void cleanUp() {
        if (createdGoodId > 0) {
            given()
                    .spec(RestApiBuilder.getBuilder())
                    .when()
                    .delete("/goods/" + createdGoodId)
                    .then()
                    .statusCode(200);

            createdGoodId = 0;
        }
    }

    @Test
    void orderThreeItemsUnder300() {
        String goodName = "Товар_" + System.currentTimeMillis();

        // 1. Создаём товар через API (требование 3.5 — генерация данных через API)
        createdGoodId = given()
                .spec(RestApiBuilder.getBuilder())
                .body("""
                        {"name": "%s", "price": 99.0}
                        """.formatted(goodName))
                .when()
                .post("/goods/add")
                .jsonPath().getInt("data.id");

        // 2. Открываем витрину
        open("/");

        // 3. Находим карточку нашего товара и увеличиваем количество до 3
        var card = $$(".product-card").findBy(text(goodName));
        card.$("[data-step='1']").click();
        card.$("[data-step='1']").click();

        // 4. Добавляем в корзину
        card.$("[data-action='add-to-cart']").click();

        // 5. Открываем корзину
        $("#open-cart-btn").click();

        // 6. Оформляем заказ
        $("#makeOrder").click();

        // 7. Проверяем уведомление
        $$(".toast")
                .findBy(text("Заказ принят в обработку"))
                .shouldBe(visible);
    }
}