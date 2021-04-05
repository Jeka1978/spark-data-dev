package com.epam.conference.sparkdatauser;

import com.epam.data.spark.unsafe.infra.SparkRepository;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
public interface OrderRepo extends SparkRepository<Order> {
    List<Order> findByPriceBetween(int min, int max);
}
