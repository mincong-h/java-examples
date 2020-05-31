package io.mincong.ocajp.introduction.question1;

/**
 * What is the result of the following class? (Choose all that apply)
 *
 * @author Mincong HUANG
 */
public class _C {

  private static int $; // dollar is OK

  public static void main(String[] main) {
    String a_b;
    System.out.print($);
    //  System.out.print(a_b);  // compile error: "Variable 'a_b' might not have been initialized"
  }
}
