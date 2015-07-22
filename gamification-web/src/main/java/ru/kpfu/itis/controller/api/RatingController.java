package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.RatingDto;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.RatingService;
import ru.kpfu.itis.util.Constant;

import java.util.List;

/**
 * Created by Rigen on 26.06.15.
 */

@Api(value = "rating", description = "operation with user's rating")
@RequestMapping(Constant.API_URI_PREFIX + "/rating")
@RestController("apiRatingController")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private AccountInfoService accountInfoService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public ResponseEntity<List<RatingDto>> getRating(@RequestParam(required = false) Double offset,
                                                     @RequestParam(required = false) Integer limit) {
        AccountInfo accountInfo = accountInfoService.findByAccountId(1L);  //TODO accountInfo getting
        if (accountInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<RatingDto> items = ratingService.getRatingDtos(accountInfo, offset, limit);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    public int getUserRating(Long id) {
        return ratingService.getUserRatingDto(id).getPosition();
    }
}
