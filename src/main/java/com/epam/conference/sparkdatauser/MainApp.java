package com.epam.conference.sparkdatauser;

import com.epam.data.spark.unsafe.infra.SparkLazyCollection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
       /* SparkSession session = SparkSession.builder().appName("data").master("local[*]").getOrCreate();
        Encoder<?> encoder = Encoders.bean(Hero.class);
        session.read()
                .schema(encoder.schema()).option("header", true).csv("C:\\ariel\\heroes.csv").show();
*/
        ConfigurableApplicationContext context = SpringApplication.run(MainApp.class);

        List<Criminal> list = context.getBean(CriminalRepo.class).findByNumberGreaterThan(15);

        List<Order> orders = list.get(1).getOrders();
        System.out.println("*********************");
        System.out.println(orders.size());
        System.out.println("*********************");
        SparkLazyCollection collection = (SparkLazyCollection) orders;
        System.out.println(collection.getForeignKeyName());
        System.out.println();
    }
}
