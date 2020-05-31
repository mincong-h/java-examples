package io.mincongh.mockito.init;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Mock object {@code mockedBook} is created by {@link MockitoExtension}.
 *
 * @author Mincong Huang
 * @blog https://mincong.io/2020/04/19/mockito-junit5/
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

  /**
   * Disable the assertion below and see the validation of framework usage. Mockito fails your build
   * and ask you to remove the unnecessary stubbings. It makes your code clean, particularly after
   * refactoring. Example output:
   *
   * <pre>
   * org.mockito.exceptions.misusing.UnnecessaryStubbingException:
   * Unnecessary stubbings detected.
   * Clean & maintainable test code requires zero unnecessary code.
   * Following stubbings are unnecessary (click to navigate to relevant line of code):
   *   1. -> at io.mincongh.mockito.init.BookReaderAnnotationWithExtensionTest.testGetContent(BookReaderAnnotationWithExtensionTest.java:38)
   * Please remove unnecessary stubbings or use 'lenient' strictness. More info: javadoc for UnnecessaryStubbingException class.
   * </pre>
   */
  @Test
  void testGetContent() {
    Mockito.when(mockedBook.getContent()).thenReturn("Mockito");
    assertEquals("Mockito", reader.getContent());
  }
}
