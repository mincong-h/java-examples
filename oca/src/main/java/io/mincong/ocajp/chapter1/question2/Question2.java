package io.mincong.ocajp.chapter1.question2;

/**
 * What is the output of the following program?
 *
 * @author Mincong Huang
 */
public class Question2 {

  private String brand;
  private boolean empty;

  /**
   * Boolean fields initialize to {@code false} and reference initialize to {@code null}, so empty
   * is {@code false} and brand is {@code null}.
   */
  public static void main(String[] args) {
    Question2 question2 = new Question2();
    System.out.println(question2.brand);  // false
    System.out.println(question2.empty);  // null
  }

}
