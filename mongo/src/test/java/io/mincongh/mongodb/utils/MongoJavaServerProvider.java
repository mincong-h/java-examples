package io.mincongh.mongodb.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class MongoJavaServerProvider implements MongoProvider {

  private final MongoClient client;
  private final MongoServer server;
  private final MongoDatabase database;

  public MongoJavaServerProvider() {
    this.server = new MongoServer(new MemoryBackend());
    var serverAddress = server.bind();
    this.client = new MongoClient(new ServerAddress(serverAddress));
    this.database = client.getDatabase("testdb");
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
    return true;
  }

  @Override
  public boolean isRealMongo() {
    return false;
  }

  @Override
  public boolean isFongo() {
    return false;
  }

  @Override
  public void close() {
    client.close();
    server.shutdown();
  }
}
