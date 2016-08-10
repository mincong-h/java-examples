package io.mincongh.library.easymock;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;

import io.mincongh.library.Book;
import io.mincongh.library.BookReader;

/**
 * @author Mincong Huang
 */
public class BookReaderTest extends EasyMockSupport {

  @TestSubject
  private BookReader reader;

  private Book mockedBook;

  @Before
  public void setUp() {
    mockedBook = mock(Book.class);
    reader = new BookReader(mockedBook);
  }

  @Test
  public void testGetContent() {
    EasyMock.expect(mockedBook.getContent()).andReturn("Easy Mock");
    replayAll();
    assertEquals("Easy Mock", reader.getContent());
    verifyAll();
  }

  @Test
  public void testPrintContent() {
    mockedBook.printContent();
    replayAll();
    reader.printContent();
    verifyAll();
  }
}
