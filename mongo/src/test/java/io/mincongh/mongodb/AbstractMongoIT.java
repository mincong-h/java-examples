package io.mincongh.mongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.mincong.mongodb.bson.JacksonCodecProvider;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public abstract class AbstractMongoIT {

  protected MongoClient client;
  protected MongoDatabase db;

  @BeforeEach
  void setUpMongo() {
    var mongoSettings =
        MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
            .codecRegistry(createCodecRegistry())
            .build();
    client = MongoClients.create(mongoSettings);
    db = client.getDatabase("test");
  }

  private CodecRegistry createCodecRegistry() {
    var defaultRegistry = MongoClientSettings.getDefaultCodecRegistry();
    var jacksonRegistry =
        CodecRegistries.fromProviders(
            new BsonValueCodecProvider(), new JacksonCodecProvider(new ObjectMapper()));
    return CodecRegistries.fromRegistries(defaultRegistry, jacksonRegistry);
  }

  @AfterEach
  void tearDownMongo() {
    try {
      db.drop();
    } finally {
      client.close();
    }
  }
}
