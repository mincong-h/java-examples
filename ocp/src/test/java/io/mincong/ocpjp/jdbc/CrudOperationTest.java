package io.mincong.ocpjp.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests CRUD (Create, retrieve, update, and delete) operations.
 *
 * @author Mincong Huang
 */
public class CrudOperationTest extends JdbcTest {

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();

    try (Statement s = connection.createStatement()) {
      /*
       * Exam Tip:
       *
       * Method `executeUpdate()` is used to execute SQL queries to
       * insert new rows in a table, and update and delete existing
       * rows. It's also used to execute DDL queries, such as the
       * creation, modification, and deletion of database objects
       * like tables. If you use method `executeQuery()` for any of
       * these operations, you'll get a `SQLException` at runtime.
       */
      s.executeUpdate(
          "CREATE TABLE book ("
              + "  id INT PRIMARY KEY,"
              + "  title VARCHAR(1000),"
              + "  author CHAR(255),"
              + "  publication_year INT,"
              + "  unit_price REAL"
              + ")");
      s.executeUpdate(
          "INSERT INTO book (id, title, author, publication_year, unit_price)"
              + " VALUES (1, 'Book Name', 'Author', 2017, 10.0)");
    }
  }

  @Test
  public void readTable() throws Exception {
    String columns;
    try (Statement s = connection.createStatement()) {
      try (ResultSet rs = s.executeQuery("SHOW COLUMNS FROM book")) {
        columns = getResults(rs);
      }
    }
    assertThat(columns).contains("ID, INTEGER(10), NO, PRI, NULL");
    assertThat(columns).contains("TITLE, VARCHAR(1000), YES, , NULL");
    assertThat(columns).contains("AUTHOR, CHAR(255), YES, , NULL");
    assertThat(columns).contains("PUBLICATION_YEAR, INTEGER(10), YES, , NULL");
    assertThat(columns).contains("UNIT_PRICE, REAL(7), YES, , NULL");
  }

  @Test
  public void readRows_dataTypes() throws Exception {
    try (Statement s = connection.createStatement()) {
      try (ResultSet rs = s.executeQuery("SELECT * FROM book")) {
        rs.next();

        assertThat(rs.getInt(1)).isEqualTo(1);
        assertThat(rs.getInt("ID")).isEqualTo(1);

        assertThat(rs.getString(2)).isEqualTo("Book Name");
        assertThat(rs.getString("TITLE")).isEqualTo("Book Name");

        assertThat(rs.getString(3)).isEqualTo("Author");
        assertThat(rs.getString("AUTHOR")).isEqualTo("Author");

        assertThat(rs.getInt(4)).isEqualTo(2017);
        assertThat(rs.getInt("PUBLICATION_YEAR")).isEqualTo(2017);

        assertThat(rs.getDouble(5)).isEqualTo(10.0);
        assertThat(rs.getDouble("UNIT_PRICE")).isEqualTo(10.0);
      }
    }
  }

  @Test
  public void readRows_existing() throws Exception {
    try (Statement s = connection.createStatement()) {
      try (ResultSet rs = s.executeQuery("SELECT * FROM book")) {
        assertThat(getResults(rs)).isEqualTo("1, Book Name, Author, 2017, 10.0\n");
      }
    }
  }

  @Test
  public void readRows_nonExistent() throws Exception {
    try (Statement s = connection.createStatement()) {
      try (ResultSet rs = s.executeQuery("SELECT * FROM book WHERE id > 10")) {
        ResultSetMetaData m = rs.getMetaData();

        assertThat(rs.next()).isFalse();
        assertThat(m.getColumnLabel(1)).isEqualTo("ID");
        assertThat(m.getColumnLabel(2)).isEqualTo("TITLE");
        assertThat(m.getColumnLabel(3)).isEqualTo("AUTHOR");
        assertThat(m.getColumnLabel(4)).isEqualTo("PUBLICATION_YEAR");
        assertThat(m.getColumnLabel(5)).isEqualTo("UNIT_PRICE");
      }
    }
  }

  @Test
  public void insertRows() throws Exception {
    try (Statement s = connection.createStatement()) {
      int count =
          s.executeUpdate(
              "INSERT INTO book (id, title, author, publication_year, unit_price) VALUES"
                  + " (2, 'OCP Java SE 7', 'Mala Gupta', 2015, 44.99)"
                  + ",(3, 'OCP Java SE 8', 'Mala Gupta', 2017, 59.99)");
      assertThat(count).isEqualTo(2);
    }
  }

  @Test
  public void updateRows() throws Exception {
    try (Statement s = connection.createStatement()) {
      int updated =
          s.executeUpdate("UPDATE book" + " SET title = 'Awesome Book'" + " WHERE id = 1");
      try (ResultSet rs = s.executeQuery("SELECT * FROM book WHERE id = 1")) {
        assertThat(updated).isEqualTo(1);
        assertThat(getResults(rs)).isEqualTo("1, Awesome Book, Author, 2017, 10.0\n");
      }
    }
  }

  @Test
  public void deleteRows_existing() throws Exception {
    try (Statement s = connection.createStatement()) {
      int deleted = s.executeUpdate("DELETE FROM book WHERE id = 1");
      assertThat(deleted).isEqualTo(1);
    }
  }

  @Test
  public void deleteRows_nonExistent() throws Exception {
    try (Statement s = connection.createStatement()) {
      int deleted = s.executeUpdate("DELETE FROM book WHERE id > 100");
      assertThat(deleted).isEqualTo(0);
    }
  }
}
