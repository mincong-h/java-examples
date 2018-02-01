package io.mincongh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation tag for issue tracking.
 *
 * @author Mincong Huang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestForIssue {
  /**
   * JIRA issue identifier.
   *
   * @return JIRA issue identifier
   */
  String jira();
}
