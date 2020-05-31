package io.mincong.ocpjp.function;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

/** @author Mincong Huang */
public class FunctionTest {

  private List<String> words;

  private List<Integer> numbers;

  private Map<Integer, Integer> map;

  @Before
  public void setUp() throws Exception {
    words = Arrays.asList("Hi", "Java", "Functional");
    numbers = Arrays.asList(0, 1, 2, 3);
    map = new HashMap<>();
    map.put(1, 2);
    map.put(2, 4);
    map.put(3, 6);
  }

  /* Predicates */

  @Test
  public void predicate1() throws Exception {
    Predicate<String> filter = s -> s.startsWith("H");

    List<String> filteredWords = words.stream().filter(filter).collect(Collectors.toList());
    assertThat(filteredWords).containsExactly("Hi");

    List<String> otherWords = words.stream().filter(filter.negate()).collect(Collectors.toList());
    assertThat(otherWords).containsExactly("Java", "Functional");
  }

  @Test
  public void predicate2() throws Exception {
    Predicate<Integer> isPositive = i -> i > 0;
    List<Integer> values = numbers.stream().filter(isPositive).collect(Collectors.toList());
    assertThat(values).containsExactly(1, 2, 3);
  }

  @Test
  public void intPredicate() throws Exception {
    IntPredicate isPositive = i -> i > 0;
    List<Integer> values = numbers.stream().filter(isPositive::test).collect(Collectors.toList());
    assertThat(values).containsExactly(1, 2, 3);
  }

  @Test
  public void longPredicate() throws Exception {
    LongPredicate isOdd = i -> i % 2 == 0;
    long count = numbers.stream().mapToLong(i -> i).filter(isOdd).count();
    assertThat(count).isEqualTo(2);
  }

  @Test
  public void doublePredicate() throws Exception {
    DoublePredicate floorIsOne = d -> Math.floor(d) == 1D;
    long count = numbers.stream().mapToDouble(i -> i * 1.5).filter(floorIsOne).count();
    assertThat(count).isEqualTo(1);
  }

  /* Consumers */

  @Test
  public void consumer() throws Exception {
    List<String> values = new ArrayList<>();
    Consumer<String> insert = values::add;
    words.forEach(insert);
    assertThat(values).containsExactly("Hi", "Java", "Functional");
  }

  @Test
  public void intConsumer() throws Exception {
    List<Integer> values = new ArrayList<>();
    IntConsumer insert = values::add;
    numbers.forEach(insert::accept);
    assertThat(values).containsExactly(0, 1, 2, 3);
  }

  @Test
  public void longConsumer() throws Exception {
    AtomicLong sum = new AtomicLong(0);
    LongConsumer add = sum::getAndAdd;
    numbers.stream().mapToLong(i -> i).forEach(add);
    assertThat(sum.get()).isEqualTo(6);
  }

  @Test
  public void doubleConsumer() throws Exception {
    List<Double> values = new ArrayList<>();
    DoubleConsumer add = values::add;
    numbers.stream().mapToDouble(i -> i * 1.0).forEach(add);
    assertThat(values).containsExactly(0.0, 1.0, 2.0, 3.0);
  }

  @Test
  public void biConsumer() throws Exception {
    List<Integer> values = new ArrayList<>();
    BiConsumer<Integer, Integer> insertMultiplication = (x, y) -> values.add(x * y);
    map.forEach(insertMultiplication);
    assertThat(values).containsExactly(2, 8, 18);
  }

  /* Functions */

  @Test
  public void function() throws Exception {
    List<String> entries =
        map.entrySet().stream()
            .map(e -> "(" + e.getKey() + ", " + e.getValue() + ")")
            .collect(Collectors.toList());
    assertThat(entries).containsExactly("(1, 2)", "(2, 4)", "(3, 6)");
  }

  @Test
  public void intFunction() throws Exception {
    IntFunction<String> toStr = String::valueOf;
    List<String> values = numbers.stream().map(toStr::apply).collect(Collectors.toList());
    assertThat(values).containsExactly("0", "1", "2", "3");
  }

  @Test
  public void biFunction() throws Exception {
    BiFunction<Integer, Integer, String> f = (x, y) -> x + ", " + y;
    assertThat(f.apply(1, 2)).isEqualTo("1, 2");
  }

  @Test
  public void intToDoubleFunction() throws Exception {
    IntToDoubleFunction doubleMe = i -> i * 2.0;
    List<Double> values =
        numbers.stream()
            .mapToInt(Integer::intValue)
            .mapToDouble(doubleMe)
            .boxed()
            .collect(Collectors.toList());
    assertThat(values).containsExactly(0.0, 2.0, 4.0, 6.0);
  }

  @Test
  public void intToLongFunction() throws Exception {
    IntToLongFunction x10 = i -> i * 10;
    List<Long> values =
        numbers.stream()
            .mapToInt(Integer::intValue)
            .mapToLong(x10)
            .boxed()
            .collect(Collectors.toList());
    assertThat(values).containsExactly(0L, 10L, 20L, 30L);
  }

  /* Operators */

  @Test
  public void unaryOperator() throws Exception {
    UnaryOperator<Integer> doubleMe = i -> i * 2;
    List<Integer> values = numbers.stream().map(doubleMe).collect(Collectors.toList());
    assertThat(values).containsExactly(0, 2, 4, 6);
  }

  @Test
  public void intUnaryOperator() throws Exception {
    IntUnaryOperator doubleMe = i -> i * 2;
    IntUnaryOperator plusOne = i -> i + 1;
    IntUnaryOperator plusOneThenDouble = doubleMe.compose(plusOne);
    IntUnaryOperator doubleThenPlusOne = doubleMe.andThen(plusOne);

    Function<IntUnaryOperator, List<Integer>> f =
        operator -> numbers.stream().map(operator::applyAsInt).collect(Collectors.toList());

    assertThat(f.apply(doubleMe)).containsExactly(0, 2, 4, 6);
    assertThat(f.apply(plusOneThenDouble)).containsExactly(2, 4, 6, 8);
    assertThat(f.apply(doubleThenPlusOne)).containsExactly(1, 3, 5, 7);
  }

  /* Suppliers */

  @Test
  public void supplier() throws Exception {
    Supplier<List<Integer>> supplier = ArrayList::new;
    List<Integer> values =
        numbers.stream().map(i -> i * 2).collect(Collectors.toCollection(supplier));
    assertThat(values).containsExactly(0, 2, 4, 6);
  }

  @Test
  public void intSupplier() throws Exception {
    IntSupplier supplier = numbers::size;
    assertThat(supplier.getAsInt()).isEqualTo(4);
  }

  @Test
  public void doubleSupplier() throws Exception {
    DoubleSupplier supplier = () -> 10.0;
    assertThat(supplier.getAsDouble()).isEqualTo(10.0);
  }

  @Test
  public void booleanSupplier() throws Exception {
    BooleanSupplier supplier = () -> false;
    assertThat(supplier.getAsBoolean()).isFalse();
  }

  @Test
  public void longSupplier() throws Exception {
    LongSupplier supplier = () -> 1L;
    assertThat(supplier.getAsLong()).isEqualTo(1L);
  }

  /*
   * Method references
   *
   * There are three main kinds of method references:
   *
   * 1. A method reference to a static method (for example, the
   *    method `parseInt` of `Integer`, written `Integer::parseInt`)
   * 2. A method reference to an instance method of an arbitrary type
   *    (for example, the method `length` of a `String`, written
   *    `String::length`)
   * 3. A method reference to an instance method of an existing
   *    object (for example, the pose you have a local variable
   *    `expensiveTransaction` that holds an object of type
   *    `Transaction`, which supports an instance method `getValue`;
   *    you can write `expensiveTransaction::getValue`)
   */

  @Test
  public void methodReference_staticMethod() throws Exception {
    List<String> values = numbers.stream().map(String::valueOf).collect(Collectors.toList());
    assertThat(values).containsExactly("0", "1", "2", "3");
  }

  @Test
  public void methodReference_instanceMethodOfArbitraryType() throws Exception {
    /*
     * BiPredicate<List<Integer>, Integer> contains =
     *     (list, element) -> list.contains(element);
     *
     * This lambda uses its first argument to call the method
     * `contains` on it. Because the first argument is of type
     * `List`, the expression can be simplified as the following.
     * This is because the target type describes a function
     * descriptor
     *
     *     (List<String>, String) -> boolean
     *
     * and `List::contains` can be unpacked to that function
     * descriptor.
     */
    BiPredicate<List<Integer>, Integer> contains = List::contains;
    assertThat(contains.test(numbers, 2)).isTrue();
  }

  @Test
  public void methodReference_instanceMethodOfExistingObject() throws Exception {
    StringBuilder builder = new StringBuilder();
    numbers.forEach(builder::append);
    assertThat(builder.toString()).isEqualTo("0123");
  }
}
