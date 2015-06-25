package ru.kpfu.itis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by timur on 17.06.15.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String taskCreationPage() {
        return "forward:/parts/taskForm.html";
    }

}
