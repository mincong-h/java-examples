package io.mincong.mongodb.bson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Implementation inspired from an answer of Stack Overflow question "Is there any way for creating
 * Mongo codecs automatically?", provided by Kevin Day. https://stackoverflow.com/a/47949886/4381330
 */
public class JacksonCodecProvider implements CodecProvider {

  private final ObjectMapper objectMapper;

  public JacksonCodecProvider(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
    return new JacksonCodec<T>(objectMapper, codecRegistry, aClass);
  }
}
