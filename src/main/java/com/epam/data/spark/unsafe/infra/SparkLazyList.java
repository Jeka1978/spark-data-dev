package com.epam.data.spark.unsafe.infra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeny Borisov
 */

@Getter
@Setter
public class SparkLazyList implements SparkLazyCollection {
    @Delegate
    private List content;
    private long modelId;
    private Class<?> modelClass;
    private String foreignKeyName;
    private String pathToSource;


    @Override
    public boolean initialized() {
        return content != null && !content.isEmpty();
    }
}
