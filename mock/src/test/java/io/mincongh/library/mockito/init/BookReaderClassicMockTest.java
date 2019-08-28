package io.mincongh.library.mockito.init;

import io.mincongh.library.Book;
import io.mincongh.library.BookReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * Mock object {@code mockedBook} is created in setup, {@link Before} the tests.
 *
 * @author Mincong Huang
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
