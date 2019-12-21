package io.mincongh.math;

public class MatrixHelper {

  private MatrixHelper() {
    // Utility class, do not instantiate
  }

  public static Matrix buildMatrix(Vector v1, Vector v2, Vector v3) {
    return new Matrix(v1, v2, v3);
  }
}
