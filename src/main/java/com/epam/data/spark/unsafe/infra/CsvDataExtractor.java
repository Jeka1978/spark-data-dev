package com.epam.data.spark.unsafe.infra;

import com.epam.conference.sparkdatauser.Criminal;
import org.apache.spark.sql.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
@Component("csv")
public class CsvDataExtractor implements DataExtractor {
    @Override
    public Dataset<Row> readFrom(String path,Class<?> modelClass, ConfigurableApplicationContext context) {

//        Encoder<?> encoder = Encoders.bean(modelClass);
        return context.getBean(SparkSession.class).read().format("csv")
                .option("header", true).option("inferSchema", true).option("allow",true).load(path);
    }
}
