package com.epam.data.spark.unsafe.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Evgeny Borisov
 */
@Component("findBy")
public class FilterTransformationSpider implements SparkTransformationSpider {

    @Autowired
    private Map<String,SparkFilterTransformation> filterTransformations;


    @Override
    public Tuple2<SparkTransformation,List<String>> createSparkTransformation(List<String> methodWords, Set<String> fieldNames) {
        List<String> parameterNames = List.of(WordsMatcher.findAndRemoveMatchingPieces(fieldNames, methodWords));
        String filterName = WordsMatcher.findAndRemoveMatchingPieces(filterTransformations.keySet(), methodWords);
        SparkTransformation sparkTransformation = filterTransformations.get(filterName);
        return new Tuple2<>(sparkTransformation, parameterNames);
    }
}
