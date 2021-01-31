package io.mincong.junit5;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class ChatBotTest {
  /**
   * Use annotation {@link ParameterizedTest} to test multiple implementation of {@link ChatBot}. It
   * ensures that all implementations respect the specification of the interface and returns the
   * expected results regardless the internal implementation.
   *
   * @param bot the chat bot to test
   */
  @ParameterizedTest
  @ArgumentsSource(ChatBotProvider.class)
  void sayHello(ChatBot bot) {
    assertThat(bot.sayHello("Foo")).isEqualTo("Hello, Foo");
    assertThat(bot.sayHello("Bar")).isEqualTo("Hello, Bar");
  }

  public static class ChatBotProvider implements ArgumentsProvider {

    /**
     * This method creates multiple chat bot instances using different implementations and returns
     * them as a stream of arguments for the parameterized test.
     */
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(new StringFormatChatBot(), new StringConcatenationChatBot())
          .map(Arguments::of);
    }
  }
}
