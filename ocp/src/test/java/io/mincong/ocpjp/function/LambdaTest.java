package io.mincong.ocpjp.function;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

/** @author Mincong Huang */
public class LambdaTest {

  @Test
  public void comparing() throws Exception {
    Apple light = new Apple(1);
    Apple heavy = new Apple(10);

    List<Apple> apples =
        Stream.of(heavy, light)
            .sorted(Comparator.comparing(Apple::getWeight))
            .collect(Collectors.toList());

    assertThat(apples).containsExactly(light, heavy);
  }

  private static class Apple {

    private int weight;

    private Apple(int weight) {
      this.weight = weight;
    }

    int getWeight() {
      return weight;
    }
  }
}
