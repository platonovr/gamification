package ru.kpfu.itis.mapper;

import ru.kpfu.itis.dto.AccountDto;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by Roman on 21.08.2015.
 */
public class AccountDtoMapper implements Function<Account, AccountDto> {


    @Override
    public AccountDto apply(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setRole(account.getUserRole());
        AccountInfo accountInfo = account.getAccountInfo();
        if (Objects.isNull(accountInfo)) {
            return accountDto;
        }
        accountDto.setFirstName(accountInfo.getFirstName() != null ? accountInfo.getFirstName() : "");
        accountDto.setLastName(accountInfo.getLastName() != null ? accountInfo.getLastName() : "");
        accountDto.setMiddleName(accountInfo.getMiddleName() != null ? accountInfo.getMiddleName() : "");
        return accountDto;
    }
}
