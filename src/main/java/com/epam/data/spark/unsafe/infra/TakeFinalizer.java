package com.epam.data.spark.unsafe.infra;

import lombok.SneakyThrows;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

/**
 * @author Evgeny Borisov
 */
@Component("take")
public class TakeFinalizer implements Finalizer {
    @SneakyThrows
    @Override
    public Object doAction(Dataset<Row> dataset, OrderedBag<?> args, Class<?> returnType, Class<?> modelClass) {

        return dataset.as(Encoders.bean(modelClass)).takeAsList((int) args.takeAndRemove());

    }
}
