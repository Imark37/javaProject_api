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

//Задание 1.1 - Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
class AddGoodSeleniumTest {

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
    void addGoodAndCheckOnShop() {
        String goodName = "Товар_" + System.currentTimeMillis();
        String goodPrice = "99";

        // 1. Логинимся в админку
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("secret123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 2. На странице админки добавляем товар
        driver.findElement(By.id("n-name")).sendKeys(goodName);
        driver.findElement(By.id("n-price")).sendKeys(goodPrice);
        driver.findElement(By.id("add-btn")).click();

        // 3. Переходим в витрину
        driver.get("http://localhost:8080/");

        // 4. Проверяем, что товар отображается
        WebElement productName = driver.findElement(
                By.xpath("//div[@class='product-card']/h4[text()='" + goodName + "']")
        );

        assertThat(productName.getText())
                .as("Название добавленного товара на витрине")
                .isEqualTo(goodName);
    }
}