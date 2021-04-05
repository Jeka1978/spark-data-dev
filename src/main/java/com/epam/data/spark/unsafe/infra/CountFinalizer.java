package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
@Component("count")
public class CountFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset, OrderedBag<?> args, Class<?> returnType,Class<?> modelClass) {
        return dataset.count();
    }

}
