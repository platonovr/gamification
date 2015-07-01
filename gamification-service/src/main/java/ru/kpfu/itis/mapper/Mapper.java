package ru.kpfu.itis.mapper;

/**
 * Created by timur on 30.06.15.
 */
public interface Mapper<A,B> {

    A fromDto(B dto);

    B toDto(A object);
}
