package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kpfu.itis.dto.AccountDto;
import ru.kpfu.itis.dto.CourseOrGroupDto;
import ru.kpfu.itis.dto.SubjectDto;
import ru.kpfu.itis.mapper.AccountDtoMapper;
import ru.kpfu.itis.mapper.SubjectDtoMapper;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.processing.SimpleService;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.itis.util.Constant;

import java.util.ArrayList;
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

    @Autowired
    private SimpleService simpleService;

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

    @RequestMapping(value = "/disciplines", method = RequestMethod.GET)
    @ResponseBody
    public List<SubjectDto> getDisciplines() {
        List<Subject> subjects = simpleService.fetchAll(Subject.class);
        return subjects.stream().map(new SubjectDtoMapper()).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getCoursesAndGroups", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseOrGroupDto> getCoursesAndGroups() {
        List<AcademicGroup> academicGroups = simpleService.fetchAll(AcademicGroup.class);
        List<StudyCourse> studyCourses = simpleService.fetchAll(StudyCourse.class);
        return prepareResultList(academicGroups, studyCourses);
    }


    public List<CourseOrGroupDto> prepareResultList(List<AcademicGroup> academicGroups, List<StudyCourse> studyCourses) {
        List<CourseOrGroupDto> resultList = new ArrayList<>();
        for (AcademicGroup academicGroup : academicGroups) {
            CourseOrGroupDto courseOrGroupDto = new CourseOrGroupDto();
            courseOrGroupDto.setIsGroup(true);
            courseOrGroupDto.setId(academicGroup.getId());
            courseOrGroupDto.setNumber(academicGroup.getName());
            resultList.add(courseOrGroupDto);
        }
        for (StudyCourse studyCourse : studyCourses) {
            CourseOrGroupDto courseOrGroupDto = new CourseOrGroupDto();
            courseOrGroupDto.setNumber(studyCourse.getNumber() + " курс");
            courseOrGroupDto.setIsGroup(false);
            courseOrGroupDto.setId(studyCourse.getId());
            resultList.add(courseOrGroupDto);
        }
        return resultList;
    }

}

