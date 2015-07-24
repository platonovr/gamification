package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.RatingDto;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.RatingService;
import ru.kpfu.itis.util.Constant;

import java.util.List;

/**
 * Created by Rigen on 26.06.15.
 */

@RequestMapping(Constant.API_URI_PREFIX + "/rating")
@RestController("apiRatingController")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ApiOperation(httpMethod = "GET", value = "get user's rating")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public ResponseEntity<List<RatingDto>> getRating(@RequestParam(required = false) Double offset,
                                                     @RequestParam(required = false) Integer limit) {
        AccountInfo accountInfo = securityService.getCurrentUser().getAccountInfo();
        if (accountInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<RatingDto> items = ratingService.getRatingDtos(accountInfo, offset, limit);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ApiOperation(httpMethod = "GET", value = "search in users rating")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public ResponseEntity<List<RatingDto>> search(@RequestParam(required = false) String searchString,
                                                  @RequestParam(required = false) Double offset,
                                                  @RequestParam(required = false) Integer limit) {
        AccountInfo accountInfo = securityService.getCurrentUser().getAccountInfo();
        if (accountInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<RatingDto> items = ratingService.searchDto(accountInfo, searchString, offset, limit);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
