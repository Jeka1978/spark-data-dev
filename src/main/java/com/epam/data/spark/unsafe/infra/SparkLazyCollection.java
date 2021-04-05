package com.epam.data.spark.unsafe.infra;

import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeny Borisov
 */
public interface SparkLazyCollection extends List{
    long getModelId();
    String getPathToSource();
    String getForeignKeyName();
    Class<?> getModelClass();
    void setModelClass(Class<?> modelClass);
    void setForeignKeyName(String foreignKeyName);
    void setPathToSource(String pathToSource);
    void setModelId(long modelId);
    boolean initialized();

}