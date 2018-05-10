package io.mincong.ocpjp.autoboxing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
@SuppressWarnings("unused")
public class WrapperClassTest {

  @Test
  public void initWithAutoBoxing() throws Exception {
    Boolean b = true;
    Character c = 'a';
    Integer i = 10;
    Double d = 1.0;
  }

  @Test
  public void initWithPrimitive() throws Exception {
    Boolean b = new Boolean(true);
    Character c = new Character('a');
    Integer i = new Integer(10);
    Double d = new Double(1.0);
  }

  @Test
  public void initWithStaticMethod() throws Exception {
    Boolean b = Boolean.valueOf("True");
  }

  @Test
  public void retrievePrimitiveWithXxxValue() throws Exception {
    Boolean B = true;
    Character C = 'a';
    Integer I = 10;
    Double D = 1.0;

    boolean b = B.booleanValue();
    char c = C.charValue();
    int i = I.intValue();
    double d = D.doubleValue();
  }

  @Test
  public void parseStringValueWithParseXxx() throws Exception {
    double d = Double.parseDouble("1.23");
    boolean b = Boolean.parseBoolean("false");
  }

  @Test
  public void comparingObjects() throws Exception {
    Map<Double, String> map = new HashMap<>();
    map.put(1.0, "1.0");
    map.put(2.0, "2.0");

    String value1 = map.get(1.0);
    assertThat(value1).isEqualTo("1.0");
    /*
     * Cannot use a `Float` object to retrieve the value that was
     * added to a `HashMap` using a `Double` instance.
     */
    String value2 = map.get(new Float(2.0));
    assertThat(value2).isNull();
  }

}
