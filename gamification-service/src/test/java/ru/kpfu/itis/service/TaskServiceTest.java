package ru.kpfu.itis.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskCategory;
import ru.kpfu.itis.processing.SimpleService;
import ru.kpfu.itis.security.SecurityService;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Roman on 27.06.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private SimpleService simpleService;

    private Account testAccount;

    private TaskCategory taskCategory;

    @Before
    public void setUp() throws Exception {


    }


    @Test
    public void submittingTask() {
        Account testAccount = TestToolkit.fakeAccount();
        testAccount = securityService.saveAccount(Account.class, testAccount);
        assertNotNull(testAccount);
        TaskCategory taskCategory = TestToolkit.fakeTaskCategory();
        taskCategory = taskService.save(taskCategory);
        assertNotNull(taskCategory);

        Task task = new Task();
        task.setAuthor(testAccount);
        task.setParticipantsCount(10);
        task.setMaxMark((byte) 5);
        task.setName("XO");
        task.setDescription("desctiption");
        task.setCategory(taskCategory);
        task.setCreateTime(new Date());
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task = taskService.submitTask(task);

        Task submittedTask = simpleService.findById(Task.class, task.getId());

        assertNotNull(submittedTask);


    }


}
