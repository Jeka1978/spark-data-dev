package com.epam.data.spark.unsafe.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Evgeny Borisov
 */
@Component
@RequiredArgsConstructor
public class DataExtractorResolverImpl implements DataExtractorResolver {
    private final Map<String, DataExtractor> extractorMap;

    @Override
    public DataExtractor resolve(String pathToSource) {
        return extractorMap.get(pathToSource.split("\\.")[1]);
    }
}
