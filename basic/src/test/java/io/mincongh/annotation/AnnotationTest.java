package io.mincongh.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
@TestForIssue(jira = "JIRA-123")
@MyTag("A")
@MyTag("B")
class AnnotationTest {

  @Test
  void getDeclaredAnnotation() {
    TestForIssue testForIssue = getClass().getDeclaredAnnotation(TestForIssue.class);
    assertThat(testForIssue.jira()).isEqualTo("JIRA-123");
  }

  /** Repeatable annotations are indirectly present. */
  @Test
  void getRepeatableAnnotation() {
    MyTag[] myTags = getClass().getDeclaredAnnotationsByType(MyTag.class);
    assertThat(Arrays.asList(myTags)).flatExtracting(MyTag::value).containsExactly("A", "B");
  }
}
