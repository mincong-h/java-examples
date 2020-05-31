package io.mincong.ocpjp.regex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.util.Formatter;
import java.util.IllegalFormatConversionException;
import java.util.List;
import java.util.Locale;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * The following format describes how to format text and an object argument list. You can define a
 * combination of fixed text and one or more embedded format specifiers, to be passed to formatting
 * methods. The format specifier takes the form:
 *
 * <pre>
 * %[argument_index$][flags][width][.precision]conversion_char
 * </pre>
 *
 * <p>Specifier <i>argument index</i> is optional. It is a decimal integer indicating the position
 * of the argument in the argument list. The first argument is referenced by <tt>1$</tt>, the second
 * by <tt>2$</tt>, and so forth.
 *
 * <p>Specifier <i>flags</i> is optional. It is a set of characters that modify the output format.
 * The set of valid flags depends on the conversion.
 *
 * <p>Specifier <i>width</i> is optional. It is a non-negative decimal integer indicating the
 * minimum number of characters to be written to the output.
 *
 * <p>Specifier <i>precision</i> is optional. It is a non-negative decimal integer usually used to
 * restrict the number of characters. The specific behavior depends on the conversion.
 *
 * <p>Specifier <i>conversion char</i> is <b>required</b>. It is a character indicating how the
 * argument should be formatted. The set of valid conversions for a given argument depends on the
 * argument's data type.
 *
 * @author Mincong Huang
 */
public class FormattingTest {

  @Rule public final TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void formattingMethods() throws Exception {
    File file = tempFolder.newFile("test.txt");
    Formatter formatter = new Formatter(file);
    formatter.format("Learning %s!", "formatting");
    formatter.flush();

    List<String> lines = Files.readAllLines(file.toPath());
    assertThat(lines).containsExactly("Learning formatting!");

    String string = String.format("Learning %s!", "formatting");
    assertThat(string).isEqualTo("Learning formatting!");
  }

  @Test
  public void flags() throws Exception {
    String v;

    v = String.format("Left justify: '%-5d'.", 1);
    assertThat(v).isEqualTo("Left justify: '1    '.");

    v = String.format("Sign inclusion: %+d, %+d.", -1, 1);
    assertThat(v).isEqualTo("Sign inclusion: -1, +1.");

    v = String.format("Pad with zeros: %05d", 7);
    assertThat(v).isEqualTo("Pad with zeros: 00007");

    v = String.format(Locale.ENGLISH, "Grouping separator: %,d", 10_000);
    assertThat(v).isEqualTo("Grouping separator: 10,000");

    v = String.format("Enclose negative numbers: %(d, %(d", 1, -1);
    assertThat(v).isEqualTo("Enclose negative numbers: 1, (1)");
  }

  @Test
  public void argumentIndex() throws Exception {
    String v = String.format("%2$s %1$s", "last", "First");
    assertThat(v).isEqualTo("First last");
  }

  @Test
  public void width() throws Exception {
    String v = String.format("%3d", 1);
    assertThat(v).isEqualTo("  1");
  }

  @Test
  public void precision() throws Exception {
    String v = String.format("%.3f", 1.0);
    assertThat(v).isEqualTo("1.000");

    v = String.format("%.3f", 1.9999999);
    assertThat(v).isEqualTo("2.000");

    v = String.format("%6.3f", 1.111111);
    assertThat(v).isEqualTo(" 1.111");
  }

  @Test
  public void formattingParameterB() throws Exception {
    String v;

    v = String.format("String(%b), null(%b), boolean(%b).", "whatever", null, false);
    assertThat(v).isEqualTo("String(true), null(false), boolean(false).");

    v = String.format("String(%b), other args are ignored.", "whatever", null, false);
    assertThat(v).isEqualTo("String(true), other args are ignored.");

    v = String.format("True is %10b.", "Fix width");
    assertThat(v).isEqualTo("True is       true.");

    v = String.format("True is %.2b.", "Truncate");
    assertThat(v).isEqualTo("True is tr.");

    v = String.format("True is %-10b.", "Left justify");
    assertThat(v).isEqualTo("True is true      .");
  }

  /** <tt>%c</tt> outputs the result as a Unicode character. */
  @Test
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void formattingParameterC() throws Exception {
    String v;

    v = String.format("Char %c", '\u007b');
    assertThat(v).isEqualTo("Char {");

    v = String.format("Char %c", '\u6124');
    assertThat(v).isEqualTo("Char 愤");

    try {
      /*
       * Passing variables of type boolean, long, float, Boolean,
       * Long, Float, or any other class will throw
       * IllegalFormatConversionException.
       */
      String.format("Char %c", true);
      fail();
    } catch (IllegalFormatConversionException e) {
      assertThat(e).hasMessage("c != java.lang.Boolean");
    }
    //    Does not compile (invalid unicode):
    //    v = String.format("Char %c". '\affff');
  }

  @Test
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void formattingParameterDF() throws Exception {
    String v = String.format("%d", 123);
    assertThat(v).isEqualTo("123");

    try {
      String.format("%f", 123);
      fail();
    } catch (IllegalFormatConversionException e) {
      assertThat(e).hasMessage("f != java.lang.Integer");
    }
  }

  /**
   * <tt>%s</tt> is a general-purpose format specifier that can be applied to both primitive
   * variables and object references. For primitive values, the value will be displayed; for object
   * references, method {@code toString()} of the object is called.
   */
  @Test
  public void formattingParameterS() throws Exception {
    String[] a = {"A", "B"};
    String v = String.format("Hello %s %s %s", "world", 123, a);
    assertThat(v).matches("Hello world 123 \\[Ljava\\.lang\\.String;@\\p{Alnum}+");
  }
}
