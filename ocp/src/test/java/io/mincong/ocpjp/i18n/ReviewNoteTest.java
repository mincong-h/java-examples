package io.mincong.ocpjp.i18n;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import org.junit.Ignore;
import org.junit.Test;

/**
 * <h1>Chapter 12: Localization</h1>
 *
 * <h2>Internationalization and localization</h2>
 * <p>
 * <ul>
 * <li>An internationalized application can be localized to different
 * regions.
 * <li>Internationalization is the process of designing an
 * application in a manner that it can be adapted to various locales.
 * <li>Localization is the process of adapting your software for a
 * locale by adding locale-specific components and translating text.
 * <li>Different locales might use different languages or formats of
 * currency, dates, and numbers.
 * <li>Advantages of localization: better user experience;
 * interpreting information in the right manner; adapting according
 * to culturally sensitive information
 * <li>Class {@link java.util.Locale} doesn't itself provide any
 * method to format the numbers, dates, or currencies. You use {@link
 * java.util.Locale} objects to pass locale-specific information to
 * other classes like {@link java.text.NumberFormat} or {@link
 * java.text.DateFormat} to format data.
 * <li>You can create and access objects of class {@link Locale} by
 * using: constructors of class {@link Locale}; {@link Locale}
 * methods; {@link Locale} constants; Class {@link Locale.Builder}.
 * <li>Overloaded constructors of {@link Locale}:
 * {@link Locale#(String)}; {@link Locale#(String, String)};
 * {@link Locale#(String, String, String)}.
 * <li>No exceptions are thrown if you pass incorrect or invalid
 * values to a {@link Locale} constructor.
 * <li>Language is a lowercase, two-letter code. Some of the commonly
 * used values are en (English), fr (French), de (German), it
 * (Italian), ja (Japanese), ko (Korean) and zh (Chinese).
 * <li>Country or region code is an uppercase, two-letter code. Some
 * of the commonly used values are US (United States), FR (France),
 * JP (Japan), DE (Germany), and CN (China).
 * <li>Variant is a vendor- or browser-specific code, such as WIN for
 * Windows, and MAC for Macintosh.
 * <li>Language is the most important parameter that you pass to a
 * {@link Locale} object. All overloaded constructors of
 * {@link Locale} accept language as their first parameter.
 * <li>You don't need to memorize all of the language or country
 * codes that are used to initialize a {@link Locale}. But the exam
 * expects you to be ware of commonly used values like EN, US, and
 * FR.
 * <li>You can access the current value of a JVM's default locale by
 * using class Locales's static method {@link Locale#getDefault()}.
 * <li>Class {@link Locale} defines {@link Locale} constants for a
 * region, a language, or both. Examples include {@link Locale#US},
 * {@link Locale#UK}, {@link Locale#ITALY}, {@link Locale#CHINESE},
 * and {@link Locale#GERMAN} for commonly used locales for languages
 * and countries.
 * <li>If you specify only a language constant to define a
 * {@link Locale}, its region remains undefined. Look out for exam
 * questions that print the region when you don't specify it during
 * the creation of a {@link Locale}.
 * <li>You can also use {@link Locale.Builder} to construct a {@link
 * Locale} by calling its constructor and then calling methods
 * {@link Locale.Builder#setLanguage(String)},
 * {@link Locale.Builder#setRegion(String)}, and
 * {@link Locale.Builder#build()}.
 * <li>To build locale-aware applications, first you must identify
 * and isolate localed-specific data, like currencies, date-time
 * format numbers, text messages, labels in a GUI applications,
 * sounds, colors, graphics, icons, phone numbers, measurements,
 * personal tiles, postal addresses, and so on.
 * <li>Instead of consuming and displaying this data directly,
 * locale-specific classes are used to display or format data
 * according to a selected locale.
 * </ul>
 * </p>
 *
 * <h2>Resource bundles</h2>
 * <p>
 * <ul>
 * <li>To implement resource bundles using property files, create
 * text files with the extension <tt>.properties</tt>. Each
 * <tt>.properties</tt> file is referred to as a resource bundle. All
 * the resource bundles are collectively referred to as a resource-
 * bundle family.
 * <li>An abstract class {@link java.util.ResourceBundle} represents
 * locale-specific resources.
 * <li>Locale-specific resources like text msg, the name and
 * location of images and icons, and labels for your GUI application
 * are starred in a resource bundle.
 * <li>Applications supporting multiple locales define locale-
 * specific information in multiple resource bundles, which form part
 * of a resource-bundle family.
 * <li>All these resource bundles share a common base name with
 * additional name components specifying the region, language, or
 * variant.
 * <li>You can implement resource bundles using either
 * <tt>.properties</tt> files or Java classes.
 * <li>To support countries and languages United States / English
 * (en_US) and France / French (fr_FR), an application (app) might
 * define the resource-bundle files as:
 * <ul>
 * <li><tt>app_en.properties</tt>,<tt>app_fr.properties</tt></li>
 * <li><tt>app_en_US.properties</tt>,<tt>app_fr_FR.properties</tt></li>
 * </ul>
 * <li>There;s no link between an application name and the resource
 * bundle base name. The following names for resource bundle files
 * are also valid for the previous notes:
 * <ul>
 * <li><tt>msg_en.properties</tt>,<tt>msg_fr.properties</tt></li>
 * <li><tt>msg_en_US.properties</tt>,<tt>msg_fr_FR.properties</tt></li>
 * </ul>
 * <li>All the <tt>properties</tt> files in a bundle contain the same
 * keys, with different values.
 * <li>Here's the code to load the locale-specific resource bundle
 * from the resource-bundle family for a locale, see
 * {@link #getBundleOfLocale()}.
 * <li>The static method {@link ResourceBundle#getBundle(String)}
 * accepts the location of the resource-bundle family as the first
 * argument and {@link Locale} as the second arguments, and loads the
 * single resource bundle, not the complete family.
 * <li>You can call different methods from class
 * {@link ResourceBundle} to access its keys and values.
 * <li>You can also define locale-specific data in resource bundles
 * by defining them as subclasses of {@link
 * java.util.ListResourceBundle}, a subclass of abstract class {@link
 * ResourceBundle}
 * <li>When your class cannot load the specified resources bundle due
 * to an incorrect name of the resource-bundle family or because it
 * cannot locate it, it will throw a runtime exception.
 * <li>Given a {@link Locale}, here's the order in which Java
 * searches for a matching resource bundle:
 * <ol>
 * <li><tt>bundleName_localeLanguage_localeCountry_localeVariant</tt>
 * <li><tt>bundleName_localeLanguage_localeCountry</tt>
 * <li><tt>bundleName_localeLanguage</tt>
 * <li><tt>bundleName_defaultLanguage_defaultCountry_localeVariant</tt>
 * <li><tt>bundleName_defaultLanguage_defaultCountry</tt>
 * <li><tt>bundleName_defaultLanguage</tt>
 * <li><tt>bundleName</tt>
 * </ol>
 * </ul>
 * </p>
 *
 * <h2>Formatting dates, numbers, and currencies for locales</h2>
 * <p>
 * <ul>To format or parse numbers, use {@link NumberFormat} and
 * {@link DecimalFormat} classes.
 * <li>To format or parse currencies, use {@link NumberFormat} class.
 * <li>Dates, use {@link DateFormat}, {@link SimpleDateFormat},
 * {@link Date} and {@link Calendar} classes.
 * <li>{@link NumberFormat} and {@link DateFormat} are abstract
 * class. Class {@link DecimalFormat} extends class {@link
 * NumberFormat}. Class {@link SimpleDateFormat} extends class
 * {@link DateFormat}.
 * <li>The static method {@link NumberFormat#getInstance()} returns
 * an object of class {@link DecimalFormat}.
 * <li>The static method {@link DateFormat#getInstance()} returns an
 * object of class {@link SimpleDateFormat}.
 * <li>To format numbers or parse string values, use {@link
 * NumberFormat}.
 * <li>To format currency, use {@link NumberFormat}.
 * <li>To format dates, use {@link DateFormat}.
 * <li>Use class {@link SimpleDateFormat} for complete control of
 * the formatting style of date and time.
 * <li>Create a {@link SimpleDateFormat} object by calling its
 * constructors, passing it a formatting pattern as a string,
 * together with a {@link Locale}.
 * <li>To create {@link Date} objects with a specific date, use
 * class {@link Calendar}. Call {@link Calendar#getInstance()} and
 * then its method <tt>set</tt>.
 * </ul>
 * </p>
 *
 * @author Mincong Huang
 */
public class ReviewNoteTest {

  /** GMT: Sunday, 26 November 2017 00:00:00 */
  private static final long TIMESTAMP_0 = 1511654400_000L;

  /** GMT: Sunday, 26 November 2017 10:15:49 */
  private static final long TIMESTAMP = 1511691349_000L;

  @Test
  public void getBundleOfLocale() throws Exception {
    ResourceBundle labels = ResourceBundle.getBundle("num.msg", Locale.FRANCE);
    assertThat(labels.getString("one")).isEqualTo("Un");
  }

  @Test
  public void subclassOfListResourceBundle() throws Exception {
    ResourceBundle resources = new MyResources_fr();
    assertThat(resources.getString("s1")).isEqualTo("Merci");
    assertThat(resources.getString("s2")).isEqualTo("Coucou");
  }

  public static class MyResources_fr extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
      return new Object[][]{
          {"s1", "Merci"},
          {"s2", "Coucou"}
      };
    }
  }

  @Test(expected = MissingResourceException.class)
  public void cannotLocateBundle() throws Exception {
    ResourceBundle.getBundle("non.existent", Locale.FRANCE);
  }

  @Test
  public void formatNumbers() throws Exception {
    NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.GERMAN);
    assertThat(numberFormat.format(10_000L)).isEqualTo("10.000");

    numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
    assertThat(numberFormat.format(10_000.12)).isEqualTo("10.000,12");

    numberFormat = DecimalFormat.getNumberInstance(Locale.GERMAN);
    assertThat(numberFormat.format(10_000L)).isEqualTo("10.000");
  }

  @Test
  public void parseNumbers() throws Exception {
    NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.GERMAN);
    assertThat(numberFormat.parse("10.000")).isEqualTo(10_000L);

    numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
    assertThat(numberFormat.parse("10.000,12")).isEqualTo(10_000.12);

    numberFormat = DecimalFormat.getNumberInstance(Locale.GERMAN);
    assertThat(numberFormat.parse("10.000")).isEqualTo(10_000L);
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatCurrencies() throws Exception {
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
    assertThat(numberFormat.format(10_000L)).isEqualTo("10 000,00 €");
  }

  @Test
  @Ignore("OracleJDK specific")
  public void parseCurrencies() throws Exception {
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
    assertThat(numberFormat.parse("10 000,00 €")).isEqualTo(10_000L);
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDate_DateFormat() throws Exception {
    Date date = new Date(TIMESTAMP);

    // Date-only (year, month, and day-of-month)
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    assertThat(dateFormat.format(date)).isEqualTo("Nov 26, 2017");

    // Date-time
    dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG, Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    assertThat(dateFormat.format(date)).isEqualTo("Nov 26, 2017 10:15:49 AM UTC");
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatCalendar_DateFormat() throws Exception {
    Calendar calendar = newCalendar();

    // Date-only (year, month, and day-of-month)
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
    dateFormat.setTimeZone(calendar.getTimeZone());
    assertThat(dateFormat.format(calendar.getTime())).isEqualTo("Nov 26, 2017");

    // Date-time
    dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG, Locale.US);
    dateFormat.setTimeZone(calendar.getTimeZone());
    assertThat(dateFormat.format(calendar.getTime())).isEqualTo("Nov 26, 2017 10:15:49 AM UTC");
  }

  @Test
  @Ignore("OracleJDK specific")
  public void parseDate_DateFormat() throws Exception {
    // Date-only (year, month, and day-of-month)
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    assertThat(dateFormat.parse("Nov 26, 2017")).hasTime(TIMESTAMP_0);

    // Date-time
    dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG, Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    assertThat(dateFormat.parse("Nov 26, 2017 10:15:49 AM UTC")).hasTime(TIMESTAMP);
  }

  @Test
  public void formatDate_SimpleDateFormat() throws Exception {
    Date date = new Date(TIMESTAMP);
    SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
    iso.setTimeZone(TimeZone.getTimeZone("UTC"));
    assertThat(iso.format(date)).isEqualTo("2017-11-26T10:15:49.000+0000");
  }

  @Test
  public void formatCalendar_SimpleDateFormat() throws Exception {
    Calendar calendar = newCalendar();

    SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
    iso.setTimeZone(calendar.getTimeZone());
    assertThat(iso.format(calendar.getTime())).isEqualTo("2017-11-26T10:15:49.000+0000");
  }

  @Test
  public void parseDate_SimpleDateFormat() throws Exception {
    SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
    iso.setTimeZone(TimeZone.getTimeZone("UTC"));
    assertThat(iso.parse("2017-11-26T10:15:49.000+0000")).hasTime(TIMESTAMP);
  }

  private static Calendar newCalendar() {
    Calendar calendar = Calendar.getInstance(Locale.US);
    calendar.set(2017, Calendar.NOVEMBER, 26, 10, 15, 49);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
    return calendar;
  }

}
