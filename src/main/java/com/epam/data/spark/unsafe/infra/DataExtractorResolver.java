package com.epam.data.spark.unsafe.infra;

/**
 * @author Evgeny Borisov
 */
public interface DataExtractorResolver {
    DataExtractor resolve(String pathToSource);
}
