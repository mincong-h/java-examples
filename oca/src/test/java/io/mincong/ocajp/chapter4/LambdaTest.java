package io.mincong.ocajp.chapter4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** @author Mincong Huang */
public class LambdaTest {

  private Animal cat;
  private Animal elephant;

  @Before
  public void setUp() {
    cat = new Animal("cat", 5);
    elephant = new Animal("elephant", 500);
  }

  @After
  public void tearDown() {
    cat = null;
    elephant = null;
  }

  @Test
  public void testLambdaExample() {
    assertFalse(testOverweight(cat, new TestWeight()));
    assertTrue(testOverweight(elephant, new TestWeight()));

    // override method TestMe#test(Animal) using lambda
    assertFalse(testOverweight(cat, a -> a.getWeight() > 50));
    assertTrue(testOverweight(elephant, a -> a.getWeight() > 50));

    // equivalent with the previous expression
    assertTrue(testOverweight(elephant, (Animal a) -> a.getWeight() > 50));
    assertTrue(
        testOverweight(
            elephant,
            (Animal a) -> {
              return a.getWeight() > 50;
            }));
  }

  private boolean testOverweight(Animal animal, TestMe testMe) {
    return testMe.test(animal);
  }

  @Test
  public void testLambdaPredicates() {
    assertFalse(testExpr(cat, a -> a.getWeight() > 50));
    assertTrue(testExpr(elephant, a -> a.getWeight() > 50));
  }

  private boolean testExpr(Animal animal, Predicate<Animal> predicate) {
    return predicate.test(animal);
  }

  @Test
  public void testRemoveIf() {
    List<String> words = new ArrayList<>();
    words.add("I");
    words.add("hate");
    words.add("love");
    words.add("Java");
    words.removeIf(word -> word.equals("hate"));
    assertEquals("I love Java", String.join(" ", words));
  }
}
