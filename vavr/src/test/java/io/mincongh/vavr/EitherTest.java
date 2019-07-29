package io.mincongh.vavr;

import io.vavr.control.Either;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class EitherTest {

  @Test
  public void javadoc() {
    assertThat(Either.right("a").map(String::toUpperCase).toString()).isEqualTo("Right(A)");
    assertThat(Either.left(1).mapLeft(i -> i + 1).toString()).isEqualTo("Left(2)");

    assertThat(Either.right("a").filterOrElse(i -> false, val -> "bad: " + val).toString())
        .isEqualTo("Left(bad: a)");
    assertThat(Either.right("a").filterOrElse(i -> true, val -> "bad: " + val).toString())
        .isEqualTo("Right(a)");
  }
}
