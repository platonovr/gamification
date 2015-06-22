package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AccountProfileDTO;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.itis.util.Constant;

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


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AccountProfileDTO getProfile(@PathVariable Long id) {
        Account account = accountService.findById(id);
        AccountInfo accountInfo = accountInfoService.findByAccount(account);

        //todo get a done tasks
        //todo calculate sum of points from list of tasks ???
        //todo get account's badges from list of tasks
        //todo to pack all information on DTO
        //???
        //PROFIT

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
