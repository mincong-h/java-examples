package io.mincongh.mongodb.fongo;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoDatabase;
import io.mincongh.mongodb.spec.FindAbstractIT;
import org.junit.Rule;

public class FindFongoIT extends FindAbstractIT {

  @Rule public FongoRule fongo = new FongoRule(false);

  @Override
  protected MongoDatabase database() {
    return fongo.getDatabase();
  }
}
