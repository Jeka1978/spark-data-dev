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
@Source("data/orders.csv")
public class Order {
    private String name;
    private String desc;
    private String price;
    private long criminalId;
}
