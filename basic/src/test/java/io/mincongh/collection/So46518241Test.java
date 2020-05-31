package io.mincongh.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class So46518241Test {

  private List<String> storedUrlList;

  private List<UrlClass> urlClasses;

  @BeforeEach
  void setUp() throws Exception {
    storedUrlList = Arrays.asList("A", "B", "E");

    urlClasses = new ArrayList<>();
    urlClasses.add(new UrlClass("A"));
    urlClasses.add(new UrlClass("B"));
    urlClasses.add(new UrlClass("C"));
    urlClasses.add(new UrlClass("D"));
    urlClasses.add(new UrlClass("E"));
  }

  @Test
  void removeMatched_java7() throws Exception {
    ListIterator<UrlClass> iterator = urlClasses.listIterator();
    while (iterator.hasNext()) {
      UrlClass u = iterator.next();
      if (storedUrlList.contains(u.getUrl())) {
        iterator.remove();
      }
    }
    assertThat(urlClasses).containsExactly(new UrlClass("C"), new UrlClass("D"));
  }

  @Test
  void removeMatched_java8() throws Exception {
    urlClasses.removeIf(u -> storedUrlList.contains(u.getUrl()));
    assertThat(urlClasses).containsExactly(new UrlClass("C"), new UrlClass("D"));
  }

  private static class UrlClass {

    public String getUrl() {
      return url;
    }

    private String url;

    UrlClass(String url) {
      this.url = url;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof UrlClass)) {
        return false;
      }

      UrlClass urlClass = (UrlClass) o;

      return url != null ? url.equals(urlClass.url) : urlClass.url == null;
    }

    @Override
    public int hashCode() {
      return url != null ? url.hashCode() : 0;
    }
  }
}
