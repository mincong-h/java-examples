package io.mincong.ocpjp.i18n;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Locale.Builder;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests different points about internationalization (i18n) and
 * localization (l10n).
 * <p>
 * Class {@link Locale} does not itself provide any method to format
 * the numbers, dates, or currencies. YOu use {@link Locale} objects
 * to pass locale-specific information to other classes like {@link
 * java.text.NumberFormat} or {@link java.text.DateFormat} to format
 * data.
 * <p>
 * Language is the most important parameter that you pass to a {@link
 * Locale} object. All overloaded constructors of {@link Locale}
 * accept language as their first parameter.
 * <p>
 * Valid values that cen be passed to language construction:
 * <ul>
 * <li>Language is a lowercase, two-letter code. Some of the commonly
 * used values are <tt>en</tt> (English), <tt>fr</tt> (French), <tt>
 * de</tt> (German), <tt>it</tt> (Italian), <tt>ja</tt> (Japanese),
 * <tt>ko</tt> (Korean), and <tt>ch</tt> (Chinese).
 * <li>Country or region code is an uppercase, two-letter code or
 * three numbers.
 * <li>Variant is a vendor- or browser-specific code, such as <tt>WIN
 * </tt> for Windows and <tt>MAC</tt> for Macintosh.
 * </ul>
 *
 * @author Mincong Huang
 */
public class TestI18n {

  @Test
  @Ignore("Failed at JDK 14")
  public void numberFormat() throws Exception {
    float f = 1234.999F;
    assertEquals("1,234.999", String.format(Locale.US, "%,.3f", f));
    assertEquals("1 234,999", String.format(Locale.FRANCE, "%,.3f", f));
    assertEquals("1.234,999", String.format(Locale.GERMANY, "%,.3f", f));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void dateFormat() throws Exception {
    LocalDate d = LocalDate.of(2017, 11, 9);
    DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    assertEquals("09/11/17", fmt.withLocale(Locale.FRANCE).format(d));
    assertEquals("09/11/17", fmt.withLocale(Locale.UK).format(d));
    assertEquals("11/9/17", fmt.withLocale(Locale.US).format(d));
  }

  @Test
  public void createLocaleObjects_usingConstructors() throws Exception {
    assertEquals(Locale.FRENCH, new Locale("fr"));
    assertEquals(Locale.FRENCH, new Locale("fr", ""));
    assertEquals(Locale.FRANCE, new Locale("fr", "FR"));
  }

  @Test
  public void createLocaleObjects_usingBuilder() throws Exception {
    // Java 7+
    Locale.Builder b = new Builder();
    b.setLanguage("fr");
    b.setRegion("FR");
    assertEquals(Locale.FRANCE, b.build());
  }

  @Test
  @Ignore("JVM specific")
  public void getInfoAboutLocale() throws Exception {
    Locale fr = Locale.FRANCE;
    assertThat(fr.getCountry()).isEqualTo("FR");

    // Invocation of the methods `getDisplay*` display the values in
    // the default locale associated to JVM.
    assertThat(fr.getDisplayCountry()).isEqualTo("France");
    assertThat(fr.getDisplayLanguage()).isEqualTo("French");
    assertThat(fr.getDisplayName()).isEqualTo("French (France)");

    assertThat(fr.getDisplayCountry(Locale.FRENCH)).isEqualTo("France");
    assertThat(fr.getDisplayLanguage(Locale.FRENCH)).isEqualTo("français");
    assertThat(fr.getDisplayName(Locale.FRENCH)).isEqualTo("français (France)");
  }

}
