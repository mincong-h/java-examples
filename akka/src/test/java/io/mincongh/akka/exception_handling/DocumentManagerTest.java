package io.mincongh.akka.exception_handling;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.transport.RemoteTransportException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class DocumentManagerTest {
  private static ActorSystem system;

  @BeforeAll
  static void beforeClass() {
    system = ActorSystem.create();
  }

  @AfterAll
  static void teardown() {
    TestKit.shutdownActorSystem(system);
    system = null;
  }

  private TestKit testKit;

  @Mock DocumentManager.IndicesClient indicesClient;

  @BeforeEach
  void setUp() {
    testKit = new TestKit(system);
  }

  @Test
  @Timeout(60) // avoid incorrect implementation
  void exceptionBackoff() {
    // Given
    var count = new AtomicInteger();
    when(indicesClient.create(any(CreateIndexRequest.class), any()))
        .thenAnswer(
            (Answer<Void>)
                invocation -> {
                  throw new RemoteTransportException("" + count.getAndIncrement(), null);
                });
    var maxBackOff = Duration.ofSeconds(3);
    var docWriter =
        system.actorOf(DocumentManager.props(indicesClient, Duration.ofMillis(1), maxBackOff));

    // When
    docWriter.tell(DocumentManager.WRITE_DOC, testKit.getRef());

    // Then
    testKit.expectNoMessage(maxBackOff);
    assertThat(count.get()).isEqualTo(6);
  }
}
