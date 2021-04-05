package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.spark.sql.functions.*;

/**
 * @author Evgeny Borisov
 */
@Component("equalsIgnoreCase")
public class EqualsIgnoreCaseFilter implements SparkFilterTransformation {


    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, OrderedBag<?> params) {
        return dataset.filter(lower(col(fieldNames.get(0))).equalTo(((String) params.takeAndRemove()).toLowerCase()));
    }


}
