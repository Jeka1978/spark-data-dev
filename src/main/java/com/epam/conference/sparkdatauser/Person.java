package com.epam.conference.sparkdatauser;

import com.epam.data.spark.unsafe.infra.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Evgeny Borisov
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/profiles.json")
public class Person {
    private String name;
    private long age;


}
