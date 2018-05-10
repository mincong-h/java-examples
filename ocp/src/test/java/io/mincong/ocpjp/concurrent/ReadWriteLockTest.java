package io.mincong.ocpjp.concurrent;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class ReadWriteLockTest {

  private ReadWriteLock lock;

  private Map<String, String> map;

  @Before
  public void setUp() throws Exception {
    lock = new ReentrantReadWriteLock();
    map = new HashMap<>();
    map.put("k", "v");
  }

  @Test
  public void writeLock() throws Exception {
    /*
     * The writeLock is an exclusive lock: it can be acquired by only
     * one thread when no read thread has been acquired.
     */
    lock.writeLock().lock();
    try {
      map.put("k", "v2");
    } finally {
      lock.writeLock().unlock();
    }
  }

  @Test
  public void readLock() throws Exception {
    lock.readLock().lock();
    try {
      assertEquals("v", map.get("k"));
    } finally {
      lock.readLock().unlock();
    }
  }

  @Test
  public void readLock_multipleCalls() throws Exception {
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      threads.add(new Thread(() -> {
        /*
         * You can acquire multiple locks as long as no write lock
         * has been acquired on a `ReadWriteLock` object.
         */
        lock.readLock().lock();
        try {
          map.get("key");
        } finally {
          lock.readLock().unlock();
        }
      }));
    }
    threads.forEach(Thread::start);
  }

}
