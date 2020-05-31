package io.mincong.ocpjp.i18n;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.text.NumberFormat;
import java.util.Locale;
import org.junit.Test;

/** @author Mincong Huang */
public class FormatCurrencyTest {

  @Test
  public void en_US() throws Exception {
    NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
    assertThat(fmt.getCurrency().getCurrencyCode()).isEqualTo("USD");
    assertThat(fmt.format(1_234.56F)).isEqualTo("$1,234.56");
  }

  @Test
  public void en_GB() throws Exception {
    NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.UK);
    assertThat(fmt.getCurrency().getCurrencyCode()).isEqualTo("GBP");
    assertThat(fmt.format(1_234.56F)).isEqualTo("£1,234.56");
  }
}
