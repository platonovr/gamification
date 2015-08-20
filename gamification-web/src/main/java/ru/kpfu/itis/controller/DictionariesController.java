package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kpfu.itis.dto.AccountDto;
import ru.kpfu.itis.mapper.AccountDtoMapper;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.itis.util.Constant;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Roman on 21.08.2015.
 */
@Controller
@RequestMapping(Constant.API_URI_PREFIX + "/dictionaries")
public class DictionariesController {

    @Autowired
    private AccountService accountService;


    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDto> getTeachers() {
        List<Account> teachers = accountService.getTeachers();
        return teachers.stream().map(new AccountDtoMapper()).collect(Collectors.toList());
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDto> getStudents() {
        List<Account> teachers = accountService.getStudents();
        return teachers.stream().map(new AccountDtoMapper()).collect(Collectors.toList());
    }
}

