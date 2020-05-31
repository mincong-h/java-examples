package io.mincong.ocpjp.regex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * The greedy quantifiers are so named because they make the matcher read the complete input string
 * before starting to get the first match. If the matcher can't match the entire input string, it
 * backs off the input string by one character and attempts again. It repeats this until a match is
 * found or until no more characters are left. At the end, depending on whether you asked it to
 * match zero, one, or more occurrences, it'll try to match the pattern against zero, one, or more
 * characters from the input string.
 *
 * <p>
 *
 * <ul>
 *   <li>Question mark '?' matches zero or one occurrence
 *   <li>Asterisk mark '*' matches zero or more occurrence
 *   <li>Plus sign '+' matches one or more occurrence
 * </ul>
 *
 * @author Mincong Huang
 */
public class GreedyQuantifierTest {

  @Test
  public void useQuestionMark1() throws Exception {
    List<String> groupList = new ArrayList<>();
    Pattern pattern = Pattern.compile("colou?r");
    Matcher matcher = pattern.matcher("color colour");
    while (matcher.find()) {
      groupList.add(matcher.group());
    }
    assertThat(groupList).containsExactly("color", "colour");
  }

  @Test
  public void useQuestionMark2() throws Exception {
    List<String> groupList = new ArrayList<>();
    Pattern pattern = Pattern.compile("Aug(ust)?");
    Matcher matcher = pattern.matcher("August Aug");
    while (matcher.find()) {
      groupList.add(matcher.group());
    }
    assertThat(groupList).containsExactly("August", "Aug");
  }

  @Test
  public void useQuestionMark3() throws Exception {
    List<String> groupList = new ArrayList<>();
    Pattern pattern = Pattern.compile("[bmf]?all");
    Matcher matcher = pattern.matcher("ball mall fall all");
    while (matcher.find()) {
      groupList.add(matcher.group());
    }
    assertThat(groupList).containsExactly("ball", "mall", "fall", "all");
  }

  @Test
  public void useQuestionMark4() throws Exception {
    /*
     * Question mark '?' will match zero or one occurrence of the
     * letter 'd'. The regex engine found 3 zero-match, at index 1,
     * 2, and 3; it found 1 one-match, at index 0.
     */
    Pattern pattern = Pattern.compile("d?");
    Matcher matcher = pattern.matcher("day");
    List<String> results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'d': [0,1[", "'': [1,1[", "'': [2,2[", "'': [3,3[");
  }

  @Test
  public void useAsteriskMark1() throws Exception {
    Pattern pattern = Pattern.compile("co*l");
    Matcher matcher = pattern.matcher("cl col cool coool");
    List<String> results = MatcherHelper.collect(matcher);
    assertThat(results)
        .containsExactly("'cl': [0,2[", "'col': [3,6[", "'cool': [7,11[", "'coool': [12,17[");
  }

  @Test
  public void useAsteriskMark2_groupOfCharacters() throws Exception {
    Pattern pattern = Pattern.compile("\\p{Lower}\\d*\\p{Lower}");
    Matcher matcher = pattern.matcher("a123b c45d e6f gh");
    List<String> results = MatcherHelper.collect(matcher);
    assertThat(results)
        .containsExactly(
            "'a123b': [0,5[", "'c45d': [6,10[", "'e6f': [11,14[", "'gh': [15,17[" // zero occurrence
            );
  }

  @Test
  public void usePlusSign1() throws Exception {
    Pattern pattern = Pattern.compile("co+l");
    Matcher matcher = pattern.matcher("cl col cool coool");
    List<String> results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'col': [3,6[", "'cool': [7,11[", "'coool': [12,17[");
  }

  @Test
  public void usePlusSign2() throws Exception {
    Pattern pattern = Pattern.compile("\\p{Lower}\\d+\\p{Lower}");
    Matcher matcher = pattern.matcher("a123b c45d e6f gh");
    List<String> results = MatcherHelper.collect(matcher);
    assertThat(results).containsExactly("'a123b': [0,5[", "'c45d': [6,10[", "'e6f': [11,14[");
  }
}
