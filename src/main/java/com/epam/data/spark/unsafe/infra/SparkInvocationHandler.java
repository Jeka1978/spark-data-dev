package com.epam.data.spark.unsafe.infra;

import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationHandler;

/**
 * @author Evgeny Borisov
 */
public interface SparkInvocationHandler extends InvocationHandler {
    void init(Class<?> modelClass, GenericApplicationContext context);
}
