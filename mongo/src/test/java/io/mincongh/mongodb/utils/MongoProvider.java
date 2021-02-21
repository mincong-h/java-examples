package io.mincongh.mongodb.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public interface MongoProvider {

  MongoDatabase database();

  MongoCollection<BasicDBObject> userCollection();

  /**
   * Whether this provider is implemented by a real MongoDB.
   *
   * @return true if this provider is implemented by a real MongoDB, else false
   */
  boolean isRealMongo();

  /**
   * Whether this provider is implemented by Fongo.
   *
   * <p>Connecting to real MongoDB via Fongo will return false, because the actual implementation is
   * MongoDB not Fongo.
   *
   * @return true if this provider is implemented by Fongo, else false
   * @see <a href="https://github.com/fakemongo/fongo/">Fongo</a>
   */
  boolean isFongo();

  void close();
}
