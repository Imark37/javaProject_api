package org.example.ui.page;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;

public class LoginPageAssert extends AbstractAssert<LoginPageAssert, LoginPage> {

    public LoginPageAssert(LoginPage actual) {
        super(actual, LoginPageAssert.class);
    }

    public LoginPageAssert usernameInputIsVisible() {
        actual.usernameInput.shouldBe(Condition.visible);
        return this;
    }

    public LoginPageAssert passwordInputIsVisible() {
        actual.passwordInput.shouldBe(Condition.visible);
        return this;
    }

    public LoginPageAssert signInButtonIsVisible() {
        actual.signInButton.shouldBe(Condition.visible);
        return this;
    }

    public LoginPageAssert usernameInputContainsText(String text) {
        actual.usernameInput.shouldHave(Condition.value(text));
        return this;
    }
}