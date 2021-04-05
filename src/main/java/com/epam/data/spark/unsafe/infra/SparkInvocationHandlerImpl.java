package com.epam.data.spark.unsafe.infra;

import com.epam.conference.sparkdatauser.Criminal;
import lombok.Builder;
import org.apache.spark.sql.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import scala.Tuple2;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Evgeny Borisov
 */
@Builder
public class SparkInvocationHandlerImpl implements SparkInvocationHandler {
    private DataExtractor dataExtractor;
    private Map<Method, List<Tuple2<SparkTransformation, List<String>>>> method2Chain;
    private Map<Method, Finalizer> finalizerMap;
    private Class<?> modelClass;
    private ConfigurableApplicationContext context;
    private String pathToSource;
    private PostFinalizer postFinalizer;


    @Override
    public void init(Class<?> modelClass, GenericApplicationContext context) {
        this.context = context;
        pathToSource = modelClass.getAnnotation(Source.class).value();

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        Dataset<Row> dataset = dataExtractor.readFrom(pathToSource, modelClass, context);

        OrderedBag<?> arguments = new OrderedBag<>(args);

        List<Tuple2<SparkTransformation, List<String>>> tupleChain = method2Chain.get(method);
        for (Tuple2<SparkTransformation, List<String>> tuple : tupleChain) {
            SparkTransformation sparkTransformation = tuple._1();
            List<String> usedFieldNames = tuple._2();
            dataset = sparkTransformation.transform(dataset, usedFieldNames, arguments);
        }
        Object o = finalizerMap.get(method).doAction(dataset, arguments, method.getReturnType(), modelClass);
        return postFinalizer.postFinalize(o,context);


    }

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder().master("local").appName("a").getOrCreate();
        /*   String s = "findByAmountGreaterThanAndAmountLessThanAndAmountEqualsOrderByAmount";
        String[] r = s.split("(?=\\p{Upper})");
        Arrays.stream(r).forEach(System.out::println);
        String[] strings = {"name"};

        session.read()
                .json("data/profiles.json")
                .orderBy("age", strings).show();*/

        Encoder<Criminal> encoder = Encoders.bean(Criminal.class);
        List<Criminal> criminals = session.read()
                .schema(encoder.schema()).option("header", true).csv("data/criminals.csv").as(encoder).collectAsList();
        criminals.forEach(System.out::println);

    }
}
