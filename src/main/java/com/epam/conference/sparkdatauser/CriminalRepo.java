package com.epam.conference.sparkdatauser;

import com.epam.data.spark.unsafe.infra.SparkRepository;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
public interface CriminalRepo extends SparkRepository<Criminal> {
    List<Criminal> findByNumberGreaterThan(int number);
}
