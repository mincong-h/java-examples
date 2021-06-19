package io.mincong.spring.mongodb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "people")
public class Person {
  private final String name;
  private final Integer age;
  private @Getter ObjectId id;
}
