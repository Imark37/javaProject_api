package org.example.ui.page;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.example.config.ConfigProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected MainPage mainPage = new MainPage();
    protected LoginPage loginPage = new LoginPage();

    @BeforeAll
    static void setUp() {
        ConfigProvider.printConfig();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = ConfigProvider.CONFIG.baseUrl();
        Configuration.timeout = ConfigProvider.CONFIG.elementTimeout();
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}