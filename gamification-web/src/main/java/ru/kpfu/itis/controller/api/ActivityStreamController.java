package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.ActivityDto;
import ru.kpfu.itis.dto.ItemsDto;
import ru.kpfu.itis.service.ActivityService;
import ru.kpfu.itis.util.Constant;

/**
 * Created by Rigen on 06.08.15.
 */
@Api(value = "activity stream", description = "activity stream showing")
@RequestMapping(Constant.API_URI_PREFIX + "/activity")
@RestController("apiActivityStreamController")
public class ActivityStreamController {

    @Autowired
    private ActivityService activityService;


    @ApiOperation(httpMethod = "GET", value = "get activity stream")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public ItemsDto<ActivityDto> getActivityStream(@RequestParam(value = "max_id", required = false) Long maxId) {
        return new ItemsDto<>(activityService.getActivityStream(maxId));
    }


}
