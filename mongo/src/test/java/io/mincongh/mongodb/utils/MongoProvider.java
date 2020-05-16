package io.mincongh.mongodb.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;

public interface MongoProvider {

  MongoDatabase database();

  MongoCollection<BasicDBObject> userCollection();

  void close();
}
