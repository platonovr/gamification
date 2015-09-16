package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.AcademicGroup;

/**
 * Created by timur on 24.06.15.
 */
public interface AcademicGroupDao {

    AcademicGroup findByGroupNumber(String number);

}
