package io.mincong.ocpjp.regex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * {@link StringTokenizer} is a legacy class that's retained for
 * compatibility reasons. Oracle recommends use of method
 * {@link String#split(String, int)} or classes from a regex package
 * to get the same functionality.
 *
 * @author Mincong Huang
 */
public class StringTokenizerTest {

  @Rule
  public final ExpectedException expectedEx = ExpectedException.none();

  @Test
  public void basic() throws Exception {
    StringTokenizer st = new StringTokenizer("one two three");
    List<String> tokens = new ArrayList<>();
    while (st.hasMoreElements()) {
      tokens.add(st.nextToken());
    }
    assertThat(tokens).containsExactly("one", "two", "three");
  }

  @Test
  public void nullDelimiter() throws Exception {
    StringTokenizer st = new StringTokenizer("one two three", null);

    expectedEx.expect(NullPointerException.class);
    st.hasMoreElements();
  }

}
