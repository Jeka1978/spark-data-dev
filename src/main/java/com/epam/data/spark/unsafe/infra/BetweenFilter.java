package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
@Component("between")
public class BetweenFilter implements SparkFilterTransformation {

    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, OrderedBag<?> params) {
        return dataset.filter(functions.col(fieldNames.get(0)).between(params.takeAndRemove(),params.takeAndRemove()));
    }

}
