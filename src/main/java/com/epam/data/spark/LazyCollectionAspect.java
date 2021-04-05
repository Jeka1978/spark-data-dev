package com.epam.data.spark;

import com.epam.data.spark.unsafe.infra.SparkLazyCollection;
import com.epam.data.spark.unsafe.infra.SparkLazyList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
@Aspect
public class LazyCollectionAspect {

    @Autowired
    private FirstLevelCacheService cacheService;

    @Before("execution(* com.epam.data.spark.unsafe.infra.SparkLazyList.*(..))")
    public void beforeEachMethodCheckAndFillContentIfEmpty(JoinPoint jp) {
        SparkLazyList lazyList = (SparkLazyList) jp.getTarget();
        if (lazyList.getPathToSource() == null) {
            return;
        }
        if (!lazyList.initialized()) {
            List<?> list = cacheService.
                    getListFor(lazyList.getPathToSource(), lazyList.getForeignKeyName(), lazyList.getModelId(), lazyList.getModelClass());
            lazyList.setContent(list);
        }
    }
}
