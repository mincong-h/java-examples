package io.mincongh.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class MyProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        return (int) item * (int) item;
    }
}
