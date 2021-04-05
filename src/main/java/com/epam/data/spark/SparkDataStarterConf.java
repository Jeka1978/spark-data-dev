package com.epam.data.spark;

import com.epam.data.spark.unsafe.infra.SparkLazyList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author Evgeny Borisov
 */
@Configuration
public class SparkDataStarterConf {
    @Bean
    public FirstLevelCacheService firstLevelCacheService(){
        return new FirstLevelCacheServiceImpl();
    }

    @Bean
    public LazyCollectionAspect lazyCollectionAspect(){
        return new LazyCollectionAspect();
    }

    @Bean
    @Scope("prototype")
    public SparkLazyList sparkLazyList(){
        return new SparkLazyList();
    }


}
