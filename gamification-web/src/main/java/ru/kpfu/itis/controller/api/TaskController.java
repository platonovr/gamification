package ru.kpfu.itis.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.util.Constant;

/**
 * Created by timur on 17.06.15.
 */
@RequestMapping(Constant.API_URI_PREFIX + "/tasks")
@RestController("apiTaskController")
public class TaskController {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createTask(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity(HttpStatus.OK);
    }

}
