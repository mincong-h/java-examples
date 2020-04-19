package io.mincongh.encoding;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Encoding testing.
 *
 * @author Mincong Huang
 */
class EncodingTest {

  @Test
  void readAllLines() throws Exception {
    Path txt = Paths.get(this.getClass().getResource("/iso-8859-1.txt").toURI());
    List<String> lines = Files.readAllLines(txt, StandardCharsets.ISO_8859_1);
    assertThat(lines).containsExactly("reçu");
  }

  @Test
  void readAllBytes() throws Exception {
    Path txt = Paths.get(this.getClass().getResource("/iso-8859-1.txt").toURI());
    byte[] bytes = Files.readAllBytes(txt);
    String content = new String(bytes, StandardCharsets.ISO_8859_1);
    assertThat(content).isEqualTo("reçu\n");
  }

  @Test
  void readBytes() {
    byte[] bytes = {(byte) 0x72, (byte) 0x65, (byte) 0xE7, (byte) 0x75};
    String word = new String(bytes, StandardCharsets.ISO_8859_1);
    assertThat(word).isEqualTo("reçu");
  }

  @Test
  void decodeBytes_iso88591() {
    byte[] bytes = {(byte) 0xE0};
    String word = new String(bytes, StandardCharsets.ISO_8859_1);
    assertThat(word).isEqualTo("à");
  }

  @Test
  void decodeBytes_utf8() {
    byte[] bytes = {(byte) 0xE0};
    String word = new String(bytes, StandardCharsets.UTF_8);
    assertThat(word).isEqualTo("\ufffd");
    assertThat(word).isEqualTo("�");
  }

  @Test
  void encodeBytes_iso88591() {
    byte[] bytes = "à".getBytes(StandardCharsets.ISO_8859_1);
    assertThat(bytes).isEqualTo(new byte[] {(byte) 0xE0});
  }

  @Test
  void encodeBytes_utf8() {
    byte[] bytes = "à".getBytes(StandardCharsets.UTF_8);
    assertThat(bytes).isEqualTo(new byte[] {(byte) 0xC3, (byte) 0xA0});
  }
}
