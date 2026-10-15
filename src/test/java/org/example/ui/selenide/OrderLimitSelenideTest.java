package org.example.ui.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

//Задание 2.5. Добавить в корзину товаров более чем на 300 рублей и нажать на кнопку «Оформить заказ». Проверить, что отображается JS Alert.

class OrderLimitSelenideTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "http://localhost:8080";
    }

    @Test
    void orderOver300ShowsAlert() {
        String goodName = "ДорогойТовар_" + System.currentTimeMillis();

        // 1. Логинимся в админку и создаём товар за 400 ₽
        open("/login");
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();

        $("#n-name").setValue(goodName);
        $("#n-price").setValue("400");
        $("#add-btn").click();

        // 2. Идём в витрину
        open("/");

        // 3. Находим нашу карточку и добавляем в корзину
        $$(".product-card")
                .findBy(text(goodName))
                .$("[data-action='add-to-cart']")
                .click();

        // 4. Открываем корзину
        $("#open-cart-btn").click();

        // 5. Нажимаем "Оформить заказ"
        $("#makeOrder").click();

        // 6. Проверяем JS Alert
        String alertText = switchTo().alert().getText();
        assertThat(alertText)
                .as("Текст JS Alert при превышении лимита")
                .contains("превышает лимит 300");

        // 7. Закрываем Alert
        switchTo().alert().accept();
    }
}