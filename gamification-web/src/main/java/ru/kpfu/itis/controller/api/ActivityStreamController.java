package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.AccountBadgeService;
import ru.kpfu.itis.service.AccountTaskService;
import ru.kpfu.itis.service.TaskService;
import ru.kpfu.itis.util.Constant;

import java.util.List;

/**
 * Created by Rigen on 06.08.15.
 */
@Api(value = "activity stream", description = "activity stream showing")
@RequestMapping(Constant.API_URI_PREFIX + "/activity")
@RestController("apiActivityStreamController")
public class ActivityStreamController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AccountTaskService accountTaskService;
    @Autowired
    private AccountBadgeService accountBadgeService;


    @ApiOperation(httpMethod = "GET", value = "get activity stream")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public List<Object> getActivityStream(@RequestParam(required = false) Integer offset,
                                          @RequestParam(required = false) Integer limit) {


        return null;
    }


}
