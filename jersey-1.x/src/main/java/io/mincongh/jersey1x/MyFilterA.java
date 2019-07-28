package io.mincongh.jersey1x;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MyFilterA implements ContainerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(MyFilterA.class);

  @Override
  public ContainerRequest filter(ContainerRequest containerRequest) {
    logger.info("Filter A called");
    return containerRequest;
  }
}
