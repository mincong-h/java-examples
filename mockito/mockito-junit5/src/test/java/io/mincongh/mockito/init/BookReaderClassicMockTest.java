package io.mincongh.mockito.init;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Mock object {@code mockedBook} is created in setup, {@link BeforeEach} the tests.
 *
 * @author Mincong Huang
 * @blog https://mincong.io/2020/04/19/mockito-junit5/
 */
class BookReaderClassicMockTest {

  private BookReader reader;
  private Book mockedBook;

  @BeforeEach
  void setUp() {
    mockedBook = Mockito.mock(Book.class);
    reader = new BookReader(mockedBook);
  }

  @Test
  void testPrintContent() {
    mockedBook.printContent();
    Mockito.verify(mockedBook).printContent();
  }

  @Test
  void testGetContent() {
    Mockito.when(mockedBook.getContent()).thenReturn("Mockito");
    assertEquals("Mockito", reader.getContent());
  }
}
