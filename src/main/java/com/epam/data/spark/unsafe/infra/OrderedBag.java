package com.epam.data.spark.unsafe.infra;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evgeny Borisov
 */
public class OrderedBag<T> {
    private List<T> items;

    public OrderedBag(T[] items) {
        this.items = Arrays.stream(items).collect(Collectors.toList());
    }


    public T takeAndRemove() {
        return items.remove(0);
    }
    public int size(){
        return items.size();
    }
}
