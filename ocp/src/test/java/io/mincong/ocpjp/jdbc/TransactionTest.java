package io.mincong.ocpjp.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class TransactionTest extends JdbcTest {

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();

    try (Statement s = connection.createStatement()) {
      s.executeUpdate(
          "CREATE TABLE bank_tx ("
              + "  tx_id INT PRIMARY KEY,"
              + "  account_id VARCHAR(200),"
              + "  tx_type VARCHAR(200),"
              + "  tx_amount REAL,"
              + "  tx_date DATE"
              + ")"
      );
      s.executeUpdate(
          "CREATE TABLE bank_account ("
              + "  account_id VARCHAR(200) PRIMARY KEY,"
              + "  account_name VARCHAR(200),"
              + "  balance REAL"
              + ")"
      );
      s.executeUpdate(
          "INSERT INTO bank_account (account_id, account_name, balance) VALUES"
              + " ('a', 'Client A', 500.0),"
              + " ('b', 'Client B', 500.0)"
      );
    }
  }

  @Test
  public void singleTransaction() throws Exception {
    /*
     * By default, the JDBC API initiates all database changes as
     * soon as you execute SQL queries. To ensure that all or none of
     * a set of database modifications happen, you can define them as
     * a single JDBC transaction. The multiple database changes in a
     * JDBC transaction execute as a single unit so that all or none
     * of them execute.
     *
     * Start transaction by calling `setAutoCommit(false)` on
     * Connection object.
     */
    connection.setAutoCommit(false);
    try (Statement s = connection.createStatement()) {
      int i1 = s.executeUpdate("INSERT INTO bank_tx VALUES (1, 'a', 'debit', 50.0, '2017-12-13')");
      int i2 = s.executeUpdate("INSERT INTO bank_tx VALUES (2, 'b', 'credit', 50.0, '2017-12-13')");
      int u1 = s.executeUpdate("UPDATE bank_account SET balance = 450 WHERE account_id = 'a'");
      int u2 = s.executeUpdate("UPDATE bank_account SET balance = 550 WHERE account_id = 'b'");
      /*
       * At the end of the execution of all the SQL statements, the
       * code `commit()` is called.
       */
      connection.commit();

      /*
       * Method `executeUpdate()` returns a count of the rows that
       * are or would be affected in the database for row insertions,
       * modifications, and deletion. The value is returned even if
       * the statement isn't committed. This method returns 0 for SQL
       * DDL(*) statements, which create database objects and modify
       * their structure or delete them.
       *
       * DDL: Data Definition Language.
       */
      assertThat(i1).isEqualTo(1);
      assertThat(i2).isEqualTo(1);
      assertThat(u1).isEqualTo(1);
      assertThat(u2).isEqualTo(1);
    } catch (SQLException e) {
      /*
       * If any SQL exception is thrown during the execution of any
       * of the SQL statements, the exception handler calls
       * `rollback()` on the `Connection` object so that a partial
       * set of statements isn't persisted in the database.
       */
      connection.rollback();
    }
  }

  @Test
  public void savepoint() throws Exception {
    /*
     * A JDBC connection can have two automatic `commit` (auto-
     * commit) modes:
     * - true
     * - false
     *
     * To work with a transaction, you must set the auto-commit mode
     * to `false` or methods `rollback()` and `commit()` will throw a
     * `SQLException`.
     */
    connection.setAutoCommit(false);
    try (Statement s = connection.createStatement()) {
      s.executeUpdate("INSERT INTO bank_tx VALUES (1, 'a', 'debit', 50.0, '2017-12-13')");
      s.executeUpdate("UPDATE bank_account SET balance = 450 WHERE account_id = 'a'");
      Savepoint savepointA = connection.setSavepoint("A");

      s.executeUpdate("INSERT INTO bank_tx VALUES (2, 'b', 'credit', 50.0, '2017-12-13')");
      s.executeUpdate("UPDATE bank_account SET balance = 550 WHERE account_id = 'b'");
      connection.setSavepoint(); // unnamed savepoint

      connection.rollback(savepointA);
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
    }

    // Based on savepoint A, only client A has been updated.
    try (Statement s = connection.createStatement()) {
      try (ResultSet rs = s.executeQuery("SELECT * FROM bank_tx")) {
        String str = getResults(rs);
        assertThat(str).contains("1, a, debit, 50.0, 2017-12-13");
        assertThat(str).doesNotContain("2, b, credit, 50.0, 2017-12-13");
      }
      try (ResultSet rs = s.executeQuery("SELECT * FROM bank_account")) {
        String str = getResults(rs);
        assertThat(str).contains("a, Client A, 450.0");
        assertThat(str).contains("b, Client B, 500.0");
      }
    }
  }

}
