package io.mincong.ocpjp.i18n;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Locale;
import org.junit.Test;

/** @author Mincong Huang */
public class GreetingHelperTest {

  @Test
  public void getWelcome_existingLocales() throws Exception {
    assertThat(GreetingHelper.getWelcome(Locale.CHINESE)).isEqualTo("欢迎");
    assertThat(GreetingHelper.getWelcome(Locale.ENGLISH)).isEqualTo("Welcome");
    assertThat(GreetingHelper.getWelcome(Locale.FRENCH)).isEqualTo("Bienvenu");
  }

  @Test
  public void getWelcome_nonexistentLocales() throws Exception {
    // Fallback to English (default locale)
    assertThat(GreetingHelper.getWelcome(Locale.GERMAN)).isEqualTo("Welcome");
    assertThat(GreetingHelper.getWelcome(Locale.JAPANESE)).isEqualTo("Welcome");
  }
}
