package com.epam.data.spark.unsafe.infra;

/**
 * @author Evgeny Borisov
 */
public class UnsupportedCollectionException extends RuntimeException {
    public UnsupportedCollectionException(String message) {
        super(message);
    }
}
