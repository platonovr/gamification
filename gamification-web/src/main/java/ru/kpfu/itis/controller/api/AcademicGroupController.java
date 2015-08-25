package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.dto.AcademicGroupDto;
import ru.kpfu.itis.dto.ItemsDto;
import ru.kpfu.itis.mapper.AcademicGroupMapper;
import ru.kpfu.itis.model.AcademicGroup;
import ru.kpfu.itis.util.Constant;

import java.util.stream.Collectors;

/**
 * Created by ainurminibaev on 25.08.15.
 */
@Api(value = "groups", description = "")
@RequestMapping(Constant.API_URI_PREFIX + "/groups")
@RestController("academicGroupController")
public class AcademicGroupController {

    @Autowired
    AcademicGroupMapper academicGroupMapper;

    @Autowired
    SimpleDao simpleDao;

    @ApiOperation(httpMethod = "GET", value = "get groups list")
    @RequestMapping(value = "/ignored", method = RequestMethod.GET)
    @ResponseBody
    public ItemsDto<AcademicGroupDto> getAcademicGroups() {
        return new ItemsDto<>(simpleDao
                .fetchAll(AcademicGroup.class)
                .stream()
                .map(academicGroupMapper::toDto)
                .collect(Collectors.toList()));
    }

}
