package io.mincong.ocpjp.regex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * <i>Character classes</i> aren't classes defined in the Java API.
 * The term refers to a set of characters that you can enclose within
 * square brackets (<tt>[]</tt>). When used in a regex pattern, Java
 * looks for exactly <b>one</b> of the specified <i>characters</i>
 * (not words).
 *
 * @author Mincong Huang
 */
public class CharacterClassTest {

  /* Character classes */

  @Test
  public void simple1() throws Exception {
    String value = "Both organization and organisation are correct.";
    String regex = "organi[sz]ation";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("organization: [5,17[; organisation: [22,34[;");
  }

  @Test
  public void simple2() throws Exception {
    String value = "do go no";
    String regex  = "[dgn]o";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("do: [0,2[; go: [3,5[; no: [6,8[;");
  }

  @Test
  public void range() throws Exception {
    String value = "efg 789";
    String regex = "[a-f0-7]";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("e: [0,1[; f: [1,2[; 7: [4,5[;");
  }

  @Test
  public void negation() throws Exception {
    String value = "12345";
    String regex = "[^123]";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("4: [3,4[; 5: [4,5[;");
  }

  /* Predefined character classes */

  /**
   * Predefined character class '.' matches any character (may or may
   * not match line terminators).
   */
  @Test
  public void predefined_anyCharacter() throws Exception {
    String value = "sun son soon";
    String regex = "s.n";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("sun: [0,3[; son: [4,7[;");
  }

  /**
   * Predefined character class '\d' matches a digit.
   */
  @Test
  public void predefined_digit() throws Exception {
    String value = "666abc";
    String regex = "\\d";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("6: [0,1[; 6: [1,2[; 6: [2,3[;");
  }

  /**
   * Predefined character class '\D' matches a non-digit, which is
   * equivalent to "[^\d]".
   */
  @Test
  public void predefined_nonDigit() throws Exception {
    String value = "666a";
    String regex = "\\D";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("a: [3,4[;");
  }

  /**
   * Predefined character class '\s' matches a whitespace character,
   * including ' ' (space), '\t' (tab), '\n' (new line), 'x0B' (end
   * of line), '\f' (form feed), '\r' (carriage).
   */
  @Test
  public void predefined_whitespaceCharacter() throws Exception {
    String value = " \t\n\f\r";
    String regex = "\\s";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo(": [0,1[; \t: [1,2[; \n: [2,3[; \f: [3,4[; \r: [4,5[;");
  }

  /**
   * Predefined character class '\S' matches a non-whitespace
   * character, which is equivalent to "[^\s]".
   */
  @Test
  public void predefined_nonWhitespaceCharacter() throws Exception {
    String value = "M \t\n\f\r";
    String regex = "\\S";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("M: [0,1[;");
  }

  /**
   * Predefined character class '\w' matches a word character, which
   * is equivalent to "[a-zA-Z_0-9]".
   */
  @Test
  public void predefined_wordCharacter() throws Exception {
    String value = "~cd!";
    String regex = "\\w";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo("c: [1,2[; d: [2,3[;");
  }

  /**
   * Predefined character class '\W' matches a non-word character,
   * which is equivalent to "[^\w]".
   */
  @Test
  public void predefined_nonWordCharacter() throws Exception {
    String value = " Regex_2017";
    String regex = "\\W";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    assertThat(asString(matcher)).isEqualTo(": [0,1[;");
  }

  /**
   * Use a predefined character class and replace all the matching
   * occurrences with a literal strings.
   */
  @Test
  public void predefined_replaceAll() throws Exception {
    String value = "A?B$C";
    String regex = "\\W";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    String newValue = matcher.replaceAll(" ");
    assertThat(newValue).isEqualTo("A B C");
  }

  /* Utility method */

  private static String asString(Matcher matcher) {
    StringBuilder b = new StringBuilder();
    while (matcher.find()) {
      b.append(matcher.group());
      b.append(": [");
      b.append(matcher.start()); // Inclusive index
      b.append(',');
      b.append(matcher.end()); // Exclusive index
      b.append("[; ");
    }
    return b.toString().trim();
  }

}
