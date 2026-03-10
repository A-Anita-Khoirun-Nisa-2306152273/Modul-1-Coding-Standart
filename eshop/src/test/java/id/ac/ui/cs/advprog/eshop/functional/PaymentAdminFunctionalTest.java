package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
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
class PaymentAdminFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    private void createOrderAndPayment(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");
        driver.findElement(By.name("author")).sendKeys("Safira");
        driver.findElement(By.name("productName")).sendKeys("Sabun");
        driver.findElement(By.name("productQuantity")).sendKeys("2");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.get(baseUrl + "/order/history");
        driver.findElement(By.name("author")).sendKeys("Safira");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.findElement(By.linkText("Pay")).click();
        driver.findElement(By.name("method")).sendKeys("CASH_ON_DELIVERY");
        driver.findElement(By.name("address")).sendKeys("Jl. Mawar No. 1");
        driver.findElement(By.name("deliveryFee")).sendKeys("10000");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Test
    void testPaymentAdminListPage(ChromeDriver driver) {
        createOrderAndPayment(driver);

        driver.get(baseUrl + "/payment/admin/list");

        assertTrue(driver.getPageSource().contains("Payment Admin List"));
        assertTrue(driver.getPageSource().contains("CASH_ON_DELIVERY"));
    }
}