package io.mincongh.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Integration test for Angular JS, which manipulates heavily the
 * DOM. The basic HTML Unit Driver does not seem to support this
 * correctly, so I switched to Firefox driver.
 * <p>
 * Prerequisite:
 * <ul>
 * <li>Install the latest version of Firefox, export to PATH
 * <li>Install the latest version of geckodriver, export to PATH
 * </ul>
 */
@Ignore("Only for demo purpose")
public class AngularJsIT {

  private WebDriver driver;

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    driver.get("https://angularjs.org/");
  }

  @After
  public void tearDown() throws Exception {
    driver.close();
  }

  @Test
  public void angularJS() throws Exception {
    WebElement div = driver.findElement(By.className("hero"));
    WebElement h2 = div.findElement(By.tagName("h2"));
    assertThat(h2.getText()).isEqualTo("AngularJS");
  }

}
