package io.mincongh.math.powermock;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.mincongh.math.Matrix;
import io.mincongh.math.MatrixHelper;
import io.mincongh.math.Vector;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MatrixHelper.class)
@Ignore("Failed since JDK 9. PowerMock 2.0 is required: https://github.com/powermock/powermock/issues/725")
public class MatrixPowermockTest {

  private Vector v1 = new Vector(1, 1);
  private Vector v2 = new Vector(2, 2);
  private Vector v3 = new Vector(3, 3);

  @Test
  public void testBuildMatrix() {
    PowerMockito.spy(MatrixHelper.class);
    Mockito.when(MatrixHelper.buildMatrix(Mockito.any(Vector.class), Mockito.any(Vector.class),
        Mockito.any(Vector.class))).thenReturn(new Matrix(v1, v2, v3));

    Vector v1x = new Vector(1, 2);
    Vector v2x = new Vector(2, 3);

    Matrix mockedMatrix = MatrixHelper.buildMatrix(v1x, v2x, v3);
    // since the matrix is mocked, no matter the input vectors, it should
    // return the vectors mocked given by mockito.
    assertEquals(mockedMatrix.getV1(), v1);
    assertEquals(mockedMatrix.getV2(), v2);
    assertEquals(mockedMatrix.getV3(), v3);
  }
}
