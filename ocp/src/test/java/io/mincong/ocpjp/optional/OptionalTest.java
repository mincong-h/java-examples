package io.mincong.ocpjp.optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class OptionalTest {

  @Test
  public void empty() throws Exception {
    Optional<Player> player = Optional.empty();
    assertThat(player).isEmpty();
  }

  @Test
  public void filter() throws Exception {
    Optional<Player> player = Optional.of(new Player("Cool"));
    Predicate<Player> isNameLong = p -> p.getName().length() > 10;
    assertThat(player.filter(isNameLong)).isEmpty();
  }

  @Test
  public void map() throws Exception {
    Optional<Player> player = Optional.empty();
    Optional<String> name = player.map(Player::getName);
    assertThat(name).isEmpty();
  }

  @Test
  public void flatMap() throws Exception {
    Optional<Player> player = Optional.of(new Player(null));
    Function<Player, Optional<String>> getNameOptionally = p -> Optional.ofNullable(p.getName());
    assertThat(player.flatMap(getNameOptionally)).isEmpty();
  }

  @Test
  public void ifPresent() throws Exception {
    Optional<Player> player = Optional.empty();
    List<Player> list = new ArrayList<>();
    player.ifPresent(list::add);
    assertThat(list).isEmpty();
  }

  @Test
  public void isPresent() throws Exception {
    Optional<Player> player = Optional.empty();
    assertThat(player.isPresent()).isFalse();
  }

  @Test
  public void orElseGet() throws Exception {
    Optional<Player> player = Optional.empty();
    assertThat(player.orElseGet(Player::new)).isEqualTo(new Player());
  }

  @Test(expected = Exception.class)
  public void orElseThrow() throws Exception {
    Optional<Player> player = Optional.empty();
    player.orElseThrow(Exception::new);
  }

  private static class Player {

    private String name;

    Player() {

    }

    Player(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Player)) {
        return false;
      }

      Player player = (Player) o;

      return name != null ? name.equals(player.name) : player.name == null;
    }

    @Override
    public int hashCode() {
      return name != null ? name.hashCode() : 0;
    }

  }

}
