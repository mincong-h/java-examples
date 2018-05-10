package io.mincong.ocpjp.design_principles.singleton;

/**
 * By using enums, you can implement the singleton pattern in a
 * thread safe manner, because enum instances can't be created by any
 * other class, the enum {@link EnumSingleton} will ensure that
 * existence of only one of its instances, that is
 * {@link EnumSingleton#INSTANCE}
 *
 * @author Mincong Huang
 */
public enum EnumSingleton {
  INSTANCE;

  public void initCache() {
    // ..code
  }

}
