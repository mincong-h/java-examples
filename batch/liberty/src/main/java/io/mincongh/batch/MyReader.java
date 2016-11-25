package io.mincongh.batch;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.batch.api.chunk.AbstractItemReader;

public class MyReader extends AbstractItemReader {

    private static final String NUMBERS = "0,1,2,3,4,5,6,7,8,9";
    private List<Integer> numbers;
    private Iterator<Integer> numberIterator;

    @Override
    public void open(Serializable checkpoint) {
        numbers = new LinkedList<>();
        for (String number : NUMBERS.split(",")) {
            numbers.add(Integer.valueOf(number));
        }
        numberIterator = numbers.iterator();
    }

    @Override
    public Object readItem() throws Exception {
        return numberIterator.hasNext() ? numberIterator.next() : null;
    }
}
