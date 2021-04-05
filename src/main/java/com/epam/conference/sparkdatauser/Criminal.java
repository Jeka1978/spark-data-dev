package com.epam.conference.sparkdatauser;

import com.epam.data.spark.unsafe.infra.ForeignKey;
import com.epam.data.spark.unsafe.infra.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/criminals.csv")
public class Criminal {

    private long id;
    private String name;
    private long number;
    @ForeignKey("criminalId")
    private List<Order> orders;
}
