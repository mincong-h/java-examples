package io.mincong.ocpjp.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;

/** @author Mincong Huang */
public class DriverManagerTest extends JdbcTest {

  @Test(expected = SQLException.class)
  public void noSuitableDriver() throws Exception {
    DriverManager.getConnection("jdbc:mysql://localhost");
  }

  @Test(expected = SQLException.class)
  public void accessDenied() throws Exception {
    DriverManager.getConnection(URL, "foo", "bar");
  }

  @Test
  public void createTable() throws Exception {
    try (Statement s = connection.createStatement()) {
      int u = s.executeUpdate("CREATE TABLE book ( id INT PRIMARY KEY )");
      try (ResultSet rs = s.executeQuery("SHOW TABLES")) {
        assertThat(getResults(rs)).contains("BOOK");
        assertThat(u).isZero();
      }
    }
  }
}
