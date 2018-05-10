package io.mincong.ocpjp.regex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * Tests for review notes: the main points covered in this chapter.
 *
 * @author Mincong Huang
 */
public class ReviewNoteTest {

  /* Regular expressions */

  /**
   * A regex has a syntax, which can be defined by using regular and
   * special characters.
   * <p>
   * As opposed to exact matches, you can use regex to search for
   * data that matches a pattern.
   * <p>
   * Class {@link Pattern} is a compiled representation of a regular
   * expression. It doesn't define a public constructor. You can
   * instantiate this class by using its factory method
   * {@link Pattern#compile(String)}.
   */
  @Test
  public void regexCharacters() throws Exception {
    // Using a regular character
    List<Integer> startIndexes = new ArrayList<>();
    Pattern pattern = Pattern.compile("c");
    Matcher matcher = pattern.matcher("coucou");
    while (matcher.find()) {
      startIndexes.add(matcher.start());
    }
    assertThat(startIndexes).containsExactly(0, 3);

    // Using a spacial character
    startIndexes = new ArrayList<>();
    pattern = Pattern.compile("6+");
    matcher = pattern.matcher("6 66 666");
    while (matcher.find()) {
      startIndexes.add(matcher.start());
    }
    assertThat(startIndexes).containsExactly(0, 2, 5);
  }

  /**
   * Character classes aren't classes defined in the Java API. The
   * term refers to a set of characters that you an enclose within
   * square brackets <tt>[]</tt>.
   */
  @Test
  public void characterClass() throws Exception {
    Pattern pattern = Pattern.compile("[abc]");
    Matcher matcher = pattern.matcher("a b 1 2");
    List<String> results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'a': [0,1[", "'b': [2,3[");
  }

  /**
   * Java supports predefined and custom character classes.
   */
  @Test
  public void predefinedCharacterClass() throws Exception {
    Pattern pattern = Pattern.compile("\\p{Alnum}");
    Matcher matcher = pattern.matcher("a b 1 2");
    List<String> results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'a': [0,1[", "'b': [2,3[", "'1': [4,5[", "'2': [6,7[");
  }

  /**
   * You create a custom character class by enclosing a set of
   * characters within square brackets <tt>[]</tt>:
   * <ul>
   * <li><tt>[fdn]</tt> can be used to find an exact match of 'f',
   * 'd', or 'n'.
   * <li><tt>[^fdn]</tt> can be used to find a character that doesn't
   * match either 'f', 'd', or 'n'.
   * <li><tt>[a-cA-C]</tt> can be used to find an exact match of
   * either 'a', 'b', 'c', 'A', 'B', or 'C'.
   * </ul>
   */
  @Test
  public void customCharacterClass() throws Exception {
    Pattern pattern;
    Matcher matcher;
    List<String> results;

    // Character class: [fdn]
    pattern = Pattern.compile("[fnd]");
    matcher = pattern.matcher("found");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'f': [0,1[", "'n': [3,4[", "'d': [4,5[");

    // Character class: [^fdn]
    pattern = Pattern.compile("[^fnd]");
    matcher = pattern.matcher("found");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'o': [1,2[", "'u': [2,3[");

    // Character class: [a-cA-C]
    pattern = Pattern.compile("[a-cA-C]");
    matcher = pattern.matcher("found");
    results = MatcherHelper.collect(matcher);
    assertThat(results).isEmpty();
  }

  /**
   * You can use these predefined character classes as follows:
   *
   * <ul>
   * <li>A dot matches any character (and may or may not match line
   * terminators).</li>
   * <li>'\d' matches any digit: <tt>[0-9]</tt>.
   * <li>'\D' matches a non-digit: <tt>[^0-9]</tt>.
   * <li>'\s' matches a whitespace character: ' ' (space), '\t'
   * (tab), '\n' (new line), '\x0B' (end of line), '\f' (form feed),
   * '\r' (carriage).
   * <li>'\S' matches a non-whitespace character: <tt>[^\s]</tt>.
   * <li>'\w' matches a word character: <tt>[a-zA-Z_0-9]</tt>.
   * <li>'\W' matches a non-word character: <tt>[^\w]</tt>.
   * </ul>
   */
  @Test
  public void predefinedCharacterClass2() throws Exception {
    Pattern pattern;
    Matcher matcher;
    List<String> results;

    pattern = Pattern.compile(".");
    matcher = pattern.matcher("_a 1B");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'_': [0,1[",
        "'a': [1,2[",
        "' ': [2,3[",
        "'1': [3,4[",
        "'B': [4,5["
    );

    pattern = Pattern.compile("\\d");
    matcher = pattern.matcher("_a 1B");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'1': [3,4[");

    pattern = Pattern.compile("\\D");
    matcher = pattern.matcher("_a 1B");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'_': [0,1[",
        "'a': [1,2[",
        "' ': [2,3[",
        "'B': [4,5["
    );

    pattern = Pattern.compile("\\s");
    matcher = pattern.matcher("_a 1B");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("' ': [2,3[");

    pattern = Pattern.compile("\\S");
    matcher = pattern.matcher("_a 1B");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'_': [0,1[",
        "'a': [1,2[",
        "'1': [3,4[",
        "'B': [4,5["
    );

    pattern = Pattern.compile("\\w");
    matcher = pattern.matcher("_a 1B");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'_': [0,1[",
        "'a': [1,2[",
        "'1': [3,4[",
        "'B': [4,5["
    );

    pattern = Pattern.compile("\\W");
    matcher = pattern.matcher("_a 1B");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("' ': [2,3[");
  }

  /**
   * Boundary matchers:
   *
   * <ul>
   * <li>'\b' indicates a word boundary.
   * <li>'\B' indicates a non-word boundary.
   * <li>'^' indicates the beginning of a line.
   * <li>'$' indicates the end of a line.
   * </ul>
   */
  @Test
  public void boundaryMatchers() throws Exception {
    Pattern pattern;
    Matcher matcher;
    List<String> results;

    pattern = Pattern.compile("\\b");
    matcher = pattern.matcher("Hello world!");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'': [0,0[",
        "'': [5,5[",
        "'': [6,6[",
        "'': [11,11["
    );

    pattern = Pattern.compile("\\B");
    matcher = pattern.matcher("Hello world!");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'': [1,1[",
        "'': [2,2[",
        "'': [3,3[",
        "'': [4,4[",
        "'': [7,7[",
        "'': [8,8[",
        "'': [9,9[",
        "'': [10,10[",
        "'': [12,12["
    );

    pattern = Pattern.compile("^", Pattern.MULTILINE);
    matcher = pattern.matcher("Line1\nLine2\n");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'': [0,0[", "'': [6,6[");

    pattern = Pattern.compile("$", Pattern.MULTILINE);
    matcher = pattern.matcher("Line1\nLine2\n");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'': [5,5[", "'': [11,11[", "'': [12,12[");
  }

  /**
   * You can specify the number of occurrences of a pattern to match
   * in a target value by using quantifiers.
   * <p>
   * The coverage of quantifiers on this exam is limited to the
   * following greedy quantifiers:
   *
   * <ul>
   * <li><tt>X?</tt> matches X, once or not at all.
   * <li><tt>X*</tt> matches X, zero or more times.
   * <li><tt>X+</tt> matches X, one or more times.
   * <li><tt>X{min, max}</tt> matches X, within the specified range.
   * </ul>
   */
  @Test
  public void specifyNbToMatch() throws Exception {
    Pattern pattern;
    Matcher matcher;
    List<String> results;

    pattern = Pattern.compile("6? ");
    matcher = pattern.matcher(" 6 66 666 ");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "' ': [0,1[",
        "'6 ': [1,3[",
        "'6 ': [4,6[",
        "'6 ': [8,10["
    );

    pattern = Pattern.compile("6* ");
    matcher = pattern.matcher(" 6 66 666 ");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "' ': [0,1[",
        "'6 ': [1,3[",
        "'66 ': [3,6[",
        "'666 ': [6,10["
    );

    pattern = Pattern.compile("6+ ");
    matcher = pattern.matcher(" 6 66 666 ");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'6 ': [1,3[",
        "'66 ': [3,6[",
        "'666 ': [6,10["
    );

    pattern = Pattern.compile("6{2,3} ");
    matcher = pattern.matcher(" 6 66 666 ");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'66 ': [3,6[",
        "'666 ': [6,10["
    );
  }

  /**
   * Regex in Java supports Unicode, as it matches against the
   * {@link CharSequence} objects.
   * <p>
   * Class {@link Matcher} is referred to as an engine that scans a
   * target {@link CharSequence} for a matching regex pattern. Class
   * {@link Matcher} doesn't define a public constructor. You can
   * create and access a {@link Matcher} object by calling the
   * instance method {@link Pattern#matcher(CharSequence)} on an
   * object of class {@link Pattern}.
   * <p>
   * When you have access to the {@link Matcher} object, you can
   * match a complete input sequence against a pattern, match the
   * input sequence starting at the beginning, find multiple
   * occurrences of the matching pattern, or retrieve information
   * about the matching groups.
   */
  @Test
  public void unicodeSupport() throws Exception {
    Pattern pattern;
    Matcher matcher;
    List<String> results;

    pattern = Pattern.compile("\\p{IsIdeographic}");
    matcher = pattern.matcher("好（Tian）好（Tian）学（Jia）习（Ban）");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'好': [0,1[",
        "'好': [7,8[",
        "'学': [14,15[",
        "'习': [20,21["
    );

    pattern = Pattern.compile("[^\\p{IsIdeographic}]+");
    matcher = pattern.matcher("好（Tian）好（Tian）学（Jia）习（Ban）");
    results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly(
        "'（Tian）': [1,7[",
        "'（Tian）': [8,14[",
        "'（Jia）': [15,20[",
        "'（Ban）': [21,26["
    );
  }

  /* Search, parse, and build strings */

  /**
   * Method {@link String#indexOf(int)} and its overloaded methods
   * return the first matching position of a character or string,
   * starting from the specified position of this string, or from its
   * beginning.
   */
  @Test
  public void indexOf() throws Exception {
    assertThat("Hello world!".indexOf('o')).isEqualTo(4);
    assertThat("Hello world!".indexOf('o', -1)).isEqualTo(4);
    assertThat("Hello world!".indexOf('o', 4)).isEqualTo(4); // `fromIndex` is inclusive.
    assertThat("Hello world!".indexOf('o', 5)).isEqualTo(7);
    assertThat("Hello world!".indexOf('o', 100)).isEqualTo(-1); // Not found; no exception.

    assertThat("Hello world!".indexOf("world")).isEqualTo(6);
    assertThat("Hello world!".indexOf("world", -1)).isEqualTo(6);
    assertThat("Hello world!".indexOf("world", 6)).isEqualTo(6);
    assertThat("Hello world!".indexOf("world", 7)).isEqualTo(-1);
  }

  /**
   * Method {@link String#lastIndexOf(int)} and its overloaded
   * methods return the last <b>matching</b> position of a character
   * in the entire string, or its subset (position 0 to the specified
   * position).
   * <p>
   * Note that <tt>indexOf()</tt> and <tt>lastIndexOf()</tt> differ
   * in the manner that they search a target string—<tt>indexOf()</tt>
   * searches in increasing position numbers and
   * <tt>lastIndexOf()</tt> searches backward. Due to this
   * difference, <tt>indexOf('a', -100)</tt> will search the complete
   * string, but <tt>lastIndexOf('a', -100)</tt> won't. In a similar
   * manner, because <tt>lastIndexOf()</tt> searches backwards,
   * <tt>lastIndexOf('a', 100)</tt> will search the string, but
   * <tt>lastIndexOf('a', 0)</tt> or <tt>lastIndexOf('a', -100)</tt>
   * won't.
   * <p>
   * Methods <tt>indexOf()</tt> and <tt>lastIndexOf()</tt> don't
   * throw a compilation error or runtime exception if the search
   * position is negative or greater than the length of the string.
   * If no match is found, they return -1.
   */
  @Test
  public void lastIndexOf() throws Exception {
    assertThat("Hello world!".lastIndexOf('o')).isEqualTo(7);
    assertThat("Hello world!".lastIndexOf('o', 7)).isEqualTo(7); // `fromIndex` is inclusive.
    assertThat("Hello world!".lastIndexOf('o', 6)).isEqualTo(4);
    assertThat("Hello world!".lastIndexOf('o', -100)).isEqualTo(-1); // Not found, no exception.

    assertThat("Hello world!".lastIndexOf("world")).isEqualTo(6);
    assertThat("Hello world!".lastIndexOf("world", 6)).isEqualTo(6);
    assertThat("Hello world!".lastIndexOf("world", 5)).isEqualTo(-1);
  }

  /**
   * Method {@link String#contains(CharSequence)} searches for an
   * exact match in this string. Because <tt>contains()</tt> accepts
   * a method parameter of interface {@link CharSequence}, you can
   * pass to it both a {@link String} and a {@link StringBuilder}
   * object.
   */
  @Test
  public void contains() throws Exception {
    assertThat("Hello world!".contains("Hello")).isTrue();
    assertThat("Hello world!".contains(new StringBuilder("world"))).isTrue();
  }

  /**
   * Method {@link String#subSequence(int, int)} and
   * {@link String#substring(int)} accept <tt>int</tt> parameters and
   * return a substring of the target string.
   * <p>
   * Method <tt>substring()</tt> defines overloaded versions, which
   * accept one or two <tt>int</tt> method parameters and return a
   * substring of the target string.
   * <p>
   * The name of methods <tt>subSequence()</tt>, <tt>substring()</tt>
   * can be used to determine their return type.
   * <ul>
   * <li><tt>subSequence()</tt> returns {@link CharSequence}
   * <li><tt>substring()</tt> returns {@link String}
   * </ul>
   */
  @Test
  public void subSequenceAndSubstring() throws Exception {
    String str = "周六";
    StringBuilder sb = new StringBuilder("周日");

    // String#subSequence(int, int)
    assertThat(str.subSequence(0, 1)).isEqualTo("周");
    assertThat(sb.subSequence(0, 1)).isEqualTo("周");

    // String#substring(int, int)
    assertThat(str.substring(0, 1)).isEqualTo("周");
    assertThat(sb.substring(0, 1)).isEqualTo("周");

    // String#substring(int)
    assertThat(str.substring(1)).isEqualTo("六");
    assertThat(sb.substring(1)).isEqualTo("日");
  }

  /**
   * Method <tt>split()</tt> searches for a matching regex pattern
   * and split a {@link String} into an array of string values.
   */
  @Test
  public void split() throws Exception {
    String[] arr = "Regex is cool".split(" ");
    assertThat(arr).containsExactly("Regex", "is", "cool");

    arr = "1,2.3!4'5".split("[,.!']");
    assertThat(arr).containsExactly("1", "2", "3", "4", "5");

    arr = "1,2.3!4'5".split("[,.!']", 3); // limit = 3
    assertThat(arr).containsExactly("1", "2", "3!4'5");
  }

  /**
   * {@link String#replace(CharSequence, CharSequence)} returns a new
   * {@link String} resulting from finding and replacing each
   * substring of the string that matches the old target sequence
   * with the specified new replacement sequence.
   */
  @Test
  public void replace() throws Exception {
    assertThat("星期六".replace("星期", "周")).isEqualTo("周六");
    assertThat("星期六".replace("星期", new StringBuffer("周"))).isEqualTo("周六");
    assertThat("星期六".replace("星期", new StringBuilder("周"))).isEqualTo("周六");
  }

  /**
   * {@link String#replaceAll(String, String)} replaces each
   * substring of the string that matches the given regular
   * expression with the given replacement.
   */
  @Test
  public void replaceAll() throws Exception {
    String oldStr = "{\"keyA\": 10, \"keyB\": 20}";
    String newStr = oldStr.replaceAll("\\d+", "?");
    assertThat(newStr).isEqualTo("{\"keyA\": ?, \"keyB\": ?}");
  }

  /**
   * {@link String#replaceFirst(String, String)} replaces the first
   * substring of the string that matches the given regular
   * expression with the given replacement.
   */
  @Test
  public void replaceFirst() throws Exception {
    String oldStr = "{\"keyA\": 10, \"keyB\": 20}";
    String newStr = oldStr.replaceFirst("\\d+", "?");
    assertThat(newStr).isEqualTo("{\"keyA\": ?, \"keyB\": 20}");
  }

  /**
   * {@link Scanner} can be used to parse and tokenize strings.
   * <ul>
   * <li>If no delimiter is specified, a pattern that matches
   * whitespace is used by default for a {@link Scanner} object.
   * <li>You can specify a custom delimiter by calling its method
   * {@link Scanner#useDelimiter(String)} with a regex.
   * </ul>
   */
  @Test
  public void scanner() throws Exception {
    Scanner scanner;

    // use delimiter
    scanner = new Scanner("/usr:/bin:/home:/var");
    scanner.useDelimiter(":");
    List<String> paths = new ArrayList<>();
    while (scanner.hasNext()) {
      paths.add(scanner.next());
    }
    assertThat(paths).containsExactly("/usr", "/bin", "/home", "/var");

    // nextXxx: String
    scanner = new Scanner("Hello world");
    List<String> stringList = new ArrayList<>();
    while (scanner.hasNext()) {
      stringList.add(scanner.next());
    }
    assertThat(stringList).containsExactly("Hello", "world");

    // nextXxx: Integer
    scanner = new Scanner("1 2 3");
    List<Integer> integers = new ArrayList<>();
    while (scanner.hasNextInt()) {
      integers.add(scanner.nextInt());
    }
    assertThat(integers).containsExactly(1, 2, 3);

    // nextXxx: Double
    scanner = new Scanner("1.0 2.0 3.0");
    List<Double> doubles = new ArrayList<>();
    while (scanner.hasNextDouble()) {
      doubles.add(scanner.nextDouble());
    }
    assertThat(doubles).containsExactly(1.0, 2.0, 3.0);
  }

  /* Formatting strings */

  /**
   * The for specifier takes the following form:
   * <pre>
   * %[argument_index$][flags][width][.precision]conversion
   * </pre>
   * <p>
   * A format specification must start with a '%' sign and end with a
   * conversion character:
   * <ul>
   * <li>'b' for <tt>boolean</tt>
   * <li>'c' for <tt>char</tt>
   * <li>'d' for <tt>int</tt>, <tt>byte</tt>, <tt>short</tt>, and
   * <tt>long</tt>
   * <li>'f' for <tt>float</tt> and <tt>double</tt>
   * <li>'s' for <tt>String</tt>
   * </ul>
   */
  @Test
  public void formatSpecification() throws Exception {
    assertThat(String.format(Locale.ENGLISH,
        "%b %c %d %d %d %d %2.1f %2.1f %s",
        true,
        '!',
        (byte) 1,
        (short) 2,
        3,
        4L,
        5.0F,
        6.0D,
        "100")
    ).isEqualTo("true ! 1 2 3 4 5.0 6.0 100");
  }

  @Test
  public void flags() throws Exception {
    assertThat(String.format(Locale.ENGLISH, "%-6d", 1000)).isEqualTo("1000  ");
    assertThat(String.format(Locale.ENGLISH, "%+6d", 1000)).isEqualTo(" +1000");
    assertThat(String.format(Locale.ENGLISH, "%06d", 1000)).isEqualTo("001000");
    assertThat(String.format(Locale.ENGLISH, "%,6d", 1000)).isEqualTo(" 1,000");
    assertThat(String.format(Locale.ENGLISH, "%(6d", -100)).isEqualTo(" (100)");
  }

}
