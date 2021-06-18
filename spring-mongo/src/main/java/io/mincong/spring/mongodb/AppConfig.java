package io.mincong.spring.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// What is the difference with org.springframework.context.annotation.Configuration?
public class AppConfig {

  /*
   * Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.
   */
  public @Bean MongoClient mongoClient() {
    return MongoClients.create("mongodb://localhost:27017");
  }
}
