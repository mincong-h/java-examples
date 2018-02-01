package io.mincongh.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import org.junit.Test;

/**
 * @author Mincong Huang
 * @author zlcook
 */
@TestForIssue(jira = "JIRA-123")
public class TestForIssueTest {

  @Test
  public void retrieveAnnotation() throws Exception {
    String v = processAnnotation(getClass().getCanonicalName());
    assertThat(v).isEqualTo("JIRA-123");
  }

  /**
   * @see <a href="https://www.jianshu.com/p/28edf5352b63">Java:Annotation(注解)--原理到案例</a>
   */
  private static String processAnnotation(String className) throws ClassNotFoundException {
    Class<?> clazz = Class.forName(className);
    Annotation[] annotations = clazz.getAnnotations();
    for (Annotation annotation : annotations) {
      if (annotation instanceof TestForIssue) {
        TestForIssue issue = (TestForIssue) annotation;
        return issue.jira();
      }
    }
    String msg = "Cannot find annotation `@TestForIssue` from class: " + className;
    throw new IllegalStateException(msg);
  }

}
