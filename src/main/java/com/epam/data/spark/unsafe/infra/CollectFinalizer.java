package com.epam.data.spark.unsafe.infra;

import lombok.SneakyThrows;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evgeny Borisov
 */
@Component("collect")
public class CollectFinalizer implements Finalizer {
    @SneakyThrows
    @Override
    public Object doAction(Dataset<Row> dataset, OrderedBag<?> args, Class<?> returnType, Class<?> modelClass) {

        Encoder<?> encoder = Encoders.bean(modelClass);
        List<String> listFieldNames = Arrays.stream(encoder.schema().fields()).filter(structField -> structField.dataType() instanceof ArrayType)
                .map(StructField::name)
                .collect(Collectors.toList());
        for (String fieldName : listFieldNames) {

            ParameterizedType genericType = (ParameterizedType) modelClass.getDeclaredField(fieldName).getGenericType();
            Class c = (Class) genericType.getActualTypeArguments()[0];
            dataset = dataset.withColumn(fieldName, functions.lit(null).cast(DataTypes.createArrayType(DataTypes.createStructType(Encoders.bean(c).schema().fields()))));
        }


        return dataset.as(encoder).collectAsList();
       /* List<Row> rows = dataset.collectAsList();
        Stream<?> stream = rows.stream()
                .map(row -> RowToModelConverter.convert(row, modelClass));
        if (returnType == Set.class) {
            return stream.collect(toSet());
        }
        if (returnType == List.class) {
            return stream.collect(toList());
        }
        throw new UnsupportedCollectionException(returnType + " is not supported collections");*/
    }
}
