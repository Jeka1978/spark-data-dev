package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;
import scala.math.Ordering;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
@Component
public class SortTransformation implements SparkTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, List<?> params) {
        return dataset.orderBy(fieldNames.get(0), fieldNames.stream().skip(1).toArray(String[]::new));
    }
}
