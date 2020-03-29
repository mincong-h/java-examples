package io.mincongh.mongodb;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoDatabase;
import org.junit.Rule;

public class UpdateMongoIT extends UpdateAbstractIT {

  @Rule public FongoRule fongo = new FongoRule(true);

  @Override
  protected MongoDatabase database() {
    return fongo.getDatabase();
  }
}
