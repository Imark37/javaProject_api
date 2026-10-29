package org.example.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

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
    public MainPage clickAdminButton() {
        adminButton.click();
        return this;
    }

    public MainPage clickCartButton() {
        cartButton.click();
        return this;
    }

    public MainPage addToCart(String goodName) {
        productCards.findBy(Condition.text(goodName))
                .$("[data-action='add-to-cart']")
                .click();
        return this;
    }

    public MainPage setGoodCount(String goodName, int count) {
        var card = productCards.findBy(Condition.text(goodName));
        var input = card.$(".qty-input");
        input.clear();
        input.setValue(String.valueOf(count));
        return this;
    }

    public MainPage clickMakeOrder() {
        makeOrderButton.click();
        return this;
    }

    // Точка входа для проверок
    public MainPageAssert check() {
        return new MainPageAssert(this);
    }
}