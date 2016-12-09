package io.mincongh.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

import io.mincongh.entity.Rectangle;

@Named
public class RectangleProcessor implements ItemProcessor {

  @Override
  public Object processItem(Object item) throws Exception {
    int id = (int) item;
    return new Rectangle(id, RectangleProcessor.processSurface(id));
  }

  public static int processSurface(int id) {
    if (id < 1) {
      throw new IllegalStateException("id must be greater than 0.");
    }
    int width = id;
    int length = id * 2;
    int surface = width * length;
    return surface;
  }
}
