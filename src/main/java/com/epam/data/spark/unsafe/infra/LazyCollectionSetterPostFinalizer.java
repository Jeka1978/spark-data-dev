package com.epam.data.spark.unsafe.infra;

import lombok.SneakyThrows;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * @author Evgeny Borisov
 */
public class LazyCollectionSetterPostFinalizer implements PostFinalizer {
    @SneakyThrows
    @Override
    public Object postFinalize(Object returningValue, ConfigurableApplicationContext context) {
        if (Collection.class.isAssignableFrom(returningValue.getClass())) {
            for (Object model : (List<?>) returningValue) {
                Class<?> modelClass = model.getClass();
                Field idField = modelClass.getDeclaredField("id");
                idField.setAccessible(true);
                long objectId = idField.getLong(model);
                Field[] fields = modelClass.getDeclaredFields();
                for (Field field : fields) {
                    if (List.class.isAssignableFrom(field.getType())) {
                        String columnName = field.getAnnotation(ForeignKey.class).value();
                        Class<?> embeddedModel = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                        SparkLazyCollection sparkLazyList = context.getBean(SparkLazyCollection.class);
                        sparkLazyList.setModelId(objectId);
                        sparkLazyList.setForeignKeyName(columnName);
                        sparkLazyList.setModelClass(embeddedModel);
                        sparkLazyList.setPathToSource(embeddedModel.getAnnotation(Source.class).value());
                        field.setAccessible(true);
                        field.set(model, sparkLazyList);
                    }
                }
            }
        }

        return returningValue;

    }

}
