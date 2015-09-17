package ru.kpfu.itis.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.model.Rating;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ainurminibaev on 17.08.15.
 */
@Component
public class AccountProfileMapper {

    @Autowired
    @Qualifier("simpleBadgeMapper")
    BadgeMapper badgeMapper;

    public AccountProfileDto map(Account account, AccountInfo accountInfo,
                                 List<AccountBadge> badges, Rating rating) {
        AccountProfileDto profileDto = new AccountProfileDto();
        profileDto.setId(account.getId());
        profileDto.setLogin(account.getLogin());
        profileDto.setFirstName(accountInfo.getFirstName());
        profileDto.setLastName(accountInfo.getLastName());
        profileDto.setRating(rating.getPoint());
        profileDto.setRatingPosition(rating.getPosition());
        profileDto.setRole(account.getUserRole());
        if (badges != null) {
            profileDto.setBadges(badges.stream().map(badgeMapper::toDto).collect(Collectors.toList()));
        }
        return profileDto;
    }
}
