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
import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

class DragAndDropTest {

    private static final List<Integer> createdIds = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        ConfigProvider.printConfig();

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

    // 1.1 Drag-and-Drop товара в корзину
    @Test
    void dragAndDropGoodToCart() {
        String goodName = "Товар_" + System.currentTimeMillis();

        int id = given()
                .spec(RestApiBuilder.getBuilder())
                .body("""
                        {"name": "%s", "price": 99.0}
                        """.formatted(goodName))
                .when()
                .post("/goods/add")
                .jsonPath().getInt("data.id");
        createdIds.add(id);

        open("/");

        var card = $$(".product-card").findBy(text(goodName));
        var cartButton = $("#open-cart-btn");

        // Drag-and-Drop через Actions
        actions()
                .dragAndDrop(card.toWebElement(), cartButton.toWebElement())
                .perform();

        // Открываем корзину и проверяем, что товар там
        cartButton.click();
        $(".cart-item")
                .shouldBe(visible)
                .shouldHave(text(goodName));
    }

    // 1.2 Удаление товара из корзины
    @Test
    void removeGoodFromCart() {
        String goodName = "Товар_" + System.currentTimeMillis();

        int id = given()
                .spec(RestApiBuilder.getBuilder())
                .body("""
                        {"name": "%s", "price": 99.0}
                        """.formatted(goodName))
                .when()
                .post("/goods/add")
                .jsonPath().getInt("data.id");
        createdIds.add(id);

        open("/");

        // Добавляем в корзину
        $$(".product-card").findBy(text(goodName))
                .$("[data-action='add-to-cart']").click();

        $("#open-cart-btn").click();

        // Проверяем, что товар есть
        $(".cart-item").shouldBe(visible).shouldHave(text(goodName));

        // Удаляем
        $(".cart-item [data-action='remove']").click();

        // Проверяем, что больше не отображается
        $(".cart-item").shouldNotBe(visible);
    }
}