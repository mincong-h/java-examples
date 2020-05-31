import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

class MavenPropertyTest {

  /**
   * Tests property defined via command-line. It demonstrates that a property defined via Maven can
   * be captured by the test and the value obtained is the expected one. Run the test using the
   * following command:
   *
   * <pre><code>
   * mvn clean test -Dtest=MavenPropertyTest -DmyProp=definedByCLI
   * </code></pre>
   */
  @Test
  void readPropertyDefinedByCLI() throws Exception {
    assumeTrue(System.getProperty("myProp") != null);
    assertEquals("definedByCLI", System.getProperty("myProp"));
  }
}
