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
@Source("data/city.csv")
public class City {
    private String name;
    private String media;

}
