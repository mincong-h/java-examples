package io.mincongh.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class MongoDatabaseIT {

  private MongoClient client;

  @Before
  public void setUp() {
    client = MongoClients.create("mongodb://localhost:27017");
  }

  @After
  public void tearDown() {
    client.close();
  }

  @Test
  public void testDatabase() {
    /*
     * By default there is only one database called "local".
     */
    assertThat(databaseNames()).containsExactly("local");
    /*
     * You can switch to a nonexistent database, such as "myDB". The
     * corresponding command line is:
     *
     *     use myDB
     *
     * When you first store data for that database, MondoDB create
     * the database for you if that database does not exist. For
     * example, the database is created when creating a new
     * collection called "myCollection".
     */
    MongoDatabase db = client.getDatabase("myDB");
    assertThat(databaseNames()).containsExactly("local");

    db.createCollection("myCollection");
    assertThat(databaseNames()).containsOnly("local", "myDB");
  }

  private Set<String> databaseNames() {
    Set<String> results = new HashSet<>();
    for (String s : client.listDatabaseNames()) {
      results.add(s);
    }
    return results;
  }
}
