package io.mincong.ocpjp.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.Before;
import org.junit.Test;

/** @author Mincong Huang */
public class PreparedStatementTest extends JdbcTest {

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    try (Statement s = connection.createStatement()) {
      s.executeUpdate("CREATE TABLE book ( id INT PRIMARY KEY, name VARCHAR(100) )");
    }
  }

  @Test
  public void preparedStatement1() throws Exception {
    try (PreparedStatement s = connection.prepareStatement("INSERT INTO book VALUES (1, ?)")) {
      s.setString(1, "A");
      s.executeUpdate();
    }
    try (Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM book")) {
      assertThat(getSize(rs)).isEqualTo(1);
    }
  }

  @Test
  public void preparedStatement2() throws Exception {
    try (Statement s = connection.createStatement()) {
      s.executeUpdate("INSERT INTO book VALUES (1,'A'), (2, 'B')");
    }
    try (PreparedStatement s = connection.prepareStatement("SELECT * FROM book WHERE id = ?")) {
      s.setInt(1, 2);
      try (ResultSet rs = s.executeQuery()) {
        assertThat(getResults(rs)).isEqualTo("2, B\n");
      }
    }
  }
}
