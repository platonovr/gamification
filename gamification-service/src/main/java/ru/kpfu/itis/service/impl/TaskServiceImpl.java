package ru.kpfu.itis.service.impl;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.service.TaskService;

/**
 * Created by timur on 17.06.15.
 */
@Service
public class TaskServiceImpl implements TaskService {

    //    @RolesAllowed({"ADMIN", "TEACHER"})
    @Override
    public Task save(TaskDTO taskDTO) {
        return new Task();
    }
}
