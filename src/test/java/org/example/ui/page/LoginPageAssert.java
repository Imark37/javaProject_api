package org.example.ui.page;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;
import io.qameta.allure.Step;

public class LoginPageAssert extends AbstractAssert<LoginPageAssert, LoginPage> {

    public LoginPageAssert(LoginPage actual) {
        super(actual, LoginPageAssert.class);
    }
    @Step("Проверить, что поле логина видно")
    public LoginPageAssert usernameInputIsVisible() {
        actual.usernameInput.shouldBe(Condition.visible);
        return this;
    }
    @Step("Проверить, что поле пароля видно")
    public LoginPageAssert passwordInputIsVisible() {
        actual.passwordInput.shouldBe(Condition.visible);
        return this;
    }
    @Step("Проверить, что кнопка 'Войти' видна")
    public LoginPageAssert signInButtonIsVisible() {
        actual.signInButton.shouldBe(Condition.visible);
        return this;
    }
    @Step("Проверить, что поле логина содержит текст '{text}'")
    public LoginPageAssert usernameInputContainsText(String text) {
        actual.usernameInput.shouldHave(Condition.value(text));
        return this;
    }
}