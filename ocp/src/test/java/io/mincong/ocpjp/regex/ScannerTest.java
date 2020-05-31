package io.mincong.ocpjp.regex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;

/** @author Mincong Huang */
public class ScannerTest {

  @Test
  public void classical() throws Exception {
    Scanner scanner = new Scanner("Hello \tworld \nMy friend");
    List<String> tokens = new ArrayList<>();
    // Do not confuse `hasNext()` and `next()`.
    // `hasNext()` only returns true / false, but does not advance.
    while (scanner.hasNext()) {
      tokens.add(scanner.next());
    }
    assertThat(tokens).containsExactly("Hello", "world", "My", "friend");
  }

  @Test
  public void useDelimiter1() throws Exception {
    Scanner scanner = new Scanner("split0by000digits");
    scanner.useDelimiter("\\d+");
    List<String> tokens = new ArrayList<>();
    while (scanner.hasNext()) {
      tokens.add(scanner.next());
    }
    assertThat(tokens).containsExactly("split", "by", "digits");
  }

  @Test
  public void userDelimiter2() throws Exception {
    Scanner scanner = new Scanner("?split by customized solution!");
    scanner.useDelimiter("[\\sA-Za-z]+");
    List<String> tokens = new ArrayList<>();
    while (scanner.hasNext()) {
      tokens.add(scanner.next());
    }
    assertThat(tokens).containsExactly("?", "!");
  }

  @Test
  public void userDelimiter3() throws Exception {
    Scanner scanner = new Scanner("one,two,three");
    scanner.useDelimiter(",");
    List<String> tokens = new ArrayList<>();
    while (scanner.hasNext()) {
      tokens.add(scanner.next());
    }
    assertThat(tokens).containsExactly("one", "two", "three");
  }

  @Test
  public void userDelimiter4() throws Exception {
    Scanner scanner = new Scanner("1 1 calculate 2 the 3 sum 5 please 8");
    scanner.useDelimiter("[\\sA-Za-z]+");
    int total = 0;
    while (scanner.hasNext()) {
      total += scanner.nextInt();
    }
    assertThat(total).isEqualTo(1 + 1 + 2 + 3 + 5 + 8);
  }

  @Test
  public void findInLine() throws Exception {
    Scanner scanner = new Scanner("ABC 123.456 cde 789");
    scanner.findInLine("\\p{Alpha}+[\\d]+\\.[\\d]+\\p{Alpha}+[\\d]+");

    String s1 = scanner.next();
    double d2 = scanner.nextDouble();
    String s3 = scanner.next();
    int i4 = scanner.nextInt();

    assertThat(s1).isEqualTo("ABC");
    assertThat(d2).isEqualTo(123.456);
    assertThat(s3).isEqualTo("cde");
    assertThat(i4).isEqualTo(789);
  }
}
