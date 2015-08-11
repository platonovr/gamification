package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.BadgeDto;
import ru.kpfu.itis.dto.ItemsDto;
import ru.kpfu.itis.service.TaskService;
import ru.kpfu.itis.util.Constant;

/**
 * Created by ainurminibaev on 10.08.15.
 */
@Api(value = "badge", description = "operation with badges")
@RequestMapping(Constant.API_URI_PREFIX + "/badge")
@RestController
public class BadgeController {

    @Autowired
    TaskService taskService;

    @ApiOperation("get available badges")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemsDto<BadgeDto>> allBadges() {
        return new ResponseEntity<>(new ItemsDto<>(taskService.getAllBadges()), HttpStatus.OK);
    }

    @ApiOperation("get badge")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/{badge_id:[0-9]+}", method = RequestMethod.GET)
    public BadgeDto getBadgeById(@PathVariable(value = "badge_id") Long badgeId) {
        return taskService.findBadgeById(badgeId);
    }
}
