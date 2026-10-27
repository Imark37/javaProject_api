package org.example.ui.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.example.config.ConfigProvider;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$$;

//Задание 2.1. Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
class AddGoodSelenideTest {

    @BeforeAll
    static void setUp() {
        ConfigProvider.printConfig();

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = ConfigProvider.CONFIG.baseUrl();
        Configuration.timeout = ConfigProvider.CONFIG.elementTimeout();
    }

    @Test
    void addGoodAndCheckOnShop() {
        String goodName = "Товар_" + System.currentTimeMillis();

        // 1. Логин в админку
        open("/login");
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();

        // 2. Добавляем товар
        $("#n-name").setValue(goodName);
        $("#n-price").setValue("99");
        $("#add-btn").click();

        // 3. Открываем витрину
        open("/");

        // 4. Проверяем, что товар отображается
        $$(".product-card h4")
                .findBy(text(goodName))
                .shouldBe(visible);
    }
}