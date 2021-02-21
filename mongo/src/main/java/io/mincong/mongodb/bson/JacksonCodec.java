package io.mincong.mongodb.bson;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.RawBsonDocument;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Implementation inspired from an answer of Stack Overflow question "Is there any way for creating
 * Mongo codecs automatically?", provided by Kevin Day. https://stackoverflow.com/a/47949886/4381330
 */
public class JacksonCodec<T> implements Codec<T> {
  private final ObjectMapper objectMapper;
  private final Codec<RawBsonDocument> rawBsonDocumentCodec;
  private final Class<T> aClass;

  public JacksonCodec(ObjectMapper objectMapper, CodecRegistry codecRegistry, Class<T> aClass) {
    this.objectMapper = objectMapper;
    this.rawBsonDocumentCodec = codecRegistry.get(RawBsonDocument.class);
    this.aClass = aClass;
  }

  @Override
  public T decode(BsonReader reader, DecoderContext decoderContext) {
    try {
      RawBsonDocument document = rawBsonDocumentCodec.decode(reader, decoderContext);
      String json = document.toJson();
      return objectMapper.readValue(json, aClass);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
    try {
      String json = objectMapper.writeValueAsString(value);
      rawBsonDocumentCodec.encode(writer, RawBsonDocument.parse(json), encoderContext);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public Class<T> getEncoderClass() {
    return aClass;
  }
}
