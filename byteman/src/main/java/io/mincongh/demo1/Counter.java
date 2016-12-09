package io.mincongh.demo1;

import java.util.concurrent.Callable;

/**
 * @author Mincong Huang
 */
public class Counter implements Callable<Integer> {

  private int end;

  public Counter(int end) {
    this.end = end;
  }

  @Override
  public Integer call() throws Exception {
    int i = 0;
    while (i < end) {
      System.out.println(i);
      i++;
    }
    return i;
  }
}
