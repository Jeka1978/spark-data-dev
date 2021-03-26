package com.epam.data.spark.unsafe.infra;

import lombok.SneakyThrows;
import org.apache.spark.sql.Row;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author Evgeny Borisov
 */
public class RowToModelConverter {
    @SneakyThrows
    public static <M> M convert(Row row, Class<M> modelClass) {
        M model = modelClass.getDeclaredConstructor().newInstance();
        Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .forEach(field -> setField(field,row,model));
        return model;

    }

    @SneakyThrows
    private static void setField(Field field, Row row, Object model) {
        field.setAccessible(true);
        field.set(model,row.getAs(field.getName()));
    }
}
