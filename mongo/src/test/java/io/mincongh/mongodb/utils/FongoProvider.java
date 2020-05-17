package io.mincongh.mongodb.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FongoProvider implements MongoProvider {

  private final MongoDatabase database;
  private final boolean isReal;

  public FongoProvider(MongoDatabase database, boolean isReal) {
    this.database = database;
    this.isReal = isReal;
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
  public boolean isMongoJavaServer() {
    return false;
  }

  @Override
  public boolean isRealMongo() {
    return isReal;
  }

  @Override
  public boolean isFongo() {
    return !isRealMongo();
  }

  @Override
  public void close() {}
}
