package io.mincongh.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

import io.mincongh.entity.Rectangle;

@Named
public class RectangleProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        int id = (int) item;
        int width = id;
        int length = id * 2;
        return new Rectangle(id, width * length);
    }
}
