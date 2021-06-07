package io.mincong.junit5.parameterized_test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class ArgumentsProviderTest {

  @ParameterizedTest
  @ArgumentsSource(UserProvider.class)
  void testFullName(User user) {
    assertThat(user.fullName).isEqualTo(user.firstName + " " + user.lastName);
  }

  public static class UserProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      var users =
          new User[] {
            new User("Sansa", "Stark"), //
            new User("Arya", "Stark")
          };
      return Stream.of(users).map(Arguments::of);
    }
  }

  private static class User {
    final String firstName;
    final String lastName;
    final String fullName;

    User(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.fullName = firstName + " " + lastName;
    }
  }
}
