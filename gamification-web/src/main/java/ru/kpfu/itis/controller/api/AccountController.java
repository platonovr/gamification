package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.dto.BadgeDto;
import ru.kpfu.itis.mapper.AccountBadgeMapper;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.model.Badge;
import ru.kpfu.itis.service.AccountBadgeService;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.itis.util.Constant;

import java.util.ArrayList;

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
    private RatingController ratingController;
    @Autowired
    AccountBadgeMapper accountBadgeMapper;


    @ApiOperation(httpMethod = "GET", value = "get user's profile")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public ResponseEntity<AccountProfileDto> getProfile(@PathVariable Long id) {
        Account account = accountService.findById(id);
//        if (account == null) {
//            return new ResponseEntity<>(new ErrorDto(Error.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
//        }
        AccountInfo accountInfo = accountInfoService.findByAccount(account);
//
//        if (accountInfo == null) {
//            return new ResponseEntity<>(new ErrorDto(Error.USER_INFO_NOT_FOUND), HttpStatus.NOT_FOUND);
//        }
        ArrayList<AccountBadge> badges = (ArrayList<AccountBadge>) accountBadgeService.findAllBadgesByAccount(account);
        AccountProfileDto profileDTO = packAccountProfileDto(account, accountInfo, badges);
        profileDTO.setRatingPosition(ratingController.getUserRating(id));
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @ResponseBody
    public ResponseEntity<AccountProfileDto> getMyProfile() {
        return getProfile(1L); //todo
    }


    private AccountProfileDto packAccountProfileDto(Account account, AccountInfo accountInfo,
                                                    ArrayList<AccountBadge> badges) {
        AccountProfileDto profileDto = new AccountProfileDto();
        profileDto.setId(account.getId());
        profileDto.setLogin(account.getLogin());
        profileDto.setFirstName(accountInfo.getFirstName());
        profileDto.setLastName(accountInfo.getLastName());
        profileDto.setRating(accountInfo.getPoint());
        ArrayList<BadgeDto> badgesDto = new ArrayList<>();
        BadgeDto dto;
        if (badges != null) {
            for (AccountBadge accountBadge : badges) {
                dto = new BadgeDto();
                Badge badge = accountBadge.getBadge();
                dto.setId(badge.getId());
                dto.setName(badge.getName());
                dto.setDescription(badge.getDescription());
                dto.setImage(badge.getImage());
                dto.setType(badge.getType().name());
                dto.setStatus(accountBadgeMapper.toDto(accountBadge));
                badgesDto.add(dto);
            }
        }
        profileDto.setBadges(badgesDto);
        return profileDto;
    }
}
