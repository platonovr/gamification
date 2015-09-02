package ru.kpfu.itis.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ainurminibaev on 25.08.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Test
    public void saveTest() {
        Assert.assertNotNull(accountService.findUserByLogin("anonymous101"));
    }
}
