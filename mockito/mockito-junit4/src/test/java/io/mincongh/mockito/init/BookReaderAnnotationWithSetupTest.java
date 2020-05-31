package io.mincongh.mockito.init;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Mock object {@code mockedBook} is created in setup, {@link Before} the tests using {@link
 * MockitoAnnotations#initMocks(Object)}.
 *
 * @author Mincong Huang
 * @blog https://mincong.io/2019/09/13/init-mock/
 */
// no JUnit runner as annotation
public class BookReaderAnnotationWithSetupTest {

  private BookReader reader;

  @Mock private Book mockedBook;

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
