package io.mincongh.inheritance;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.mincongh.inheritance2.OuterChild;

/**
 * Inheritance test.
 *
 * @author Mincong Huang
 */
public class InheritanceTest {

  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private final ByteArrayOutputStream err = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(out));
    System.setErr(new PrintStream(err));
  }

  @Test
  public void testConstructionOrder() {
    @SuppressWarnings("unused")
    Parent parent = new Child();
    String expected = String.format("%s%n%s%n", "Parent", "Child");
    assertEquals("Parent's constructor is called first", expected, out.toString());
  }

  /**
   * <pre>
   * Modifier    Class Package Subclass World
   * ----------------------------------------
   * public      Y     Y       Y        Y
   * protected   Y     Y       Y        N
   * no modifier Y     Y       N        N
   * private     Y     N       N        N
   * </pre>
   */
  @Test
  public void testVisibility() {

    Parent parent = new Parent();
    // Modifiers are accessible except the private modifier.
    assertEquals("parent no modifier", parent.noModifierAttr);
    assertEquals("parent protected", parent.protectedAttr);
    assertEquals("parent public", parent.publicAttr);

    Child child = new Child();
    // Modifiers are accessible except the private modifier.
    assertEquals("child no modifier", child.noModifierAttr);
    assertEquals("child protected", child.protectedAttr);
    assertEquals("child public", child.publicAttr);
    assertEquals("parent no modifier", ((Parent) child).noModifierAttr);
    assertEquals("parent protected", ((Parent) child).protectedAttr);
    assertEquals("parent public", ((Parent) child).publicAttr);

    Parent parent2 = new Child();
    // Modifiers are accessible except the private modifier.
    assertEquals("parent no modifier", parent2.noModifierAttr);
    assertEquals("parent protected", parent2.protectedAttr);
    assertEquals("parent public", parent2.publicAttr);
    assertEquals("child no modifier", ((Child) parent2).noModifierAttr);
    assertEquals("child protected", ((Child) parent2).protectedAttr);
    assertEquals("child public", ((Child) parent2).publicAttr);

    OuterChild outerChild = new OuterChild();
    // Modifiers having types private/no-modifier/protected are not
    // accessible because outer child is located in another package.
    // Only the public attribute is accessible.
    assertEquals("outer-child public", outerChild.publicAttr);
    assertEquals("parent no modifier", ((Parent) outerChild).noModifierAttr);
    assertEquals("parent protected", ((Parent) outerChild).protectedAttr);
    assertEquals("parent public", ((Parent) outerChild).publicAttr);

  }

  @After
  public void cleanUpStreams() {
    System.setOut(null);
    System.setErr(null);
  }
}
