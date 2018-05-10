package io.mincong.ocpjp.regex;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Searching, parsing, and building strings.
 *
 * @author Mincong Huang
 */
public class StringTest {

  private static final String SENTENCE = "bla bla bla...";

  @Rule
  public final ExpectedException expectedEx = ExpectedException.none();

  /**
   * Search forward
   */
  @Test
  public void indexOf() throws Exception {
    assertThat(SENTENCE.indexOf('b', -1)).isEqualTo(0);
    assertThat(SENTENCE.indexOf('b', 0)).isEqualTo(0);
    assertThat(SENTENCE.indexOf('b')).isEqualTo(0);
    assertThat(SENTENCE.indexOf('b', 1)).isEqualTo(4);
    assertThat(SENTENCE.indexOf('b', 10)).isEqualTo(-1);
  }

  /**
   * Search backward
   */
  @Test
  public void lastIndexOf() throws Exception {
    assertThat(SENTENCE.lastIndexOf('b', -1)).isEqualTo(-1);
    assertThat(SENTENCE.lastIndexOf('b', 0)).isEqualTo(0);
    assertThat(SENTENCE.lastIndexOf('b')).isEqualTo(8);
    assertThat(SENTENCE.lastIndexOf('b', 3)).isEqualTo(0);
    assertThat(SENTENCE.lastIndexOf('b', 10)).isEqualTo(8);
  }

  @Test
  public void contains() throws Exception {
    assertThat(SENTENCE.contains("bla")).isTrue();
  }

  @Test
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void subSequence_substring() throws Exception {
    CharSequence actual;

    actual = SENTENCE.subSequence(0, 3);
    assertThat(actual).isEqualTo("bla"); // End index 3 is exclusive.

    actual = SENTENCE.substring(4);
    assertThat(actual).isEqualTo("bla bla...");

    actual = SENTENCE.substring(8, 14);
    assertThat(actual).isEqualTo("bla...");

    /*
     * Unlike methods `indexOf(int)` and `lastIndexOf(int)`, methods
     * `subSequence(int, int)`, `substring(int)`, and
     * `substring(int, int)` throw the runtime exception
     * `StringIndexOutOfBoundsException` for invalid start and end
     * positions.
     */
    expectedEx.expect(StringIndexOutOfBoundsException.class);
    SENTENCE.substring(8, 15);
  }

  /**
   * <i>Tokenizing</i> is the process of splitting a string, based on
   * a separator, into tokens. A separator can be a character, text,
   * or a regex. For example:
   *
   * <pre>
   * Value:   |f|l|a|t| |c|a|t| |r|e|d|
   *             ^ ^ ^   ^ ^ ^
   * Token 0: |f|
   * Token 1:         | |
   * Token 2:                 | |r|e|d|
   * </pre>
   *
   * You might also want to limit the number of tokens using the
   * overloaded method {@link String#split(String, int)}. For
   * example, we can set the limit=2:
   *
   * <pre>
   * Value:   |f|l|a|t| |c|a|t| |r|e|d|
   *             ^ ^ ^
   * Token 0: |f|
   * Token 1:         | |c|a|t| |r|e|d|
   * </pre>
   */
  @Test
  public void split() throws Exception {
    // Without limit for the number of tokens
    String[] tokens = "flat cat red".split(".at");
    assertThat(tokens).containsExactly("f", " ", " red");

    // Limit the number of tokens to 2
    String[] limited = "flat cat red".split(".at", 2);
    assertThat(limited).containsExactly("f", " cat red");
  }

  @Test
  public void replace() throws Exception {
    String str = "He? world. He? world.".replace("?", "llo");
    assertThat(str).isEqualTo("Hello world. Hello world.");
  }

  @Test
  public void replaceAll() throws Exception {
    /*
     * Unlike `replace()`, `replaceAll()` does not accept method
     * parameters of type `CharSequence`.
     */
    String str = "Hi world. Hey world.".replaceAll("\\bH\\p{Alpha}+\\b", "Hello");
    assertThat(str).isEqualTo("Hello world. Hello world.");
  }

  @Test
  public void exercise() throws Exception {
    String answerA = "cat cap copp".replaceAll("c.p\\b", "()");
    assertThat(answerA).isEqualTo("cat () copp");

    String answerD = "cat cap copp".replaceFirst("c.p\\b", "()");
    assertThat(answerD).isEqualTo("cat () copp");

    // Method replace() does not support regex.
    String answerE = "cat cap copp".replace("c.p", "()");
    assertThat(answerE).isEqualTo("cat cap copp");
  }

  @Test
  public void endsWith() throws Exception {
    assertThat(SENTENCE.startsWith("bla")).isTrue();
    assertThat(SENTENCE.startsWith("bla", 4)).isTrue();
    assertThat(SENTENCE.startsWith("bla", 8)).isTrue();
    assertThat(SENTENCE.startsWith("bla", -1)).isFalse();
    assertThat(SENTENCE.startsWith("bla", SENTENCE.length())).isFalse();
    assertThat(SENTENCE.endsWith("...")).isTrue();

    assertThat("B".compareTo("A")).isPositive();
    assertThat("B".compareTo("B")).isZero();
    assertThat("B".compareTo("C")).isNegative();
    assertThat("B".compareTo("b")).isNegative();
    assertThat("B".compareToIgnoreCase("b")).isZero();

    assertThat("Hello".equals("Hello")).isTrue();
    assertThat("Hello".equals("hello")).isFalse();
    assertThat("Hello".equalsIgnoreCase("hello")).isTrue();
  }

}
