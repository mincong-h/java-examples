package io.mincong.spring.mongodb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Document(collection = "people")
public class Person {
  private ObjectId id;
  private String name;
  private int age;
}
