package io.mincong.ocpjp.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

/**
 * Java 8 in Action, Chapter 6: Collecting data with streams
 * <ul>
 * <li>{@code Stream#collect} is a terminal operation that takes as
 * argument various recipes (called collectors) for accumulating the
 * elements of a stream into a summary result.
 * <li>Predefined collectors include reducing and summarizing stream
 * elements into a single value, such as calculating the minimum,
 * maximum, or average.
 * <li>Predefined collectors let you group elements of a stream with
 * {@code Collectors#groupingBy} and partition elements of a stream
 * with {@code Collectors#partitioningBy}.
 * <li>Collectors compose effectively to create multilevel groupings,
 * partitions, and reductions.
 * <li>You can develop your own collectors by implementing the
 * methods defined in the {@code Collector} interface.
 * </ul>
 *
 * @author Mincong Huang
 */
public class CollectDataTest {

  private List<User> users;

  @Before
  public void setUp() throws Exception {
    User a = new User("A", 10, Locale.US);
    User b = new User("B", 20, Locale.US);
    User c = new User("C", 30, Locale.UK);
    User d = new User("D", 40, Locale.FRANCE);
    User e = new User("E", 50, Locale.CHINA);

    users = Arrays.asList(a, b, c, d, e);
  }

  @Test
  public void partition() throws Exception {
    Map<Boolean, List<User>> results = users.stream()
        .collect(Collectors.partitioningBy(this::canSpeakEnglish));
    assertThat(results.get(true)).hasSize(3);
    assertThat(results.get(false)).hasSize(2);
  }

  @Test
  public void groupingBy() throws Exception {
    Map<String, List<User>> results = users.stream()
        .collect(Collectors.groupingBy(User::getLanguage));
    assertThat(results.get("en")).hasSize(3);
    assertThat(results.get("fr")).hasSize(1);
    assertThat(results.get("zh")).hasSize(1);
  }

  @Test
  public void subGroupingBy() throws Exception {
    Comparator<User> age = Comparator.comparing(User::getAge);
    Map<String, Optional<User>> minByAge = users.stream()
        .collect(Collectors.groupingBy(User::getLanguage, Collectors.minBy(age)));
    assertThat(minByAge.get("en").orElseThrow(Exception::new).name).isEqualTo("A");
    assertThat(minByAge.get("fr").orElseThrow(Exception::new).name).isEqualTo("D");
    assertThat(minByAge.get("zh").orElseThrow(Exception::new).name).isEqualTo("E");
  }

  @Test
  public void subGroupingBy_customizedCollector() throws Exception {
    Map<String, User> minByAge = users.stream().collect(new MinAgeByLanguage());
    assertThat(minByAge.get("en").name).isEqualTo("A");
    assertThat(minByAge.get("fr").name).isEqualTo("D");
    assertThat(minByAge.get("zh").name).isEqualTo("E");
  }

  /**
   * <ul>
   * <li>Param 'T' (User): the type of input elements to the
   * reduction operation
   * <li>Param 'A' (Map): the mutable accumulation type of the
   * reduction operation (often hidden as an implementation detail)
   * <li>Param 'R' (Map): the result type of the reduction operation
   * </ul>
   */
  private class MinAgeByLanguage implements Collector<User, Map<String, User>, Map<String, User>> {

    private Comparator<User> byAge = Comparator.comparing(User::getAge);

    @Override
    public Supplier<Map<String, User>> supplier() {
      return HashMap::new;
    }

    @Override
    public BiConsumer<Map<String, User>, User> accumulator() {
      return (users, u) -> {
        String k = u.getLanguage();
        if (!users.containsKey(k) || byAge.compare(u, users.get(k)) < 0) {
          users.put(k, u);
        }
      };
    }

    @Override
    public BinaryOperator<Map<String, User>> combiner() {
      return (m1, m2) -> {
        m2.forEach((k, v) -> {
          if (!m1.containsKey(k) || byAge.compare(v, m1.get(k)) < 0) {
            m1.put(k, v);
          }
        });
        return m1;
      };
    }

    @Override
    public Function<Map<String, User>, Map<String, User>> finisher() {
      return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
      return EnumSet.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH);
    }

  }

  @Test
  public void min() throws Exception {
    Optional<User> youngest = users.stream()
        .min(Comparator.comparing(User::getAge));
    assertThat(youngest.orElseThrow(Exception::new).name).isEqualTo("A");
  }

  @Test
  public void max() throws Exception {
    Optional<User> oldest = users.stream()
        .max(Comparator.comparing(User::getAge));
    assertThat(oldest.orElseThrow(Exception::new).name).isEqualTo("E");
  }

  @Test
  public void average() throws Exception {
    double avg = users.stream()
        .collect(Collectors.averagingInt(User::getAge));
    assertThat(avg).isEqualTo(30);
  }

  private boolean canSpeakEnglish(User u) {
    String english = Locale.ENGLISH.getLanguage();
    return english.equals(u.getLanguage());
  }

  private class User {

    private String name;

    private int age;

    private Locale locale;

    User(String name, int age, Locale locale) {
      this.name = name;
      this.age = age;
      this.locale = locale;
    }

    public String getName() {
      return name;
    }

    private int getAge() {
      return age;
    }

    private String getLanguage() {
      return locale.getLanguage();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof User)) {
        return false;
      }

      User user = (User) o;

      if (age != user.age) {
        return false;
      }
      return name.equals(user.name);
    }

    @Override
    public int hashCode() {
      int result = name.hashCode();
      result = 31 * result + age;
      return result;
    }

  }

}
