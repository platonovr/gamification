package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AccountBadgeDto;
import ru.kpfu.itis.model.AccountBadge;

import java.math.BigDecimal;

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
            dto.setTheory(new BigDecimal(accountBadge.getTheory()));
            dto.setProgress(new BigDecimal(accountBadge.getProgress()));
            dto.setPractice(new BigDecimal(accountBadge.getPractice()));
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
