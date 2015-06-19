package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.service.TaskService;
import ru.kpfu.itis.util.Constant;

/**
 * Created by timur on 17.06.15.
 */
@RequestMapping(Constant.API_URI_PREFIX + "/tasks")
@RestController("apiTaskController")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
//        taskService.save(taskDTO);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

}
