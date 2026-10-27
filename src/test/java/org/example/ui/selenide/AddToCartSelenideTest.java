package org.example.ui.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.example.config.ConfigProvider;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

//Задание 2.2. Добавить товар в корзину и проверить, что он отображается.
class AddToCartSelenideTest {

    @BeforeAll
    static void setUp() {
        ConfigProvider.printConfig();

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = ConfigProvider.CONFIG.baseUrl();
        Configuration.timeout = ConfigProvider.CONFIG.elementTimeout();
    }

    @Test
    void addGoodToCartAndCheckItDisplayed() {
        // 1. Открываем витрину
        open("/");

        // 2. Берём имя первого товара
        String goodName = $$(".product-card h4").first().getText();

        // 3. Кликаем "В корзину" на первой карточке
        $(".product-card [data-action='add-to-cart']").click();

        // 4. Открываем корзину
        $("#open-cart-btn").click();

        // 5. Проверяем, что товар в корзине
        $(".cart-item").shouldBe(visible).shouldHave(text(goodName));
    }
}