package org.example.ui.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public SelenideElement usernameInput = $("#username");
    public SelenideElement passwordInput = $("#password");
    public SelenideElement signInButton = $("button[type='submit']");

    @Step("Ввести логин: {username}")
    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }
    @Step("Ввести пароль")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }
    @Step("Нажать кнопку 'Войти'")
    public LoginPage clickSignIn() {
        signInButton.click();
        return this;
    }

    public LoginPageAssert check() {
        return new LoginPageAssert(this);
    }
}