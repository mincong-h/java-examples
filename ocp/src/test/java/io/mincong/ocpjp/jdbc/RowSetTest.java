package io.mincong.ocpjp.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.RowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
@Ignore("java.sql.SQLException: No suitable driver found for jdbc:h2:mem:test")
public class RowSetTest extends JdbcTest {

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    try (Statement s = connection.createStatement()) {
      s.executeUpdate("CREATE TABLE book ( id INT PRIMARY KEY, name VARCHAR(100) )");
      s.executeUpdate("INSERT INTO book VALUES ( 1, 'A' ), ( 2, 'B' )");
    }
  }

  @Test
  public void jdbcRowSet() throws Exception {
    RowSetFactory rowSetFactory = RowSetProvider.newFactory();
    Map<Integer, String> books = new HashMap<>();

    try (RowSet rowSet = rowSetFactory.createJdbcRowSet()) {
      rowSet.setUrl(URL);
      rowSet.setCommand("SELECT * FROM book");
      rowSet.execute();

      while (rowSet.next()) {
        int id = rowSet.getInt("id");
        String name = rowSet.getString("name");
        books.put(id, name);
      }
    }
    assertThat(books).containsExactly(entry(1, "A"), entry(2, "B"));
  }

}
