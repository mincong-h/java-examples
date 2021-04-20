package io.mincongh.akka.exception_handling;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class DocumentManagerTest {
  private static final int MAX_RETRIES = 5;

  private ActorSystem system;
  private TestKit testKit;

  @Mock ExternalServiceClient externalServiceClient;

  @BeforeEach
  void setUp() {
    system = ActorSystem.create();
    testKit = new TestKit(system);
  }

  @AfterEach
  void tearDown() {
    TestKit.shutdownActorSystem(system);
    system = null;
  }

  @Test
  @Timeout(60) // avoid incorrect implementation
  void exceptionWithoutBackoff_TooManyRequestsException() {
    // Given
    var count = new AtomicInteger();
    when(externalServiceClient.createDocument(anyString()))
        .thenAnswer(
            (Answer<String>)
                invocation -> {
                  throw new TooManyRequestsException("" + count.getAndIncrement());
                });
    var maxBackOff = Duration.ofSeconds(3);
    var docWriter =
        system.actorOf(
            DocumentManager.props(
                externalServiceClient, Duration.ofMillis(1), maxBackOff, MAX_RETRIES));

    // When
    docWriter.tell(DocumentManager.CREATE_DOC_WITHOUT_BACKOFF, testKit.getRef());

    // Then
    testKit.expectNoMessage(maxBackOff);
    assertThat(count.get()).isGreaterThan(1 + MAX_RETRIES); // initial (1) + retries (N)
  }

  @Test
  @Timeout(60) // avoid incorrect implementation
  void exceptionWithoutBackoff_OtherException() {
    // Given
    var count = new AtomicInteger();
    when(externalServiceClient.createDocument(anyString()))
        .thenAnswer(
            (Answer<String>)
                invocation -> {
                  throw new IllegalStateException("" + count.getAndIncrement());
                });
    var maxBackOff = Duration.ofSeconds(3);
    var docWriter =
        system.actorOf(
            DocumentManager.props(
                externalServiceClient, Duration.ofMillis(1), maxBackOff, MAX_RETRIES));

    // When
    docWriter.tell(DocumentManager.CREATE_DOC_WITHOUT_BACKOFF, testKit.getRef());

    // Then
    testKit.expectNoMessage(maxBackOff);
    assertThat(count.get()).isGreaterThan(1 + MAX_RETRIES); // initial (1) + retries (N)
  }

  @Test
  @Timeout(60) // avoid incorrect implementation
  void exceptionBackoff_TooManyRequestsException() {
    // Given
    var count = new AtomicInteger();
    when(externalServiceClient.createDocument(anyString()))
        .thenAnswer(
            (Answer<String>)
                invocation -> {
                  throw new TooManyRequestsException("" + count.getAndIncrement());
                });
    var maxBackOff = Duration.ofSeconds(3);
    var docWriter =
        system.actorOf(
            DocumentManager.props(
                externalServiceClient, Duration.ofMillis(1), maxBackOff, MAX_RETRIES));

    // When
    docWriter.tell(DocumentManager.CREATE_DOC_WITH_BACKOFF, testKit.getRef());

    // Then
    testKit.expectNoMessage(maxBackOff);
    assertThat(count.get()).isEqualTo(1 + MAX_RETRIES); // initial (1) + retries (N)
  }

  @Test
  @Timeout(60) // avoid incorrect implementation
  void exceptionBackoff_OtherException() {
    // Given
    var count = new AtomicInteger();
    when(externalServiceClient.createDocument(anyString()))
        .thenAnswer(
            (Answer<String>)
                invocation -> {
                  throw new IllegalStateException("" + count.getAndIncrement());
                });
    var maxBackOff = Duration.ofSeconds(3);
    var docWriter =
        system.actorOf(
            DocumentManager.props(
                externalServiceClient, Duration.ofMillis(1), maxBackOff, MAX_RETRIES));

    // When
    docWriter.tell(DocumentManager.CREATE_DOC_WITH_BACKOFF, testKit.getRef());

    // Then
    testKit.expectNoMessage(maxBackOff);
    assertThat(count.get()).isEqualTo(1);
  }
}
