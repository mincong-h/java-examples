package io.mincongh.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
@TestForIssue(jira = "JIRA-123")
@MyTag("A")
@MyTag("B")
public class AnnotationTest {

  @Test
  public void getDeclaredAnnotation() throws Exception {
    TestForIssue testForIssue = getClass().getDeclaredAnnotation(TestForIssue.class);
    assertThat(testForIssue.jira()).isEqualTo("JIRA-123");
  }

  /**
   * Repeatable annotations are indirectly present.
   */
  @Test
  public void getRepeatableAnnotation() throws Exception {
    MyTag[] myTags = getClass().getDeclaredAnnotationsByType(MyTag.class);
    assertThat(Arrays.asList(myTags)).flatExtracting(MyTag::value).containsExactly("A", "B");
  }

}
