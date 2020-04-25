package io.mincongh.mockito.init;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Mock object {@code mockedBook} is created by {@link MockitoJUnitRunner} created in parent class.
 * Inheritance is OK: {@code MockitoJUnitRunner} can initialize Mock objects correctly.
 *
 * @author Mincong Huang
 * @blog https://mincong.io/2019/09/13/init-mock/
 */
public class BookReaderAnnotationWithRunnerInheritanceTest
    extends BookReaderAnnotationWithRunnerTest {

  private BookReader reader2;

  @Mock private Book mockedBook2;

  @Before
  public void setUp2() {
    reader2 = new BookReader(mockedBook2);
  }

  @Test
  public void testPrintContent2() {
    mockedBook2.printContent();
    Mockito.verify(mockedBook2).printContent();
  }

  @Test
  public void testGetContent2() {
    Mockito.when(mockedBook2.getContent()).thenReturn("Mockito");
    assertEquals("Mockito", reader2.getContent());
  }
}
