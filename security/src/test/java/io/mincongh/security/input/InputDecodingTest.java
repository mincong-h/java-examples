package io.mincongh.security.input;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

/**
 * @author Mincong Huang
 * @see <a href="https://www.owasp.org/index.php/Path_Traversal">Path Traversal</a>
 */
public class InputDecodingTest {

  @Test
  public void simpleEncoding() throws Exception {
    String[] encodedValues1 = {"%2e%2e%2f", "%2e%2e/", "..%2f"};
    for (String encoded : encodedValues1) {
      String decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.name());
      assertThat(decoded).isEqualTo("../");
    }

    String[] encodedValues2 = {"%2e%2e%5c", "%2e%2e\\", "..%5c"};
    for (String encoded : encodedValues2) {
      String decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.name());
      assertThat(decoded).isEqualTo("..\\");
    }
  }

  @Test
  public void doubleEncoding() throws Exception {
    String[] doubleEncodedValues = {"%252e%252e%255c", "..%255c"};
    for (String doubleEncoded : doubleEncodedValues) {
      String simpleEncoded = URLDecoder.decode(doubleEncoded, StandardCharsets.UTF_8.name());
      String decoded = URLDecoder.decode(simpleEncoded, StandardCharsets.UTF_8.name());
      assertThat(decoded).isEqualTo("..\\");
    }
  }

}
