/**
 * Question 3: What is the output of the following code? (Choose all that apply)
 *
 * <ul>
 *   <li>A. 2
 *   <li>B. 4
 *   <li>C. The code will not compile because of line 3.
 *   <li>D. The code will not compile because of line 5.
 *   <li>E. The code will not compile because of line 7.
 *   <li>F. The code will not compile because of line 11.
 *   <li>G. The output cannot be determined from the code provided.
 * </ul>
 *
 * The answers are C, D, E.
 *
 * <p>
 *
 * <pre>
 * interface HasTail { int getTailLength(); }     // 'getTailLength()' is assumed to be public
 * abstract class Puma implements HasTail {
 *   protected int getTailLength() { return 4; }  // Invalid override: protected < public. (C)
 * }
 * public class Cougar extends Puma {             // Error in parent class Puma. (D)
 *   public static void main(String[] args) {
 *     Puma puma = new Puma();                    // Abstract class Puma cannot be instantiated. (E)
 *     System.out.println(puma.getTailLength());
 *   }
 *
 *   public int getTailLength(int length) { return 2; }
 * }
 * </pre>
 *
 * @author Mincong HUANG
 */
package io.mincong.ocajp.introduction.question3;
