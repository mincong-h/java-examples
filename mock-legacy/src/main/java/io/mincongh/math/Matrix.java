package io.mincongh.math;

public class Matrix {

  private Vector v1;
  private Vector v2;
  private Vector v3;

  public Matrix(Vector v1, Vector v2, Vector v3) {
    this.v1 = v1;
    this.v2 = v2;
    this.v3 = v3;
  }

  public Vector getV1() {
    return v1;
  }

  public Vector getV2() {
    return v2;
  }

  public Vector getV3() {
    return v3;
  }
}
