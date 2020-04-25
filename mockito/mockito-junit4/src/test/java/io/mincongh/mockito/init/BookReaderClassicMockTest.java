package io.mincongh.mockito.init;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * Mock object {@code mockedBook} is created in setup, {@link Before} the tests.
 *
 * @author Mincong Huang
 * @blog https://mincong.io/2019/09/13/init-mock/
 */
public class BookReaderClassicMockTest {

  private BookReader reader;
  private Book mockedBook;

  @Before
  public void setUp() {
    mockedBook = Mockito.mock(Book.class);
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
