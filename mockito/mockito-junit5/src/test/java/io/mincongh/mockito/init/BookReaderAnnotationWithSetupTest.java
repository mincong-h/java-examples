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
 * @blog https://mincong.io/2020/04/19/mockito-junit5/
 */
// you don't need: @ExtendWith(MockitoExtension.class)
class BookReaderAnnotationWithSetupTest {

  private BookReader reader;

  @Mock private Book mockedBook;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
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
