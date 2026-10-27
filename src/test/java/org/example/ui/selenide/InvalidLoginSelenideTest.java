package org.example.ui.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.example.config.ConfigProvider;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

//Задание 2.3. Попытаться войти в админку с неверным логином и паролем.
class InvalidLoginSelenideTest {

    @BeforeAll
    static void setUp() {
        ConfigProvider.printConfig();

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = ConfigProvider.CONFIG.baseUrl();
        Configuration.timeout = ConfigProvider.CONFIG.elementTimeout();
    }

    @Test
    void loginWithInvalidCredentials() {
        open("/login");

        $("#username").setValue("wrong_user");
        $("#password").setValue("wrong_password");
        $("button[type='submit']").click();

        $(".alert.alert-danger")
                .shouldBe(visible)
                .shouldHave(text("Неверные учетные данные пользователя"));
    }
}