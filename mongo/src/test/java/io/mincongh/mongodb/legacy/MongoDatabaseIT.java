package io.mincongh.mongodb.legacy;

import com.mongodb.client.MongoDatabase;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class MongoDatabaseIT extends AbstractMongoIT {

  @Override
  protected Class<?> getTestClass() {
    return MongoDatabaseIT.class;
  }

  @Test
  public void testDatabase() {
    /*
     * By default there is only one database called "local".
     */
    assertThat(databaseNames()).containsOnly("local", "config", "admin");
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
    assertThat(databaseNames()).containsOnly("local", "config", "admin");

    db.createCollection("myCollection");
    assertThat(databaseNames()).containsOnly("local", "config", "admin", "myDB");
  }

  private Set<String> databaseNames() {
    Set<String> results = new HashSet<>();
    for (String s : client.listDatabaseNames()) {
      results.add(s);
    }
    return results;
  }
}
