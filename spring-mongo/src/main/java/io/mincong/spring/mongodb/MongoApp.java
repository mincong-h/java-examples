package io.mincong.spring.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class MongoApp {

  private static final Logger log = LoggerFactory.getLogger(MongoApp.class);

  public static void main(String[] args) {

    MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "database");
    mongoOps.insert(new Person("Joe", 34));

    log.info("{}", mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));

    mongoOps.dropCollection("person");
  }
}
