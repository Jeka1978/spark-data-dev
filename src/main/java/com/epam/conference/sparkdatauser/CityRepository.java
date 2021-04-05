package com.epam.conference.sparkdatauser;

import com.epam.data.spark.unsafe.infra.SparkRepository;

import java.util.List;

/**
 * @author Evgeny Borisov
 */

public interface CityRepository extends SparkRepository<City> {
    List<City> findByNameContains(String part);
    List<City> findByMediaEqualsIgnoreCase(String part);

}
