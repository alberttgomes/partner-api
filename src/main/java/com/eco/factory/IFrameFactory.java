package com.eco.factory;

/**
 * @author Albert Gomes Cabral
 */
public interface IFrameFactory <T> {
    public T create() throws Exception;
}
