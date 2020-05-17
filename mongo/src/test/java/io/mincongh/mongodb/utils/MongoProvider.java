package io.mincongh.mongodb.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public interface MongoProvider {

  MongoDatabase database();

  MongoCollection<BasicDBObject> userCollection();

  /**
   * Whether this provider is implemented by Mongo Java Server
   *
   * @return true if this provider is implemented by Mongo Java Server, else false
   * @see <a href="https://github.com/bwaldvogel/mongo-java-server">Mongo Java Server</a>
   */
  boolean isMongoJavaServer();

  void close();
}
