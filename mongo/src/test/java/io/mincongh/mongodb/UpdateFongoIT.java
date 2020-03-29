package io.mincongh.mongodb;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoDatabase;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class UpdateFongoIT extends UpdateAbstractIT {

  @Rule public FongoRule fongo = new FongoRule(false);

  @Override
  protected MongoDatabase database() {
    return fongo.getDatabase();
  }

  @Test
  @Override
  @Ignore("https://github.com/fakemongo/fongo/issues/375")
  public void update_elemMatch_element() {}
}
