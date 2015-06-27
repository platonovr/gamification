package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.AccountInfoDto;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rigen on 26.06.15.
 */

@RequestMapping(Constant.API_URI_PREFIX + "/rating")
@RestController("apiRatingController")
public class RatingController {
    @Autowired
    AccountInfoService accountInfoService;

    @RequestMapping
    @ResponseBody
    public List<AccountInfoDto> getUsersRating() {
        AccountInfo accountInform = accountInfoService.findById(0L); //todo
        ArrayList<AccountInfoDto> accountInfoDTOs = new ArrayList<>();
        for (AccountInfo accountInfo : accountInfoService.getAllAndSort(accountInform)) {
            accountInfoDTOs.add(accountInfoToDto(accountInfo));
        }
        return accountInfoDTOs;
    }

    public int getUserRating() {
        Long accountId = 0L;
        AccountInfo accountInform = accountInfoService.findById(accountId); //todo
        int ratingPosition = 1;
        for (AccountInfo accountInfo : accountInfoService.getAllAndSort(accountInform)) {
            if (accountInfo.getAccount().getId() == accountId)
                break;
            ratingPosition++;
        }

        return ratingPosition;
    }

    private AccountInfoDto accountInfoToDto(AccountInfo accountInfo) {
        AccountInfoDto accountInfoDTO = new AccountInfoDto();
        accountInfoDTO.setId(accountInfo.getId());
        accountInfoDTO.setFirst_name(accountInfo.getFirstName());
        accountInfoDTO.setLast_name(accountInfo.getLastName());
        accountInfoDTO.setPhoto(accountInfo.getPhoto());
        accountInfoDTO.setRating(accountInfo.getPoint());
        return accountInfoDTO;
    }
}
