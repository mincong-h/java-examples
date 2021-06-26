package io.mincong.spring.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@SpringBootApplication
public class AppConfig extends AbstractMongoClientConfiguration {

  @Override
  public MongoClient mongoClient() {
    return MongoClients.create("mongodb://localhost:27017");
  }

  @Override
  protected String getDatabaseName() {
    return "demo";
  }
}
