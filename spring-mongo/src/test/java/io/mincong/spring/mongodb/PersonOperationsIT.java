package io.mincong.spring.mongodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest(classes = MongoConfig.class)
class PersonOperationsIT {
  @Autowired MongoOperations operations;

  Person sansa, arya;

  @BeforeEach
  void setUp() {
    this.sansa = Person.builder().name("Sansa Stark").age(20).build();
    this.arya = Person.builder().name("Arya Stark").age(20).build();

    operations.save(sansa);
    operations.save(arya);
    assertThat(operations.findAll(Person.class)).hasSize(2);
  }

  @AfterEach
  void tearDown() {
    operations.remove(new Query(), Person.class);
  }

  @Test
  void queryId() {
    var query = query(where("name").is("Sansa Stark"));
    assertThat(operations.find(query, Person.class)).contains(sansa);
  }
}
