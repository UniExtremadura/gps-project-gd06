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

public class HU11ExperienciasDeUsuarioTest {
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
  public void experienciasdeusuario() {
    driver.get("http://uniextremadura.github.io/gps-project-gd06/testimonial.html");
    driver.manage().window().setSize(new Dimension(1936, 1048));
    driver.findElement(By.cssSelector(".nav-item:nth-child(1) > .nav-link")).click();
    driver.findElement(By.cssSelector(".nav-item:nth-child(3) > .nav-link")).click();
    driver.findElement(By.cssSelector(".nav-item:nth-child(2) > .nav-link")).click();
    driver.findElement(By.cssSelector(".nav-item:nth-child(3) > .nav-link")).click();
    driver.findElement(By.cssSelector(".nav-item:nth-child(4) > .nav-link")).click();
    driver.findElement(By.cssSelector(".nav-item:nth-child(3) > .nav-link")).click();
    driver.findElement(By.cssSelector(".container > .col-md-10")).click();
    driver.findElement(By.cssSelector(".col-md-10 input:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".col-md-10 input:nth-child(1)")).sendKeys("Pato");
    driver.findElement(By.cssSelector("input:nth-child(2)")).click();
    driver.findElement(By.cssSelector("input:nth-child(2)")).sendKeys("Pato@pato");
    driver.findElement(By.cssSelector(".form-control")).click();
    driver.findElement(By.cssSelector(".form-control")).sendKeys("Soy un pato al que le gustan los patos y esta apllicacion");
    driver.findElement(By.cssSelector("button:nth-child(4)")).click();
  }
}
