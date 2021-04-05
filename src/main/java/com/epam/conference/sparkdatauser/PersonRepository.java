package com.epam.conference.sparkdatauser;

import com.epam.data.spark.unsafe.infra.SparkRepository;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
public interface PersonRepository extends SparkRepository<Person> {
    List<Person> findByAgeGreaterThan(int age);
    List<Person> findByAgeGreaterThanAndNameContains(int age,String suffix);
    List<Person> findByAgeGreaterThanAndNameEqualsIgnoreCaseSortByNameAndAge(int age, String suffix);
}
