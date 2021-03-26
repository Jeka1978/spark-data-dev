package com.epam.data.spark.unsafe.infra;

import lombok.Builder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import scala.Tuple2;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Evgeny Borisov
 */
@Builder
public class SparkInvocationHandlerImpl implements SparkInvocationHandler {
    private Map<Method, List<Tuple2<SparkTransformation, List<String>>>> method2Chain;
    private Map<Method, Finalizer> finalizerMap;
    private Class<?> modelClass;
    private ConfigurableApplicationContext context;
    private String pathToSource;


    @Override
    public void init(Class<?> modelClass, GenericApplicationContext context) {
        this.context = context;
        pathToSource = modelClass.getAnnotation(Source.class).value();

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Dataset<Row> dataset = context.getBean(SparkSession.class).read().json(pathToSource);

        List<Object> arguments = Arrays.stream(args).collect(Collectors.toList());

        List<Tuple2<SparkTransformation, List<String>>> tupleChain = method2Chain.get(method);
        for (Tuple2<SparkTransformation, List<String>> tuple : tupleChain) {
            SparkTransformation sparkTransformation = tuple._1();
            List<String> usedFieldNames = tuple._2();
            dataset = sparkTransformation.transform(dataset, usedFieldNames, arguments);
        }
        return finalizerMap.get(method).doAction(dataset, arguments, method.getReturnType(),modelClass);


    }

    public static void main(String[] args) {
        String s = "findByAmountGreaterThanAndAmountLessThanAndAmountEqualsOrderByAmount";
        String[] r = s.split("(?=\\p{Upper})");
        Arrays.stream(r).forEach(System.out::println);

        String[] strings = {"name"};

        SparkSession.builder().master("local").appName("a").getOrCreate().read()
                .json("data/profiles.json")
                .orderBy("age", strings).show();

    }
}
