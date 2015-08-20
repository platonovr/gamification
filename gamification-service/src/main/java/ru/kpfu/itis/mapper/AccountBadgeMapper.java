package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AccountBadgeDto;
import ru.kpfu.itis.model.AccountBadge;

/**
 * Created by Rigen on 02.07.15.
 */
@Component
public class AccountBadgeMapper implements Mapper<AccountBadge, AccountBadgeDto> {
    @Override
    public AccountBadge fromDto(AccountBadgeDto dto) {
        return null;
    }

    @Override
    public AccountBadgeDto toDto(AccountBadge accountBadge) {
        if (accountBadge != null) {
            AccountBadgeDto dto = new AccountBadgeDto();
            dto.setTheory(accountBadge.getTheory());
            dto.setProgress(accountBadge.getProgress());
            dto.setPractice(accountBadge.getPractice());
            dto.setAccountId(accountBadge.getAccount().getId());
            dto.setBadgeId(accountBadge.getBadge().getId());
            dto.setId(accountBadge.getId());
            dto.setDate(accountBadge.getChangeTime());
            return dto;
        } else {
            return null;
        }

    }
}
