package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
public interface Finalizer {
    Object doAction(Dataset<Row> dataset, OrderedBag<?> args, Class<?> returnType,Class<?> modelClass);
}
