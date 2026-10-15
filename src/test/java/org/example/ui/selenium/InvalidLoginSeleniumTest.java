package org.example.ui.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

//Задание 1.3 - Попытаться войти в админку с неверным логином и паролем.
class InvalidLoginSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void loginWithInvalidCredentials() {
        // 1. Открываем форму входа
        driver.get("http://localhost:8080/login");

        // 2. Вводим неверные данные
        driver.findElement(By.id("username")).sendKeys("wrong_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 3. Проверяем, что появилось сообщение об ошибке
        WebElement errorAlert = driver.findElement(By.cssSelector(".alert.alert-danger"));

        assertThat(errorAlert.getText())
                .as("Текст ошибки при неверном логине")
                .contains("Неверные учетные данные пользователя");

        // 4. Проверяем, что URL остался на /login
        assertThat(driver.getCurrentUrl())
                .as("URL после неудачного входа")
                .contains("/login");
    }
}