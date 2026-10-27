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
import static com.codeborne.selenide.Selenide.refresh;


//Задание 2.4. Проверить сохранение товаров в корзине после обновления страницы.
class CartPersistenceSelenideTest {

    @BeforeAll
    static void setUp() {
        ConfigProvider.printConfig();

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = ConfigProvider.CONFIG.baseUrl();
        Configuration.timeout = ConfigProvider.CONFIG.elementTimeout();
    }

    @Test
    void cartIsSavedAfterPageRefresh() {
        // БАГ: корзина не сохраняется после обновления страницы

        open("/");

        String goodName = $$(".product-card h4").first().getText();

        $(".product-card [data-action='add-to-cart']").click();

        refresh();

        $("#open-cart-btn").click();

        $(".cart-item")
                .shouldBe(visible)
                .shouldHave(text(goodName));
    }
}