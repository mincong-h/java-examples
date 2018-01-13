package io.mincongh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mincong Huang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyTags {
  /*
   * Implicitly used for storing multiple annotations as an array.
   * It means that:
   *
   *     @MyTag(jira = "A")
   *     @MyTag(jira = "B")
   *
   * is equivalent to:
   *
   *     @MyTags({
   *         @MyTag(jira = "A"),
   *         @MyTag(jira = "B")
   *     })
   *
   */
  @SuppressWarnings("unused")
  MyTag[] value();
}
