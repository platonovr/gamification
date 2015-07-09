package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AccountInfoDto;
import ru.kpfu.itis.model.AcademicGroup;
import ru.kpfu.itis.model.AccountInfo;

import java.util.Optional;

/**
 * Created by Rigen on 02.07.15.
 */
@Component
public class AccountInfoMapper implements Mapper<AccountInfo, AccountInfoDto> {
    @Override
    public AccountInfo fromDto(AccountInfoDto dto) {
        return null;
    }

    @Override
    public AccountInfoDto toDto(AccountInfo accountInfo) {
        if (accountInfo != null) {
            AccountInfoDto dto = new AccountInfoDto();
            dto.setId(accountInfo.getId());
            dto.setFirstName(accountInfo.getFirstName());
            dto.setLastName(accountInfo.getLastName());
            dto.setPhoto(accountInfo.getPhoto());
            dto.setGroup(Optional.ofNullable(accountInfo.getGroup()).<String>map(AcademicGroup::getName).orElse(null));
            dto.setRating(accountInfo.getPoint());
            return dto;
        } else {
            return null;
        }

    }
}
