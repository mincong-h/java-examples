package io.mincong.spring.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  @Autowired MongoOperations operations;

  public Optional<Person> findPerson(String name) {
    var p = operations.findOne(query(where("name").is(name)), Person.class);
    return Optional.ofNullable(p);
  }
}
