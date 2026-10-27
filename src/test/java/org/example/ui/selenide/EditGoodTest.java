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
import static com.codeborne.selenide.Condition.attribute;
import static io.restassured.RestAssured.given;

//Задание 3.4. Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились
class EditGoodTest {

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

    @Test
    void editGoodAndCheckOnShop() {
        String ts = String.valueOf(System.currentTimeMillis());
        String oldName = "СтароеИмя_" + ts;
        String newName = "НовоеИмя_" + ts;
        String newPrice = "777";

        // 1. Создаём товар через API
        int id = given()
                .spec(RestApiBuilder.getBuilder())
                .body("""
                        {"name": "%s", "price": 100.0}
                        """.formatted(oldName))
                .when()
                .post("/goods/add")
                .jsonPath().getInt("data.id");

        createdIds.add(id);

        // 2. Логинимся в админку
        open("/login");
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();

        // 3. Меняем имя и цену в инпутах для нашего товара
        $("#nm-" + id).setValue(newName);
        $("#pr-" + id).setValue(newPrice);

        // 4. Нажимаем "Сохранить" в строке нашего товара (ищем по data-id)
        $$("[data-action='update']")
                .findBy(attribute("data-id", String.valueOf(id)))
                .click();

        // 5. Переходим на витрину
        open("/");

        // 6. Проверяем, что новое имя и цена отображаются
        var card = $$(".product-card").findBy(text(newName));
        card.shouldBe(visible);
        card.shouldHave(text(newPrice));
    }
}