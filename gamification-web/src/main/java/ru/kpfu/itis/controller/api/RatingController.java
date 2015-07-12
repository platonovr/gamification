package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.AccountInfoDto;
import ru.kpfu.itis.dto.ErrorDto;
import ru.kpfu.itis.dto.enums.Error;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.itis.util.Constant;

import java.util.ArrayList;

/**
 * Created by Rigen on 26.06.15.
 */

@RequestMapping(Constant.API_URI_PREFIX + "/rating")
@RestController("apiRatingController")
public class RatingController {
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/")
    @ResponseBody
    public ResponseEntity getUsersRating() {
        Long id = 1L;
        AccountInfo accountInform = accountInfoService.findByAccountId(id); //todo
        if (accountInform == null) {
            return new ResponseEntity<>(new ErrorDto(Error.USER_INFO_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        ArrayList<AccountInfoDto> accountInfoDTOs = new ArrayList<>();
        for (AccountInfo accountInfo : accountInfoService.getAllAndSort(accountInform)) {
            accountInfoDTOs.add(accountInfoToDto(accountInfo));
        }
        return new ResponseEntity<>(accountInfoDTOs, HttpStatus.OK);
    }

    public int getUserRating(Long id) {
        AccountInfo accountInform = accountInfoService.findByAccountId(id); //todo
        if (accountInform == null) {
            return 0;
        }
        int ratingPosition = 1;
        for (AccountInfo accountInfo : accountInfoService.getAllAndSort(accountInform)) {
            if (accountInfo.getAccount().getId() == id)
                break;
            ratingPosition++;
        }

        return ratingPosition;
    }

    private AccountInfoDto accountInfoToDto(AccountInfo accountInfo) {
        AccountInfoDto accountInfoDTO = new AccountInfoDto();
        accountInfoDTO.setId(accountInfo.getId());
        accountInfoDTO.setFirstName(accountInfo.getFirstName());
        accountInfoDTO.setLastName(accountInfo.getLastName());
        accountInfoDTO.setPhoto(accountInfo.getPhoto());
        accountInfoDTO.setRating(accountInfo.getPoint());
        return accountInfoDTO;
    }
}
