package io.mincong.ocpjp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;

/** @author Mincong Huang */
public abstract class JdbcTest {

  /**
   * The JDBC URL determines the appropriate driver for a given URL string. For example:
   *
   * <pre>
   * jdbc:sub-protocol://&lt;host&gt;:&lt;port&gt;/&lt;database_name&gt;
   * </pre>
   */
  static final String URL = "jdbc:h2:mem:test";

  Connection connection;

  @Before
  public void setUp() throws Exception {
    /*
     * Since JDBC 4.0, calling method `Class.forName("xxx");` is no
     * longer necessary.
     */
    connection = DriverManager.getConnection(URL);
  }

  @After
  public void tearDown() throws Exception {
    connection.close();
  }

  String getResults(ResultSet rs) throws SQLException {
    int max = rs.getMetaData().getColumnCount();
    StringBuilder sb = new StringBuilder();
    while (rs.next()) {
      List<String> fields = new ArrayList<>(max);
      // Database index starts at 1.
      for (int i = 1; i <= max; i++) {
        fields.add(rs.getString(i));
      }
      sb.append(String.join(", ", fields)).append('\n');
    }
    return sb.toString();
  }

  int getSize(ResultSet rs) throws SQLException {
    int count = 0;
    while (rs.next()) {
      count++;
    }
    return count;
  }
}
