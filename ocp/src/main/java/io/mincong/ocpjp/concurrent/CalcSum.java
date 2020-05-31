package io.mincong.ocpjp.concurrent;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/** @author Mala Gupta */
/*
 * Defines class `CalcSum, which extends `RecursiveTask<Integer>`.
 * This parameter V, `Integer`, is the type of the values returned by
 * method `compute()`.
 */
public class CalcSum extends RecursiveTask<Integer> {

  private static final int UNIT_SIZE = 15;

  private int[] values;

  private int startPos;

  private int endPos;

  private CalcSum(int[] values) {
    this(values, 0, values.length);
  }

  private CalcSum(int[] values, int startPos, int endPos) {
    this.values = values;
    this.startPos = startPos;
    this.endPos = endPos;
  }

  /**
   * The main computation performed by this task.
   *
   * @return the result of the computation
   */
  @Override
  protected Integer compute() {
    final int currentSize = endPos - startPos;
    if (currentSize <= UNIT_SIZE) {
      return computeSum();
    }
    int center = currentSize / 2;

    /*
     * On the right part, it calls `compute()`; on the left part, it
     * calls `fork()`:
     *
     * 1. Calling `fork()` makes it execute asynchronously.
     * 2. Calling `compute()` will recursively create (left and right)
     *    `CalcSum` objects, if it still needs to be divided into
     *    smaller tasks.
     */
    int leftEnd = startPos + center;
    CalcSum leftSum = new CalcSum(values, startPos, leftEnd);
    leftSum.fork();

    int rightStart = startPos + center + 1;
    CalcSum rightSum = new CalcSum(values, rightStart, endPos);
    /*
     * `leftSum.join()` waits until it returns a value; `compute()`
     * is main computation performed by task.
     */
    return rightSum.compute() + leftSum.join();
  }

  private Integer computeSum() {
    int sum = 0;
    for (int i = startPos; i < endPos; i++) {
      sum += values[i];
    }
    System.out.printf("Sum(%d-%d):%d%n", startPos, endPos, sum);
    return sum;
  }

  public static void main(String... args) {
    int[] intArray = new int[100];
    Random randomValues = new Random();

    for (int i = 0; i < intArray.length; i++) {
      intArray[i] = randomValues.nextInt(10);
    }

    ForkJoinPool pool = new ForkJoinPool();
    CalcSum calculator = new CalcSum(intArray);
    // `invoke()` awaits and obtains result
    System.out.println(pool.invoke(calculator));
  }
}
