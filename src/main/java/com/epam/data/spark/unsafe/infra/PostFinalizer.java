package com.epam.data.spark.unsafe.infra;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author Evgeny Borisov
 */
public interface PostFinalizer {
    Object postFinalize(Object returningValue, ConfigurableApplicationContext context);
}
