package io.mincong.ocajp.chapter1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class IdentifiersTest {

  @Test
  public void canStartWithDollar() throws Exception {
    int $i = 1;
    assertEquals($i, 1);
  }

  @Test
  public void canStartWithUnderscore() throws Exception {
    int _i = 1;
    assertEquals(_i, 1);
  }

  @Test
  public void canStartWithLetter() throws Exception {
    int i = 1;
    assertEquals(i, 1);
  }

}
