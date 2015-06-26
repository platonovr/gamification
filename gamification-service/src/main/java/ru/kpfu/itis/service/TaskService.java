package ru.kpfu.itis.service;

import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskCategory;

import java.util.Collection;

/**
 * Created by timur on 17.06.15.
 */
public interface TaskService {

    Task save(TaskDTO taskDTO);

    Task findByName(String name);

    Collection<TaskCategory> getAllCategories();
}
