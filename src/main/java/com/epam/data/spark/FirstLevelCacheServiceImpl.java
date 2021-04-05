package com.epam.data.spark;

import com.epam.data.spark.unsafe.infra.DataExtractor;
import com.epam.data.spark.unsafe.infra.DataExtractorResolver;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.spark.sql.functions.col;

/**
 * @author Evgeny Borisov
 */
public class FirstLevelCacheServiceImpl implements FirstLevelCacheService {
    @Autowired
    private GenericApplicationContext context;
    @Autowired
    private DataExtractorResolver extractorResolver;

    private Map<Class<?>, Dataset<Row>> modelClass2Dataset = new HashMap<>();

    @Override
    public <T> List<T> getListFor(String pathToSource, String foreignKeyName, long id, Class<T> modelClass) {

        System.out.println("888888888888888888");
        if (!modelClass2Dataset.containsKey(modelClass)) {
            System.out.println("зззззззззззззз");
            DataExtractor dataExtractor = extractorResolver.resolve(pathToSource);
            Dataset<Row> dataset = dataExtractor.readFrom(pathToSource, modelClass, context).persist();
            modelClass2Dataset.put(modelClass, dataset);
        }
        return modelClass2Dataset.get(modelClass).filter(col(foreignKeyName).equalTo(id)).as(Encoders.bean(modelClass)).collectAsList();
    }
}











