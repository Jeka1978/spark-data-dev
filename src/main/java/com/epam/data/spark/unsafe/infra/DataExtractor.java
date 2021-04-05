package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Evgeny Borisov
 */
public interface DataExtractor {
    Dataset<Row> readFrom(String path,Class<?> modelClass, ConfigurableApplicationContext context);
}
