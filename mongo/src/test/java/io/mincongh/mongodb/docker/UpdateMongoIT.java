package io.mincongh.mongodb.docker;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoDatabase;
import io.mincongh.mongodb.spec.UpdateAbstractIT;
import org.junit.Rule;

public class UpdateMongoIT extends UpdateAbstractIT {

  @Rule public FongoRule fongo = new FongoRule(true);

  @Override
  protected MongoDatabase database() {
    return fongo.getDatabase();
  }
}
