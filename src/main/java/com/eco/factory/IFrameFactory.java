package com.partner.factory;

/**
 * @author Albert Gomes Cabral
 */
public interface IFrameFactory <T> {
    T create(T templateFactory) throws Exception;
}
