package io.mincongh.mockito.init;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArgumentMatchTest {
  static class UserRegistry {
    private final Map<String, String> users;

    public UserRegistry(Map<String, String> users) {
      this.users = users;
    }

    Optional<String> getUser(String userId) {
      return Optional.ofNullable(users.get(userId));
    }
  }

  @Test
  void getUser() {
    var registry = mock(UserRegistry.class);
    when(registry.getUser("user1")).thenReturn(Optional.of("User One"));
    when(registry.getUser("user2")).thenReturn(Optional.of("User Two"));
    when(registry.getUser("user3")).thenReturn(Optional.empty());

    assertThat(registry.getUser("user1")).hasValue("User One");
    assertThat(registry.getUser("user2")).hasValue("User Two");
    assertThat(registry.getUser("user3")).isEmpty();
  }
}
