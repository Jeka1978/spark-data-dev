package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
public interface SparkTransformation {
    Dataset<Row> transform(Dataset<Row> dataset,List<String> fieldNames, OrderedBag<?> params);


}
