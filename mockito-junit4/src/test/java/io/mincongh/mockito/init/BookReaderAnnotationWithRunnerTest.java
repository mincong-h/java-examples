package io.mincongh.mockito.init;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Mock object {@code mockedBook} is created by {@link MockitoJUnitRunner}.
 *
 * @author Mincong Huang
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
