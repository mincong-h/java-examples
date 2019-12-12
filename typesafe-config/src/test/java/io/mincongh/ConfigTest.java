package io.mincongh;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigMemorySize;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigTest {

  @Before
  public void setUp() {
    System.setProperty("demo.sys", "definedBySystemProperty");
  }

  @After
  public void tearDown() {
    System.clearProperty("demo.sys");
  }

  @Test
  public void itShouldLoadConfig() {
    Config config = ConfigFactory.load();

    // retrieve key
    assertThat(config.getString("demo.ref")).isEqualTo("definedByReferenceConf");

    // retrieve object
    Config demoConfig = config.getConfig("demo");
    assertThat(demoConfig.getString("ref")).isEqualTo("definedByReferenceConf");
    assertThat(demoConfig.getString("sys")).isEqualTo("definedBySystemProperty");

    // loading order
    //
    // The convenience method ConfigFactory.load() loads the following (first-listed are higher
    // priority):
    //
    // - `system properties`
    // - `application.conf` (all resources on classpath with this name)
    // - `application.json` (all resources on classpath with this name)
    // - `application.properties` (all resources on classpath with this name)
    // - `reference.conf` (all resources on classpath with this name)
    //
    assertThat(config.getString("demo.ref")).isEqualTo("definedByReferenceConf");
    assertThat(config.getString("demo.app")).isEqualTo("definedByApplicationConf");
    assertThat(config.getString("demo.sys")).isEqualTo("definedBySystemProperty");
  }

  @Test
  public void isShouldSupportTypes() {
    String s = "";

    s += "booleanKey: true\n";
    s += "intKey: 1\n";
    s += "doubleKey: 1.0\n";
    s += "stringKey: value\n";

    s += "booleanList: [true, false]\n";
    s += "intList: [1, 2, 3]\n";
    s += "doubleList: [1.0, 2.0, 3.0]\n";
    s += "stringList: [v1, v2, v3]\n";

    Config config = ConfigFactory.parseString(s);

    assertThat(config.getBoolean("booleanKey")).isTrue();
    assertThat(config.getInt("intKey")).isEqualTo(1);
    assertThat(config.getLong("intKey")).isEqualTo(1L);
    assertThat(config.getDouble("doubleKey")).isEqualTo(1.0);
    assertThat(config.getString("stringKey")).isEqualTo("value");

    assertThat(config.getBooleanList("booleanList")).containsExactly(true, false);
    assertThat(config.getIntList("intList")).containsExactly(1, 2, 3);
    assertThat(config.getLongList("intList")).containsExactly(1L, 2L, 3L);
    assertThat(config.getDoubleList("doubleList")).containsExactly(1.0, 2.0, 3.0);
    assertThat(config.getStringList("stringList")).containsExactly("v1", "v2", "v3");
  }

  @Test
  public void itShouldRecognizeInlineStructure() {
    Config u1 = ConfigFactory.parseString("user { firstName: Foo, lastName: Bar }");
    assertThat(u1.getString("user.firstName")).isEqualTo("Foo");
    assertThat(u1.getString("user.lastName")).isEqualTo("Bar");
    Config u2 = ConfigFactory.parseString("user { firstName = Foo, lastName = Bar }");
    assertThat(u2.getString("user.firstName")).isEqualTo("Foo");
    assertThat(u2.getString("user.lastName")).isEqualTo("Bar");
  }

  @Test
  public void itShouldHandleInclude() {
    Config cfg = ConfigFactory.load("myApp");
    assertThat(cfg.getString("moduleA.msg")).isEqualTo("Hello from Module A");
    assertThat(cfg.getString("moduleB.msg")).isEqualTo("Hello from Module B");
    assertThat(cfg.getString("app.msg")).isEqualTo("Hello from App");
  }

  @Test
  public void itShouldSupportCombination() {
    Config config = ConfigFactory.load();
    assertThat(config.getString("demo.firstName")).isEqualTo("Mincong");
    assertThat(config.getString("demo.lastName")).isEqualTo("Huang");
    assertThat(config.getString("demo.fullName")).isEqualTo("Mincong Huang");
  }

  @Test
  public void itShouldSupportMemorySize() {
    String s = "";

    s += "minSize: 1K\n";
    s += "maxSize: 1M\n";

    Config config = ConfigFactory.parseString(s);
    assertThat(config.getMemorySize("minSize")).isEqualTo(ConfigMemorySize.ofBytes(1024L));
    assertThat(config.getMemorySize("maxSize")).isEqualTo(ConfigMemorySize.ofBytes(1024L * 1024L));
  }

  @Test
  public void itShouldSupportDuration() {
    String s = "";

    s += "minDuration: 1s\n";
    s += "maxDuration: 1m\n";

    // See SimeConfig#parseDuration
    // ---
    // TimeUnit.MILLISECONDS: ms, millis, milliseconds
    // TimeUnit.MICROSECONDS: us, micros, microseconds
    // TimeUnit.NANOSECONDS:  ns, nanos, nanoseconds
    // TimeUnit.DAYS:          d, days
    // TimeUnit.HOURS:         h, hours
    // TimeUnit.SECONDS:       s, seconds
    // TimeUnit.MINUTES:       m, minutes
    Config config = ConfigFactory.parseString(s);
    assertThat(config.getDuration("minDuration", TimeUnit.SECONDS))
        .isEqualTo(TimeUnit.SECONDS.toSeconds(1));
    assertThat(config.getDuration("minDuration")).isEqualTo(Duration.ofSeconds(1));
    assertThat(config.getDuration("maxDuration", TimeUnit.SECONDS))
        .isEqualTo(TimeUnit.SECONDS.toSeconds(60));
    assertThat(config.getDuration("maxDuration")).isEqualTo(Duration.ofMinutes(1));
  }
}
