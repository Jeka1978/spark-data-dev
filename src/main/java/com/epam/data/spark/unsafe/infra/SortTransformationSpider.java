package com.epam.data.spark.unsafe.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Evgeny Borisov
 */
@Component("sortBy")
public class SortTransformationSpider implements SparkTransformationSpider {
    @Autowired
    private SortTransformation sortTransformation;

    @Override
    public Tuple2<SparkTransformation,List<String>> createSparkTransformation(List<String> methodWords, Set<String> fieldNames) {
        String fieldName = WordsMatcher.findAndRemoveMatchingPieces(fieldNames, methodWords);
        List<String> additionalFields = new ArrayList<>();
        while (!methodWords.isEmpty() && methodWords.get(0).equalsIgnoreCase("and")) {
            methodWords.remove(0);
            additionalFields.add(WordsMatcher.findAndRemoveMatchingPieces(fieldNames, methodWords));
        }
        additionalFields.add(0,fieldName);
        return new Tuple2<>(sortTransformation,additionalFields);
    }
}
