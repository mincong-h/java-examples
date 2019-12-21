package io.mincongh.mockito.init;

/** @author Mincong Huang */
public class BookReader {

  private Book book;

  public BookReader(Book book) {
    this.book = book;
  }

  public void printContent() {
    book.printContent();
  }

  public String getContent() {
    return book.getContent();
  }
}
