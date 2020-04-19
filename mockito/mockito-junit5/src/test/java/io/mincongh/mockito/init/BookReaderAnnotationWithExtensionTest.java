package io.mincongh.mockito.init;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Mock object {@code mockedBook} is created by {@link MockitoExtension}.
 *
 * @author Mincong Huang
 * @blog TODO
 */
@ExtendWith(MockitoExtension.class)
class BookReaderAnnotationWithExtensionTest {

  @Mock private Book mockedBook;

  private BookReader reader;

  @BeforeEach
  void setUp() {
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
