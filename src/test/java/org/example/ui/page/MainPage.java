package org.example.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    // Элементы
    public SelenideElement adminButton = $("a[href='/admin']");
    public SelenideElement cartButton = $("#open-cart-btn");
    public ElementsCollection productCards = $$(".product-card");
    public ElementsCollection productNames = $$(".product-card h4");
    public ElementsCollection productPrices = $$(".product-card > div:nth-child(2)");
    public SelenideElement makeOrderButton = $("#makeOrder");

    // Методы взаимодействия
    @Step("Клик по кнопке 'Администрирование'")
    public MainPage clickAdminButton() {
        adminButton.click();
        return this;
    }
    @Step("Клик по кнопке 'Корзина'")
    public MainPage clickCartButton() {
        cartButton.click();
        return this;
    }
    @Step("Добавить товар '{goodName}' в корзину")
    public MainPage addToCart(String goodName) {
        productCards.findBy(Condition.text(goodName))
                .$("[data-action='add-to-cart']")
                .click();
        return this;
    }
    @Step("Установить количество товара '{goodName}' = {count}")
    public MainPage setGoodCount(String goodName, int count) {
        var card = productCards.findBy(Condition.text(goodName));
        var input = card.$(".qty-input");
        input.clear();
        input.setValue(String.valueOf(count));
        return this;
    }
    @Step("Нажать 'Оформить заказ'")
    public MainPage clickMakeOrder() {
        makeOrderButton.click();
        return this;
    }

    // Точка входа для проверок
    public MainPageAssert check() {
        return new MainPageAssert(this);
    }
}