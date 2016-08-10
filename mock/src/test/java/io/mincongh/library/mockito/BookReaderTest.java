package io.mincongh.library.mockito;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.mincongh.library.Book;
import io.mincongh.library.BookReader;

/**
 * @author Mincong Huang
 */
public class BookReaderTest {

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
