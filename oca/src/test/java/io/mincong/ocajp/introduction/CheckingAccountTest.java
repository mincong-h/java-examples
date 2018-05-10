package io.mincong.ocajp.introduction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Which three lines, when inserted independently at line n1, cause the program to print a 0
 * balance?
 * <pre>
 *    CheckingAccount acct = new CheckingAccount((int)(Math.random() * 1000));
 *    //line n1
 *    System.out.println(acct.getAmount());
 * </pre>
 */
public class CheckingAccountTest {

  @Test
  public void testPrint0optionD() {
    CheckingAccount acct = new CheckingAccount((int) (Math.random() * 1000));
    acct.amount = 0;
    assertEquals(0, acct.getAmount());
  }

  @Test
  public void testPrint0optionG() {
    CheckingAccount acct = new CheckingAccount((int) (Math.random() * 1000));
    acct.changeAmount(-acct.getAmount());
    assertEquals(0, acct.getAmount());
  }

  @Test
  public void testPrint0optionH() {
    CheckingAccount acct = new CheckingAccount((int) (Math.random() * 1000));
    acct.changeAmount(-acct.amount);
    assertEquals(0, acct.getAmount());
  }
}
