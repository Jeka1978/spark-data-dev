package com.epam.data.spark.unsafe.infra;

import scala.Tuple2;

import java.util.List;
import java.util.Set;

/**
 * @author Evgeny Borisov
 */
public interface SparkTransformationSpider {
    Tuple2<SparkTransformation,List<String>> createSparkTransformation(List<String> methodWords, Set<String> fieldNames);
}
