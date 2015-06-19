package ru.kpfu.itis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kpfu.itis.dto.TaskDTO;

/**
 * Created by timur on 17.06.15.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

    @ModelAttribute
    public TaskDTO taskDTO(){
        return new TaskDTO();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String taskCreationPage() {
        return "forward:/parts/taskForm.html";
    }

}
