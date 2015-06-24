package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AccountProfileDTO;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.model.AccountTask;
import ru.kpfu.itis.service.AccountBadgeService;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.itis.service.AccountTaskService;
import ru.kpfu.itis.util.Constant;

import java.util.ArrayList;

/**
 * Created by Rigen on 22.06.15.
 */

@RequestMapping(Constant.API_URI_PREFIX + "/account")
@RestController("apiAccountController")
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountInfoService accountInfoService;
    @Autowired
    AccountTaskService accountTaskService;
    @Autowired
    AccountBadgeService accountBadgeService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AccountProfileDTO getProfile(@PathVariable Long id) {
        Account account = accountService.findById(id);
        AccountInfo accountInfo = accountInfoService.findByAccount(account);
        ArrayList<AccountTask> tasks = (ArrayList<AccountTask>) accountTaskService.findCompleteTasksByAccount(account);
        ArrayList<AccountBadge> badges = (ArrayList<AccountBadge>) accountBadgeService.findAllBadgesByAccount(account);

        //todo to pack all information on DTO

        AccountProfileDTO profileDTO = packAccountProfileDto(account, accountInfo);
        return profileDTO;
    }


    private AccountProfileDTO packAccountProfileDto(Account account, AccountInfo accountInfo) {
        AccountProfileDTO profileDTO = new AccountProfileDTO();
        profileDTO.setLogin(account.getLogin());
        profileDTO.setId(account.getId());
        profileDTO.setFirstName(accountInfo.getFirstName());
        profileDTO.setMiddleName(accountInfo.getMiddleName());
        profileDTO.setLastName(accountInfo.getLastName());
        profileDTO.setPoint(accountInfo.getPoint());

        return profileDTO;
    }
}
