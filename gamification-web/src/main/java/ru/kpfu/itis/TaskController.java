package ru.kpfu.itis;

import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.TaskDto;

import java.util.Set;

/**
 * Created by Rigen on 17.06.15.
 */

@RestController
public class TaskController {

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    @ResponseBody
    public Set<TaskDto> getAllTasks(@RequestParam String filter) {


        return null;
    }

}
