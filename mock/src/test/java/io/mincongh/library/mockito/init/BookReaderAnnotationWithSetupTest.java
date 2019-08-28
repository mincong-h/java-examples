package io.mincongh.library.mockito.init;

import io.mincongh.library.Book;
import io.mincongh.library.BookReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * Mock object {@code mockedBook} is created in setup, {@link Before} the tests using {@link
 * MockitoAnnotations#initMocks(Object)}.
 *
 * @author Mincong Huang
 */
// no JUnit runner as annotation
public class BookReaderAnnotationWithSetupTest {

  private BookReader reader;

  @Mock Book mockedBook;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
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
