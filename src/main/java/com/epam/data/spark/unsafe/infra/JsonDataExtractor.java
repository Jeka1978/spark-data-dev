package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Evgeny Borisov
 */
@Component("json")
public class JsonDataExtractor implements DataExtractor {
    @Override
    public Dataset<Row> readFrom(String path,Class<?> modelClass, ConfigurableApplicationContext context) {
        return context.getBean(SparkSession.class).read().json(path);
    }
}
