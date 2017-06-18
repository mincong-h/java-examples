import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeNotNull;

import org.junit.Test;

public class MavenPropertyTest {

  /**
   * Tests property defined via command-line. It demonstrates that a
   * property defined via Maven can be captured by the test and the
   * value obtained is the expected one. Run the test using the
   * following command:
   * <pre><code>
   * mvn clean test -Dtest=MavenPropertyTest -DmyProp=definedByCLI
   * </code></pre>
   */
  @Test
  public void readPropertyDefinedByCLI() throws Exception {
    assumeNotNull(System.getProperty("myProp"));
    assertEquals("definedByCLI", System.getProperty("myProp"));
  }

}
