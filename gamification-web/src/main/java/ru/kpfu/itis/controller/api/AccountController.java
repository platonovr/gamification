package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.dto.BadgeDto;
import ru.kpfu.itis.mapper.AccountBadgeMapper;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.service.AccountBadgeService;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.itis.service.RatingService;
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
    private RatingService ratingService;
    @Autowired
    AccountBadgeMapper accountBadgeMapper;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<AccountProfileDto> getProfile(@PathVariable Long id) {
        Account account = accountService.findById(id);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountInfo accountInfo = accountInfoService.findByAccount(account);
//
        if (accountInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ArrayList<AccountBadge> badges = (ArrayList<AccountBadge>) accountBadgeService.findAllBadgesByAccount(account);
        Rating rating = ratingService.getUserRating(accountInfo.getId());
        if (rating == null) {
            ratingService.createUserRating(accountInfo, 0.0);
            ratingService.recalculateRating(accountInfo);
            rating = ratingService.getUserRating(accountInfo.getId());
        }
        AccountProfileDto profileDTO = packAccountProfileDto(account, accountInfo, badges, rating);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<AccountProfileDto> getMyProfile() {
        return getProfile(1L);  //TODO account getting
    }


    private AccountProfileDto packAccountProfileDto(Account account, AccountInfo accountInfo,
                                                    ArrayList<AccountBadge> badges, Rating rating) {
        AccountProfileDto profileDto = new AccountProfileDto();
        profileDto.setId(account.getId());
        profileDto.setLogin(account.getLogin());
        profileDto.setFirstName(accountInfo.getFirstName());
        profileDto.setLastName(accountInfo.getLastName());
        profileDto.setRating(rating.getPoint());
        profileDto.setRatingPosition(rating.getPosition());
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
