package io.mincong.junit5.dynamic_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class NumberTest {

  @TestFactory
  Stream<DynamicTest> dynamicTestStream() {
    return IntStream.of(0, 3, 6, 9)
        .mapToObj(v -> dynamicTest(v + " is a multiple of 3", () -> assertEquals(0, v % 3)));
  }

  @TestFactory
  DynamicTest[] dynamicTestArray() {
    return IntStream.of(0, 3, 6, 9)
        .mapToObj(v -> dynamicTest(v + " is a multiple of 3", () -> assertEquals(0, v % 3)))
        .toArray(DynamicTest[]::new);
  }

  @TestFactory
  List<DynamicTest> dynamicTestList() {
    return IntStream.of(0, 3, 6, 9)
        .mapToObj(v -> dynamicTest(v + " is a multiple of 3", () -> assertEquals(0, v % 3)))
        .collect(Collectors.toList());
  }
}
