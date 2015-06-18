package ru.kpfu.itis.service;

import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.model.Task;

/**
 * Created by timur on 17.06.15.
 */
public interface TaskService {

    Task save(TaskDTO taskDTO);
}
