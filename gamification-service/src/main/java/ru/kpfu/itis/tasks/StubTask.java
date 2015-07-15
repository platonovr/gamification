package ru.kpfu.itis.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.service.TaskService;

/**
 * @author rplatonov
 * @date 15.07.2015
 */

@Component("stubTask")
public class StubTask implements Runnable {

    @Autowired
    private TaskService taskService;

    @Override
    public void run() {
        taskService.stub();
    }
}
