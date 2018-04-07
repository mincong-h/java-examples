package io.mincongh.stdio;

import java.util.Scanner;

/**
 * A small demo which repeats the user input as a form.
 *
 * <p>Answer for Stack Overflow question: <i>How to run a program in terminal for my given input for
 * the program in mac?</i>
 *
 * @author Mincong Huang
 * @see <a href="https://stackoverflow.com/questions/49687207/">Stack Overflow</a>
 */
public class UserForm {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      String user = getValue(scanner, "user");
      String email = getValue(scanner, "email");
      System.out.println("---");
      System.out.println(user + " <" + email + ">");
    }
  }

  private static String getValue(Scanner scanner, String property) {
    System.out.print(property + ": ");
    return scanner.nextLine();
  }
}
