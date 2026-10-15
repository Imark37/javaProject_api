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

//Задание 1.4 - Проверить сохранение товаров в корзине после обновления страницы.
class CartPersistenceSeleniumTest {

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
    void cartIsSavedAfterPageRefresh() {
        // БАГ: корзина не сохраняется после обновления страницы.
        // Ожидается: товар остаётся в корзине.
        // Фактически: корзина пуста.

        // 1. Открываем витрину
        driver.get("http://localhost:8080/");

        // 2. Берём имя первого товара
        WebElement firstCard = driver.findElements(By.cssSelector(".product-card")).get(0);
        String goodName = firstCard.findElement(By.cssSelector("h4")).getText();

        // 3. Кликаем "В корзину"
        firstCard.findElement(By.cssSelector("[data-action='add-to-cart']")).click();

        // 4. Обновляем страницу
        driver.navigate().refresh();

        // 5. Открываем корзину
        driver.findElement(By.id("open-cart-btn")).click();

        // 6. Проверяем, что товар на месте
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart-item"));
        assertThat(cartItems)
                .as("БАГ: после обновления страницы корзина не сохраняется")
                .isNotEmpty();
    }
}