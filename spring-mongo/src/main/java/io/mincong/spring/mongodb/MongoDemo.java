package io.mincong.spring.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import com.mongodb.client.MongoClients;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

public class MongoDemo {

  private static final Logger log = LoggerFactory.getLogger(MongoDemo.class);

  public static void main(String[] args) {

    MongoOperations mongoOps =
        new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "database"));
    Person p = Person.builder().name("Joe").age(34).build();

    // Insert is used to initially store the object into the database.
    mongoOps.insert(p);
    log.info("Insert: " + p);

    // Find
    p = mongoOps.findById(p.getId(), Person.class);
    log.info("Found: " + p);

    // Update
    mongoOps.updateFirst(query(where("name").is("Joe")), update("age", 35), Person.class);
    p = mongoOps.findOne(query(where("name").is("Joe")), Person.class);
    log.info("Updated: " + p);

    // Delete
    mongoOps.remove(p);

    // Check that deletion worked
    List<Person> people = mongoOps.findAll(Person.class);
    log.info("Number of people = : " + people.size());

    mongoOps.dropCollection(Person.class);
  }
}
