package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
@Component("contains")
@FilterTransformation
public class ContainsFilter implements SparkTransformation {


    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, List<?> params) {
        System.out.println("**************123(***********");
        System.out.println(fieldNames.get(0));
        System.out.println(params.get(0));
        return dataset.filter(functions.col(fieldNames.get(0)).contains(params.get(0)));
    }


}
