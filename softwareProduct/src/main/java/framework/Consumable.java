package framework;

import exception.FileAccessException;

/**
 * The Consumable interface should be implemented by any classes that will have to receive data
 * and write it somewhere. The class must define a method with one argument called write.
 * <p></p>
 * The Interface is designed to provide a common protocol for instances that are designed to be consumers.
 * <p></p>
 * @param <D> The Object that has to be consumed.
 */
public interface Consumable<D> {

    /**
     * Takes data and writes it to the specified location.
     * <p></p>
     * @param data the data that has to be transferred.
     * @throws FileAccessException as filepath could be incorrect.
     */
    void write(D data) throws FileAccessException;
}
