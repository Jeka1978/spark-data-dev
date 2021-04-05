package com.epam.data.spark.unsafe.infra;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.beans.Introspector;
import java.lang.reflect.Proxy;

/**
 * @author Evgeny Borisov
 */
public class SparkRepositoriesRegistrarApplicationListener implements ApplicationContextInitializer {


    @Override
    public void initialize(ConfigurableApplicationContext context) {

        AnnotationConfigApplicationContext solidContext = new AnnotationConfigApplicationContext(Conf.class);
        SparkInvocationHandlerFactory invocationHandlerFactory = solidContext.getBean(SparkInvocationHandlerFactory.class);
        DataExtractorResolver dataExtractorResolver = solidContext.getBean(DataExtractorResolver.class);
        invocationHandlerFactory.setRealContext(context);
        ConfigurableListableBeanFactory beanFactory = ((GenericWebApplicationContext) context).getBeanFactory();

        solidContext.close();
        String appName = context.getEnvironment().getProperty("spark.appName");
        SparkSession sparkSession = SparkSession.builder().appName(appName).master("local[*]").getOrCreate();

        beanFactory.registerSingleton("sparkSession", sparkSession);

        JavaSparkContext sc = new JavaSparkContext(sparkSession.sparkContext());

        beanFactory.registerSingleton("sc", sc);

        beanFactory.registerSingleton("dataExtractorResolver", dataExtractorResolver);


        Reflections scanner = new Reflections("com.epam");

        scanner.getSubTypesOf(SparkRepository.class).forEach(sparkDataInterface -> {
            SparkInvocationHandler invocationHandler = invocationHandlerFactory.create(sparkDataInterface);
            Object sparkRepoBean = Proxy.newProxyInstance(sparkDataInterface.getClassLoader(), new Class[]{sparkDataInterface}, invocationHandler);
            beanFactory.registerSingleton(Introspector.decapitalize(sparkDataInterface.getSimpleName()), sparkRepoBean);
        });

    }
}
