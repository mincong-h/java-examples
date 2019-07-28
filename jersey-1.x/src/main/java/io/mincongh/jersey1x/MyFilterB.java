package io.mincongh.jersey1x;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MyFilterB implements ContainerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(MyFilterB.class);

  @Override
  public ContainerRequest filter(ContainerRequest containerRequest) {
    logger.info("Filter B called");
    return containerRequest;
  }
}
