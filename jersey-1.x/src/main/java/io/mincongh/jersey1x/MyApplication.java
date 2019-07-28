package io.mincongh.jersey1x;

import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MyApplication extends DefaultResourceConfig {

  public MyApplication() {
    String[] containerRequestFilters =
        newContainerRequestFilters() //
            .stream()
            .map(Class::getName)
            .toArray(String[]::new);
    super.getProperties().put(PROPERTY_CONTAINER_REQUEST_FILTERS, containerRequestFilters);
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> set = new HashSet<>();
    set.add(MyResource.class);
    return set;
  }

  @Override
  public Set<Object> getSingletons() {
    return new HashSet<>();
  }

  private static Set<Class<? extends ContainerRequestFilter>> newContainerRequestFilters() {
    Set<Class<? extends ContainerRequestFilter>> set = new HashSet<>();
    set.add(MyFilterA.class);
    set.add(MyFilterB.class);
    return set;
  }
}
