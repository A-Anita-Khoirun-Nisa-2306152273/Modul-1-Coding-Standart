package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderHistoryFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    private void createOrder(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");

        driver.findElement(By.name("author")).sendKeys("Safira");
        driver.findElement(By.name("productName")).sendKeys("Sabun");
        driver.findElement(By.name("productQuantity")).sendKeys("2");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Test
    void testOrderHistoryShowsOrders(ChromeDriver driver) {
        createOrder(driver);

        driver.get(baseUrl + "/order/history");
        driver.findElement(By.name("author")).sendKeys("Safira");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(driver.getPageSource().contains("Safira"));
        assertTrue(driver.getPageSource().contains("Sabun"));
        assertTrue(driver.getPageSource().contains("WAITING_PAYMENT"));
    }

    @Test
    void testOrderPayWithVoucher(ChromeDriver driver) {
        createOrder(driver);

        driver.get(baseUrl + "/order/history");
        driver.findElement(By.name("author")).sendKeys("Safira");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.findElement(By.linkText("Pay")).click();

        WebElement methodSelect = driver.findElement(By.name("method"));
        methodSelect.sendKeys("VOUCHER_CODE");

        driver.findElement(By.name("voucherCode")).sendKeys("ESHOP1234ABC5678");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(driver.getPageSource().contains("Payment Result"));
        assertTrue(driver.getPageSource().contains("SUCCESS"));
    }
}