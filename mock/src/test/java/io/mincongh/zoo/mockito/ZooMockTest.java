package io.mincongh.zoo.mockito;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.mincongh.zoo.Animal;
import io.mincongh.zoo.Zoo;

/**
 * @author Mincong Huang
 */
@RunWith(MockitoJUnitRunner.class)
public class ZooMockTest {

  // @Mock(name = "queen")
  // private Animal mockedQueen;
  @Mock
  private Animal queen;

  // @Mock(name = "king")
  // private Animal mockedKing;
  @Mock
  private Animal king;

  @InjectMocks
  private Zoo zoo;

  @Before
  public void setUp() {
    Mockito.when(king.getName()).thenReturn("TIGER KING ฅ(ٛ•௰• ٛ )");
    Mockito.when(queen.getName()).thenReturn("TIGER QUEEN ( •̆௰•̆)");
  }

  @Test
  public void testHasKing() {
    assertTrue(zoo.hasKing());
  }

  @Test
  public void testHasQueen() {
    assertTrue(zoo.hasQueen());
  }
}
