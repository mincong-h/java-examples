package io.mincongh.so42188199;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class InputTest {

  private int getInput() {
    return new InputReader().readInput();
  }

  @Test
  public void getInputTest() throws Exception {
    InputReader sc = mock(InputReader.class);
    when(sc.readInput()).thenReturn(1);

    assertEquals(0, getInput());
    assertEquals(1, sc.readInput());
  }

}
