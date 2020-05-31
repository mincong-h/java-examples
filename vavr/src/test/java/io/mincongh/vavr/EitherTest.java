package io.mincongh.vavr;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

/**
 * @author Mincong Huang
 * @since 1.0
 */
class EitherTest {

  @Test
  void javadoc() {
    assertThat(Either.right("a").map(String::toUpperCase).toString()).isEqualTo("Right(A)");
    assertThat(Either.left(1).mapLeft(i -> i + 1).toString()).isEqualTo("Left(2)");

    assertThat(Either.right("a").filterOrElse(i -> false, val -> "bad: " + val).toString())
        .isEqualTo("Left(bad: a)");
    assertThat(Either.right("a").filterOrElse(i -> true, val -> "bad: " + val).toString())
        .isEqualTo("Right(a)");
  }
}
