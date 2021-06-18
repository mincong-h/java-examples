package io.mincong.spring.mongodb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest(classes = {PersonService.class, AppConfig.class})
class PersonServiceIT {

  @Autowired MongoOperations operations;
  @Autowired PersonService personService;

  Person sansa, arya;

  @BeforeEach
  void setUp() {
    this.sansa = new Person("60ccca5197813b3eb8b607a1", "Sansa Stark", 20);
    this.arya = new Person("60ccbd71880d9a1c9e1f6c78", "Arya Stark", 20);

    operations.save(sansa);
    operations.save(arya);
  }

  @AfterEach
  void tearDown() {
    operations.remove(new Query(), Person.class);
  }

  @Test
  void queryId() {
    var optPerson = personService.findPerson("Sansa Stark");
    assertThat(optPerson).hasValue(sansa);
  }
}
