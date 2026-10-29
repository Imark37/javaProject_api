package org.example.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.assertj.core.api.AbstractAssert;
import static com.codeborne.selenide.Selenide.$;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {

    public MainPageAssert(MainPage actual) {
        super(actual, MainPageAssert.class);
    }

    public MainPageAssert adminButtonIsVisible() {
        actual.adminButton.shouldBe(Condition.visible);
        return this;
    }

    public MainPageAssert cartButtonIsVisible() {
        actual.cartButton.shouldBe(Condition.visible);
        return this;
    }

    public MainPageAssert productListIsNotEmpty() {
        actual.productCards.first().shouldBe(Condition.visible);
        return this;
    }

    public MainPageAssert productNameContains(String text) {
        actual.productNames.findBy(Condition.text(text)).shouldBe(Condition.visible);
        return this;
    }

    public MainPageAssert toastContains(String text) {
        Selenide.$$(".toast")
                .findBy(Condition.text(text))
                .shouldBe(Condition.visible);
        return this;
    }
    public MainPageAssert totalPriceIs(String expected) {
        $("#total-price").shouldHave(Condition.text(expected));
        return this;
    }
}