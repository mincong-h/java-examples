package io.mincong.ocpjp.generic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * The wildcard '?' represents an unknown type. You can use it to
 * declare the type of a parameter; a local, instance , or static
 * variable; and return value of generic types. But you can't use it
 * as a type argument on invoke a generic method, create a generic
 * class instance, or for a supertype.
 *
 * @author Mincong Huang
 */
public class WildcardParcelTest {

  @Test
  public void relation() throws Exception {
    assertThat(new Book()).isInstanceOf(Gift.class);
    assertThat(new Phone()).isInstanceOf(Gift.class);
  }

  @Test
  public void upperBoundedWildcard() throws Exception {
    // The following assignment is not valid:
//    List<Gift> wishList = new ArrayList<Book>(); // Won't compile

    List<? extends Gift> myListU1 = new ArrayList<Book>();
    List<? extends Gift> myListU2 = new ArrayList<Phone>();
    List<? extends Gift> myListU3 = new ArrayList<Gift>();
    List<? extends Gift> myListU4 = new ArrayList<>();

    /* Write */
//    myListU1.add(new Book()); // Won't compile

//    Gift gift = new Book();
//    myListU1.add(gift); // Won't compile

    /* Read */
    for (Gift gift : myListU1) {
      // ...
    }
  }

  @Test
  public void lowerBoundedWildcard() throws Exception {
    List<? super Gift> myListL1 = new ArrayList<Gift>();
    List<? super Gift> myListL2 = new ArrayList<Object>();
//    List<? super Gift> myListL3 = new ArrayList<Phone>(); // Won't compile
    List<? super Phone> myListL4 = new ArrayList<Gift>();

    /* Write */
    myListL1.add(new Book());
    myListL1.add(new Phone());

    /* Read */
    // Elements are read as instance `Object`, superclass of `Gift`.
    for (Object o : myListL4) {
      // ...
    }
  }

  /**
   * We can use final classes in upper-bounded wildcards. Although
   * <code>class X extends String</code> won't compile,
   * <code>&lt;? extends String&gt;</code> will compile successfully.
   */
  private static void wrapGift(List<? extends String> list) {
    list.forEach(System.out::println);
  }

}
