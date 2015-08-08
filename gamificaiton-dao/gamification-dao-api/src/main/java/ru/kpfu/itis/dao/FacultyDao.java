package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Faculty;

/**
 * Created by Rigen on 20.07.15.
 */
public interface FacultyDao {

    Faculty findByName(String name);
}
