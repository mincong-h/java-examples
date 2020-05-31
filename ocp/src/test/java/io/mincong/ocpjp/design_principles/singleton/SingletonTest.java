package io.mincong.ocpjp.design_principles.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/** @author Mincong Huang */
public class SingletonTest {

  @Test
  public void testGetInstance_basicSingleton() throws Exception {
    BasicSingleton i1 = BasicSingleton.getInstance();
    BasicSingleton i2 = BasicSingleton.getInstance();
    assertThat(i1).isSameAs(i2);
  }

  @Test
  public void testGetInstance_enumSingleton() throws Exception {
    EnumSingleton i1 = EnumSingleton.INSTANCE;
    EnumSingleton i2 = EnumSingleton.INSTANCE;
    assertThat(i1).isSameAs(i2);
  }

  @Test
  public void testGetInstance_eagerInitSingleton() throws Exception {
    EagerInitSingleton i1 = EagerInitSingleton.getInstance();
    EagerInitSingleton i2 = EagerInitSingleton.getInstance();
    assertThat(i1).isSameAs(i2);
  }

  @Test
  public void testGetInstance_syncSingleton() throws Exception {
    SyncSingleton i1 = SyncSingleton.getInstance();
    SyncSingleton i2 = SyncSingleton.getInstance();
    assertThat(i1).isSameAs(i2);
  }

  @Test
  public void testGetInstance_syncSingleton2() throws Exception {
    SyncSingleton2 i1 = SyncSingleton2.getInstance();
    SyncSingleton2 i2 = SyncSingleton2.getInstance();
    assertThat(i1).isSameAs(i2);
  }
}
