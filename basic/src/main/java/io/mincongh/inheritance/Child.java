package io.mincongh.inheritance;

/**
 *
 * @author Mincong Huang
 */
public class Child extends Parent {

    Child () {
        System.out.println("Child");
    }

//  Child () {
//      System.out.println("Child");
//      super(); // Error! Constructor call must be the first statement in a constructor
//  }
}
