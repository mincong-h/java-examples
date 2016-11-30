package io.mincongh.batch;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

@Named
public class MyWriter extends AbstractItemWriter {

    @Override
    public void writeItems(List<Object> items) throws Exception {
        for (Object item : items) {
            System.out.println((int) item);
        }
    }
}
