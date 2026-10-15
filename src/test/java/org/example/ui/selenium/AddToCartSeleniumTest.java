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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//Задание 1.2 - Добавить товар в корзину и проверить, что он отображается.
class AddToCartSeleniumTest {

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
    void addGoodToCartAndCheckItDisplayed() {
        // 1. Открываем витрину
        driver.get("http://localhost:8080/");

        // 2. Берём название и цену первого товара из карточки
        WebElement firstCard = driver.findElements(By.cssSelector(".product-card")).get(0);
        String goodName = firstCard.findElement(By.cssSelector("h4")).getText();

        // 3. Кликаем "В корзину"
        firstCard.findElement(By.cssSelector("[data-action='add-to-cart']")).click();

        // 4. Открываем корзину
        driver.findElement(By.id("open-cart-btn")).click();

        // 5. Проверяем, что товар в корзине
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart-item"));
        assertThat(cartItems)
                .as("Корзина должна содержать хотя бы один товар")
                .isNotEmpty();

        String cartItemText = cartItems.get(0).getText();
        assertThat(cartItemText)
                .as("В корзине должен быть товар '%s'", goodName)
                .contains(goodName);
    }
}