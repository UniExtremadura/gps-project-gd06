import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HU10PresentacionAplicacionTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void presentacionAplicacion() {
        driver.get("https://uniextremadura.github.io/gps-project-gd06/index.html");
        driver.manage().window().setSize(new Dimension(1552, 832));
        driver.findElement(By.cssSelector(".nav-item:nth-child(2) > .nav-link")).click();
        driver.findElement(By.cssSelector(".nav-item:nth-child(1) > .nav-link")).click();
        driver.findElement(By.cssSelector(".nav-item:nth-child(3) > .nav-link")).click();
        driver.findElement(By.cssSelector(".nav-item:nth-child(1) > .nav-link")).click();
        driver.findElement(By.cssSelector(".nav-item:nth-child(4) > .nav-link")).click();
        driver.findElement(By.cssSelector(".nav-item:nth-child(1) > .nav-link")).click();
        driver.findElement(By.linkText("Contact Us")).click();
        driver.findElement(By.linkText("About Us")).click();
        driver.findElement(By.cssSelector("#customCarousel1 li:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#customCarousel1 li:nth-child(3)")).click();
        driver.findElement(By.cssSelector(".carousel-indicators > li:nth-child(4)")).click();
        driver.findElement(By.cssSelector("#customCarousel2 li:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#customCarousel2 li:nth-child(3)")).click();
        driver.findElement(By.cssSelector("input")).click();
        driver.findElement(By.cssSelector("input")).sendKeys("pato@pato");
        driver.findElement(By.cssSelector("form > button")).click();
    }
}
