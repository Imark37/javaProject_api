package org.example.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.assertj.core.api.AbstractAssert;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {

    public MainPageAssert(MainPage actual) {
        super(actual, MainPageAssert.class);
    }
    @Step("Проверить, что кнопка 'Администрирование' видна")
    public MainPageAssert adminButtonIsVisible() {
        actual.adminButton.shouldBe(Condition.visible);
        return this;
    }
    @Step("Проверить, что кнопка 'Корзина' видна")
    public MainPageAssert cartButtonIsVisible() {
        actual.cartButton.shouldBe(Condition.visible);
        return this;
    }
    @Step("Проверить, что список товаров не пуст")
    public MainPageAssert productListIsNotEmpty() {
        actual.productCards.first().shouldBe(Condition.visible);
        return this;
    }
    @Step("Проверить, что товар '{text}' есть на странице")
    public MainPageAssert productNameContains(String text) {
        actual.productNames.findBy(Condition.text(text)).shouldBe(Condition.visible);
        return this;
    }
    @Step("Проверить, что тост содержит текст '{text}'")
    public MainPageAssert toastContains(String text) {
        Selenide.$$(".toast")
                .findBy(Condition.text(text))
                .shouldBe(Condition.visible);
        return this;
    }
    @Step("Проверить, что общая сумма = '{expected}'")
    public MainPageAssert totalPriceIs(String expected) {
        $("#total-price").shouldHave(Condition.text(expected));
        return this;
    }
}