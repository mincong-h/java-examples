package io.mincongh.mongodb.docker;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoDatabase;
import io.mincongh.mongodb.spec.FindAbstractIT;
import org.junit.Rule;

public class FindMongoIT extends FindAbstractIT {

  @Rule public FongoRule fongo = new FongoRule(true);

  @Override
  protected MongoDatabase database() {
    return fongo.getDatabase();
  }
}
