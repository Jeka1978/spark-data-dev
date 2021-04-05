package com.epam.data.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
public interface FirstLevelCacheService {
    <T> List<T> getListFor(String pathToSource, String foreignKeyName, long id, Class<T> modelClass);
}
