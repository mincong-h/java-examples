package io.mincong.ocajp.introduction;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import org.junit.Test;

/**
 * Which of the following will print current time?
 *
 * <pre>
 *   System.out.print(new LocalTime().now());
 *   System.out.print(new LocalTime());
 *   System.out.print(LocalTime.now());
 *   System.out.print(LocalTime.today());
 * </pre>
 */
public class PrintLocalTimeTest {

  @Test
  public void testLocalTime() {
    assertEquals(LocalTime.class, LocalTime.now().getClass());
  }
}
