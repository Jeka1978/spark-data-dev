package com.epam.data.spark.unsafe.infra;

import org.apache.spark.sql.SparkSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//@SpringBootApplication
public class SparkDataUserApplication {

//
//    @Bean
//    public SparkSession sparkSession() {
//        return SparkSession.builder().appName("data").master("local[*]").getOrCreate();
//    }
//
//    @Bean
//    public JavaSparkContext sc(SparkSession sparkSession) {
//        return new JavaSparkContext(sparkSession.sparkContext());
//    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SparkDataUserApplication.class, args);
        SparkSession sparkSession = context.getBean(SparkSession.class);
//        sparkSession.read().json("data/profiles.json").show();
    }

}
