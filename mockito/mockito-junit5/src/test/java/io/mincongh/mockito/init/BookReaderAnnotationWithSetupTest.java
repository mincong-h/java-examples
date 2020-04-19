package io.mincongh.mockito.init;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Mock object {@code mockedBook} is created in setup, {@link BeforeEach} the tests using {@link
 * MockitoAnnotations#initMocks(Object)}.
 *
 * @author Mincong Huang
 * @blog TODO
 */
// no JUnit extension as annotation
public class BookReaderAnnotationWithSetupTest {

  private BookReader reader;

  @Mock private Book mockedBook;

  @BeforeEach
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
