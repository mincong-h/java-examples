package io.mincongh.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Note: currently the tests can only be executed via command-line.
 * It doesn't work on IntelliJ IDEA.
 *
 * @author Mincong Huang
 */
public class SeleniumQuickStartIT {

  /**
   * Artifact name defined by Maven POM in &lt;build&gt; section.
   */
  private static String WAR_NAME = System.getProperty("warName");

  private static String BASE_URL = "http://localhost:8080/" + WAR_NAME + '/';

  private WebDriver driver;

  @BeforeClass
  public static void setUpBeforeAll() throws Exception {
    assertThat(WAR_NAME).as("Property `warName` is null.").isNotNull();
  }

  @Before
  public void setUp() throws Exception {
    driver = new HtmlUnitDriver();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Test
  public void h1() throws Exception {
    driver.get(BASE_URL);
    WebElement element = driver.findElement(By.tagName("h1"));
    assertThat(element.getText()).isEqualTo("Welcome to Selenium");
  }

  @Test
  public void title() throws Exception {
    driver.get(BASE_URL);
    assertThat(driver.getTitle()).isEqualTo("Selenium Tests");
  }

  @Test
  public void locale_enGB() throws Exception {
    testLocale(Locale.UK.toLanguageTag());
  }

  @Test
  public void locale_frFR() throws Exception {
    testLocale(Locale.FRANCE.toLanguageTag());
  }

  private void testLocale(String languageTag) {
    DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
    capabilities.setCapability(HtmlUnitDriver.BROWSER_LANGUAGE_CAPABILITY, languageTag);
    driver = new HtmlUnitDriver(capabilities);
    driver.get(BASE_URL);

    WebElement element = driver.findElement(By.id("locale"));
    assertThat(element.getText()).isEqualTo(languageTag);
  }

}
