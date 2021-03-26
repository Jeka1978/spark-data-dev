package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author Evgeny Borisov
 */
@Component("collect")
public class CollectFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset, List<Object> args, Class<?> returnType,Class<?> modelClass) {
        List<Row> rows = dataset.collectAsList();
        Stream<?> stream = rows.stream()
                .map(row -> RowToModelConverter.convert(row, modelClass));
        if (returnType == Set.class) {
            return stream.collect(toSet());
        }
        if (returnType == List.class) {
            return stream.collect(toList());
        }
        throw new UnsupportedCollectionException(returnType + " is not supported collections");
    }
}
