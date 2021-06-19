package io.mincong.spring.mongodb;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Document(collection = "people")
public class Person {
  private final String name;
  private final Integer age;
  private @Getter ObjectId id;
}
