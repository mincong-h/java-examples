package io.mincongh.xml.xstream.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import io.mincongh.xml.xstream.model.Person;

/**
 * @author Mincong Huang
 */
final class XStreamUtil {

  /**
   * Does not require XPP3 library
   */
  private static final XStream X_STREAM;

  static {
    X_STREAM = new XStream(new DomDriver());
    /*
     * The annotation-alias will be taken into account in
     * serialization and deserialization, if and only if method
     * `processAnnotation` is called.
     */
    X_STREAM.processAnnotations(Person.class);
  }

  private XStreamUtil() {
    // Utility class, do not instantiate
  }

  static <T> String serialize(T t) {
    return X_STREAM.toXML(t);
  }

  @SuppressWarnings({"unchecked", "unused"})
  static <T> T deserialize(String xml, Class<T> returnType) {
    return (T) X_STREAM.fromXML(xml);
  }

}
