package org.example.ui.selenide;

import com.codeborne.selenide.Configuration;
import org.example.api.RestApiBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

//Задание 3.2. Добавить в корзину несколько разных товаров и проверить, что общая цена в корзине считается корректно.
class CartTotalPriceTest {

    private static final List<Integer> createdIds = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "http://localhost:8080";
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
    void cartTotalPriceIsCorrect() {
        String ts = String.valueOf(System.currentTimeMillis());

        // 1. Создаём два товара через API
        int id1 = createGood("Товар_A_" + ts, 100.0);
        int id2 = createGood("Товар_B_" + ts, 50.0);

        // 2. Открываем витрину
        open("/");

        // 3. Добавляем первый товар
        $$(".product-card").findBy(text("Товар_A_" + ts))
                .$("[data-action='add-to-cart']").click();

        // 4. Добавляем второй товар
        $$(".product-card").findBy(text("Товар_B_" + ts))
                .$("[data-action='add-to-cart']").click();

        // 5. Открываем корзину
        $("#open-cart-btn").click();

        // 6. Проверяем общую сумму
        $("#total-price")
                .shouldBe(visible)
                .shouldHave(text("150"));

        // 7. Дополнительно — проверим через AssertJ (получим число)
        String totalText = $("#total-price").getText();
        double total = Double.parseDouble(totalText);

        assertThat(total)
                .as("Общая сумма в корзине (100 + 50)")
                .isEqualTo(150.0);
    }

    private static int createGood(String name, double price) {
        int id = given()
                .spec(RestApiBuilder.getBuilder())
                .body("""
                        {"name": "%s", "price": %s}
                        """.formatted(name, price))
                .when()
                .post("/goods/add")
                .jsonPath().getInt("data.id");

        createdIds.add(id);
        return id;
    }
}