package io.mincong.ocpjp.threads;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Mincong Huang
 */
public class ThreadWait {

  /**
   * Milliseconds required to let the king be ready.
   * <p>
   * You can change this value and / or the value of {@link
   * #MS_TO_WAIT} to modify the behaviors of white walkers.
   *
   * @see #MS_TO_WAIT
   */
  private static final int MS_TO_READY = 100;

  /**
   * Milliseconds required to wait the king.
   * <p>
   * You can change this value and / or the value of {@link
   * #MS_TO_READY} to modify the behaviors of white walkers.
   *
   * @see #MS_TO_READY
   */
  private static final int MS_TO_WAIT = 1_000;

  public static void main(String... args) {
    NightKing king = new NightKing();
    List<WhiteWalker> walkers = Arrays.asList(
        new WhiteWalker("A", king),
        new WhiteWalker("B", king),
        new WhiteWalker("C", king)
    );
    king.start();
    walkers.forEach(Thread::start);
  }

  public static class WhiteWalker extends Thread {

    private String name;

    private final NightKing king;

    WhiteWalker(String name, NightKing king) {
      this.name = name;
      this.king = king;
    }

    @Override
    public void run() {
      System.out.println(name + " is waiting...");
      synchronized (king) {
        try {
          king.wait(MS_TO_WAIT);
        } catch (InterruptedException e) {
          throw new IllegalStateException(e);
        }
      }
      if (king.isReady.get()) {
        System.out.println(name + " follows.");
      } else {
        System.out.println(name + " is dead (king is not ready).");
      }
    }

    @Override
    public String toString() {
      return name;
    }
  }

  public static class NightKing extends Thread {

    AtomicBoolean isReady = new AtomicBoolean(false);

    @Override
    public void run() {
      System.out.println("Night King is preparing...");
      try {
        Thread.sleep(MS_TO_READY);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      ready();
    }

    private synchronized void ready() {
      System.out.println("Night King is ready.");
      isReady.set(true);
      notifyAll();
    }
  }

}
