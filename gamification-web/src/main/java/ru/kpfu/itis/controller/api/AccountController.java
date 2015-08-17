package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.mapper.AccountProfileMapper;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.*;
import ru.kpfu.itis.util.Constant;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@Api(value = "user", description = "operation with users")
@RequestMapping(Constant.API_URI_PREFIX + "/user")
@RestController("apiAccountController")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private AccountBadgeService accountBadgeService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountProfileMapper accountProfileMapper;


    @ApiOperation(httpMethod = "GET", value = "get user's profile")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public ResponseEntity<AccountProfileDto> getProfile(@PathVariable Long id) {
        Account account = accountService.findById(id);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountInfo accountInfo = accountInfoService.findByAccount(account);
        if (accountInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountBadge> badges = accountBadgeService.findAllBadgesByAccount(account);
        Rating rating = ratingService.getUserRating(accountInfo.getId());
        if (rating == null) {
            ratingService.createUserRating(accountInfo, 0.0);
            ratingService.recalculateRating(accountInfo);
            rating = ratingService.getUserRating(accountInfo.getId());
        }
        AccountProfileDto profileDto = accountProfileMapper.map(account, accountInfo, badges, rating);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public ResponseEntity<AccountProfileDto> getMyProfile() {
        return getProfile(securityService.getCurrentUserId());
    }


}
