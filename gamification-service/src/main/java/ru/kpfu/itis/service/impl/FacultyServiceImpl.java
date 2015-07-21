package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.FacultyDao;
import ru.kpfu.itis.model.Faculty;
import ru.kpfu.itis.service.FacultyService;

/**
 * Created by Rigen on 20.07.15.
 */
@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    FacultyDao facultyDao;

    @Override
    @Transactional
    public Faculty findByName(String name) {
        return facultyDao.findByName(name);
    }
}
