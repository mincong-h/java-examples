package io.mincongh.mongodb.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FongoProvider implements MongoProvider {

  private final MongoDatabase database;

  public FongoProvider(MongoDatabase database) {
    this.database = database;
    database.createCollection("users");
  }

  @Override
  public MongoDatabase database() {
    return database;
  }

  @Override
  public MongoCollection<BasicDBObject> userCollection() {
    return database.getCollection("users", BasicDBObject.class);
  }

  @Override
  public void close() {}
}
