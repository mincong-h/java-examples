package io.mincong.logback;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * Demonstration of Logback and SLF4J.
 *
 * @author Mincong Huang
 * @blog https://mincong.io/2020/02/02/logback-test-logging-event/
 */
public class AppTest {

  /*
   * Create a custom appender for logging events, which allows to
   * capture logs for assertions.
   */
  private ListAppender<ILoggingEvent> appender;

  /*
   * Retrieve the Logback logger used by `App.class`. Declaring it
   * here allows to add an appender for logging events.
   */
  private final Logger appLogger = (Logger) LoggerFactory.getLogger(App.class);

  @Before
  public void setUp() {
    appender = new ListAppender<>();
    appender.start();
    appLogger.addAppender(appender);
  }

  @After
  public void tearDown() {
    appLogger.detachAppender(appender);
  }

  @Test
  public void testSayHi() {
    App.sayHi("Java");
    App.sayHi("Logback");

    assertThat(appender.list)
        .extracting(ILoggingEvent::getFormattedMessage)
        .containsExactly("Hi, Java!", "Hi, Logback!");

    // `ILoggingEvent#getMessage()` is probably NOT what you want:
    // it returns the message before variable injection
    assertThat(appender.list)
        .extracting(ILoggingEvent::getMessage)
        .containsExactly("Hi, {}!", "Hi, {}!");

    assertThat(appender.list)
        .extracting(ILoggingEvent::getLevel) //
        .containsOnly(Level.INFO);

    assertThat(appender.list)
        .extracting(ILoggingEvent::getLoggerName) //
        .containsOnly("io.mincong.logback.App");

    assertThat(appender.list)
        .extracting(ILoggingEvent::getThrowableProxy) //
        .containsExactly(null, null);
  }
}
