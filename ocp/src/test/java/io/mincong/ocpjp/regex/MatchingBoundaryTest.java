package io.mincong.ocpjp.regex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * <i>Matching boundaries</i> can help you to limit the searches to
 * the start or end of a word. You can match boundaries including the
 * start of a line, a word, a non-word, or the end of a line by using
 * regex pattern. The following list shows the boundary basic
 * constructs:
 *
 * <pre>
 * Boundary construct | Description
 * ------------------ | -------------------
 * '\b'               | A word boundary
 * '\B'               | A non-word boundary
 * '^'                | Beginning of a line
 * '$'                | End of a line
 * </pre>
 *
 * @author Mincong Huang
 */
public class MatchingBoundaryTest {

  /**
   * A sentence containing multiple substring <tt>the</tt>.
   */
  private static final String SENTENCE = "the leather in their coat made her seethe";

  /**
   * A paragraph containing multiple words starting with 'F'.
   */
  private static final String PARAGRAPH = "One\nTwo\nThree\nFour\nFive Finished.\n";

  @Test
  public void wordBoundary() throws Exception {
    Pattern pattern = Pattern.compile("\\bthe"); // Match words which start with "the".
    Matcher matcher = pattern.matcher(SENTENCE);
    List<String> results = new ArrayList<>();
    List<Integer> startIndexes = new ArrayList<>();
    while (matcher.find()) {
      results.add(matcher.group());
      startIndexes.add(matcher.start());
    }
    assertThat(results).containsExactly("the", "the" /* From "their" */);
    assertThat(startIndexes).containsExactly(0, 15);
  }

  @Test
  public void nonWordBoundary() throws Exception {
    Pattern pattern = Pattern.compile("\\Bthe"); // Match words which do not start with "the".
    Matcher matcher = pattern.matcher(SENTENCE);
    List<String> results = new ArrayList<>();
    List<Integer> startIndexes = new ArrayList<>();
    while (matcher.find()) {
      results.add(matcher.group());
      startIndexes.add(matcher.start());
    }
    assertThat(results).containsExactly(
        "the" /* From "leather" */,
        "the" /* From "seethe" */
    );
    assertThat(startIndexes).containsExactly(7, 38);
  }

  @Test
  public void beginningOfALine() throws Exception {
    Pattern pattern = Pattern.compile("^[Ff]", Pattern.MULTILINE); // Start with a char 'F' or 'f'.
    Matcher matcher = pattern.matcher(PARAGRAPH);
    List<String> results = new ArrayList<>();
    List<Integer> startIndexes = new ArrayList<>();
    while (matcher.find()) {
      results.add(matcher.group());
      startIndexes.add(matcher.start());
    }
    // Notice that "finished" is not included.
    assertThat(results).containsExactly(
        "F" /* From "Four" */,
        "F" /* From "Five" */
    );
    assertThat(startIndexes).containsExactly(14, 19);
  }

  @Test
  public void endOfALine() throws Exception {
    Pattern pattern = Pattern.compile("e$", Pattern.MULTILINE); // End with a char 'e'.
    Matcher matcher = pattern.matcher(PARAGRAPH);
    List<String> results = new ArrayList<>();
    List<Integer> startIndexed = new ArrayList<>();
    while (matcher.find()) {
      results.add(matcher.group());
      startIndexed.add(matcher.start());
    }
    assertThat(results).containsExactly(
        "e" /* From "One" */,
        "e" /* From "Three" */);
    assertThat(startIndexed).containsExactly(2, 12);
  }

  /**
   * Define a regex pattern for searching the literal string for
   * "the" at either the beginning or end of a word, but not in its
   * middle.
   */
  @Test
  public void exercise() throws Exception {
    String answer = "\\bthe|the\\b";
    Pattern pattern = Pattern.compile(answer);
    Matcher matcher = pattern.matcher(SENTENCE);
    List<String> results = new ArrayList<>();
    List<Integer> startIndexes = new ArrayList<>();
    while (matcher.find()) {
      results.add(matcher.group());
      startIndexes.add(matcher.start());
    }
    assertThat(results).containsExactly(
        "the" /* From "the" */,
        "the" /* From "their" */,
        "the" /* From "seethe" */
    );
    assertThat(startIndexes).containsExactly(0, 15, 38);
  }

}
