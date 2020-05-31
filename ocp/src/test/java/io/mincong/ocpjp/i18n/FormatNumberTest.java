package io.mincong.ocpjp.i18n;

import static org.junit.Assert.assertEquals;

import java.text.NumberFormat;
import java.util.Locale;
import org.junit.Ignore;
import org.junit.Test;

/** @author Mincong Huang */
public class FormatNumberTest {

  @Test
  @Ignore("JVM specific")
  public void formatNumber_defaultLocale() throws Exception {
    NumberFormat fmt = NumberFormat.getInstance();
    assertEquals("1,234.56", fmt.format(1_234.56D));
  }

  @Test
  public void formatNumber_specificLocale() throws Exception {
    NumberFormat fmt = NumberFormat.getInstance(Locale.GERMAN);
    assertEquals("1.234,56", fmt.format(1_234.56D));
  }

  @Test
  @Ignore("JVM specific")
  public void formatInteger_defaultLocale() throws Exception {
    NumberFormat fmt = NumberFormat.getIntegerInstance();
    assertEquals("1,235", fmt.format(1_234.56D));
  }

  @Test
  public void formatInteger_specificLocale() throws Exception {
    NumberFormat fmt = NumberFormat.getIntegerInstance(Locale.GERMAN);
    assertEquals("1.235", fmt.format(1_234.56D));
  }

  @Test
  @Ignore("JVM specific")
  public void formatPercentage_defaultLocale() throws Exception {
    NumberFormat fmt = NumberFormat.getPercentInstance();
    assertEquals("12%", fmt.format(0.123F));
  }

  @Test
  @Ignore("Oracle JDK specific")
  public void formatPercentage_specificLocale() throws Exception {
    NumberFormat fmt = NumberFormat.getPercentInstance(Locale.GERMAN);
    assertEquals("12%", fmt.format(0.123F));
  }
}
