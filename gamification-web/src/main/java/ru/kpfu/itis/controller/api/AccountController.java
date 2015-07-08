package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.dto.BadgeDto;
import ru.kpfu.itis.dto.ResponseDto;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.AccountInfo;
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
    AccountService accountService;
    @Autowired
    AccountInfoService accountInfoService;
    @Autowired
    AccountBadgeService accountBadgeService;
    @Autowired
    RatingController ratingController;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseDto<AccountProfileDto>> getProfile(@PathVariable Long id) {
        Account account = accountService.findById(id);
        if (account == null) {
            return new ResponseEntity<>(new ResponseDto<>("User with requested id not found.",
                    HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        AccountInfo accountInfo = accountInfoService.findByAccount(account);

        if (accountInfo == null) {
            return new ResponseEntity<>(new ResponseDto<>("Information for user with requested id not found.",
                    HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        ArrayList<AccountBadge> badges = (ArrayList<AccountBadge>) accountBadgeService.findAllBadgesByAccount(account);
        AccountProfileDto profileDTO = packAccountProfileDto(account, accountInfo, badges);
        profileDTO.setRating_position(ratingController.getUserRating(id));
        return new ResponseEntity<>(new ResponseDto<>("", profileDTO, HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseDto<AccountProfileDto>> getMyProfile() {
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
        BadgeDto badge;
        if (badges != null) {
            for (AccountBadge accountBadge : badges) {
                badge = new BadgeDto();
                badge.setId(accountBadge.getBadge().getId());
                badge.setName(accountBadge.getBadge().getName());
                badge.setDescription(accountBadge.getBadge().getDescription());
                badge.setImage(accountBadge.getBadge().getImage());
                badge.setType(accountBadge.getBadge().getType().name());
                badgesDto.add(badge);
            }
        }
        profileDto.setBadges(badgesDto);
        return profileDto;
    }
}
