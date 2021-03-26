package com.epam.data.spark.unsafe.infra;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Evgeny Borisov
 */
@Component
@RequiredArgsConstructor
public class SparkInvocationHandlerFactory {

    @Setter
    private ConfigurableApplicationContext realContext;

    private final Map<String, Finalizer> name2Finalizer;

    private final Map<String, SparkTransformationSpider> spidersMap;

    private SparkTransformationSpider currentSpider;



    public SparkInvocationHandler create(Class<? extends SparkRepository> sparkDataInterface) {
        Class<?> modelClass = getModelClass(sparkDataInterface);
//        realContext.getEnvironment().getProperty("data.location")+"/"+
        String pathToSource = getPathToSource(modelClass);
        Map<Method, Finalizer> finalizerMap = new HashMap<>();
        Map<Method, List<Tuple2<SparkTransformation, List<String>>>> method2TransformationChain = new HashMap<>();

        Set<String> fieldNames = getModelFieldNames(modelClass);
        System.out.println("fieldNames = " + fieldNames);


        Method[] methods = sparkDataInterface.getMethods();
        for (Method method : methods) {
            List<Tuple2<SparkTransformation, List<String>>> transformationChain = new ArrayList<>();
            String methodName = method.getName();
            List<String> methodWords = Arrays.stream(methodName.split("(?=\\p{Upper})")).collect(Collectors.toList());
            while (methodWords.size() > 1) {
                String strategyName = WordsMatcher.findAndRemoveMatchingPieces(spidersMap.keySet(), methodWords);
                if (!strategyName.isEmpty()) {
                    currentSpider = spidersMap.get(strategyName);
                }
                transformationChain.add(currentSpider.createSparkTransformation(methodWords, fieldNames));
            }
            method2TransformationChain.put(method, transformationChain);

            String finalizerName = "collect";
            if (!methodWords.isEmpty()) {
                finalizerName = methodWords.get(0);
            }
            finalizerMap.put(method, name2Finalizer.get(finalizerName));

        }
        return SparkInvocationHandlerImpl.builder()
                .context(realContext)
                .modelClass(modelClass)
                .pathToSource(pathToSource)
                .method2Chain(method2TransformationChain)
                .finalizerMap(finalizerMap)
                .build();
    }

    private Set<String> getModelFieldNames(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private String getPathToSource(Class<?> modelClass) {
        Source annotation = modelClass.getAnnotation(Source.class);
        return annotation.value();
    }

    private Class<?> getModelClass(Class<? extends SparkRepository> sparkDataInterface) {
        Class<?> modelClass = (Class<?>) ((ParameterizedType) sparkDataInterface.getGenericInterfaces()[0]).getActualTypeArguments()[0];
        return modelClass;
    }

}
