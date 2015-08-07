package ru.kpfu.itis.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.kpfu.itis.model.AccountTask;

/**
 * Created by ainurminibaev on 07.08.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class CheckTaskTest {

    @Autowired
    AccountTaskService accountTaskService;

    @Autowired
    TaskService taskService;

    @Test
    @javax.transaction.Transactional
    public void test() {
        AccountTask byTaskAndAccount = accountTaskService.findByTaskAndAccount(100l, 2l);
        Assert.assertNotNull(byTaskAndAccount);
        taskService.setNewStatus(byTaskAndAccount);
    }

    @Test
//    @Transactional
    public void test1() {
        taskService.getCreatedTasks(1l, 0, 10);
    }
}
