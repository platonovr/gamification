package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.model.Faculty;

/**
 * Created by Rigen on 20.07.15.
 */
public interface FacultyService {
    @Transactional
    Faculty findByName(String name);
}
