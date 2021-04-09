package io.mincong.junit5.parameterized_test;

public enum FilePermission {
  X(1),
  W(2),
  WX(3),
  R(4),
  RX(5),
  RW(6),
  RWX(7);

  private final boolean readable;
  private final boolean writable;
  private final boolean executable;

  FilePermission(int v) {
    this.executable = (v & 0b001) == 1;
    this.writable = (v & 0b010) == 2;
    this.readable = (v & 0b100) == 4;
  }

  boolean isReadable() {
    return readable;
  }

  boolean isWritable() {
    return writable;
  }

  boolean isExecutable() {
    return executable;
  }
}
