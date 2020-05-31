package io.mincongh.mockito.init;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Mock object {@code mockedBook} is created by {@link MockitoJUnitRunner}.
 *
 * @author Mincong Huang
 * @blog https://mincong.io/2019/09/13/init-mock/
 */
@RunWith(MockitoJUnitRunner.class)
public class BookReaderAnnotationWithRunnerTest {

  private BookReader reader;

  @Mock private Book mockedBook;

  @Before
  public void setUp() {
    reader = new BookReader(mockedBook);
  }

  @Test
  public void testPrintContent() {
    mockedBook.printContent();
    Mockito.verify(mockedBook).printContent();
  }

  @Test
  public void testGetContent() {
    Mockito.when(mockedBook.getContent()).thenReturn("Mockito");
    assertEquals("Mockito", reader.getContent());
  }
}
