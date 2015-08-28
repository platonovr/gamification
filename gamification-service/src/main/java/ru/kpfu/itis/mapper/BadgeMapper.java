package ru.kpfu.itis.mapper;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.dto.BadgeDto;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.Badge;
import ru.kpfu.itis.model.Subject;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.AccountBadgeService;

import java.util.stream.Collectors;

import static org.hibernate.Hibernate.isInitialized;

/**
 * Created by ainurminibaev on 10.08.15.
 */
@Component("simpleBadgeMapper")
public class BadgeMapper implements Mapper<Badge, BadgeDto> {
    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    TaskMapper simpleTaskMapper;

    @Autowired
    AccountBadgeMapper accountBadgeMapper;

    @Autowired
    SecurityService securityService;

    @Autowired
    AccountDao accountDao;

    @Autowired
    AccountBadgeService accountBadgeService;

    @Autowired
    SimpleDao simpleDao;

    private boolean includeTasks;

    public BadgeMapper() {
        includeTasks = false;
    }

    public BadgeMapper(boolean includeTasks) {
        this.includeTasks = includeTasks;
    }

    @Override
    public Badge fromDto(BadgeDto dto) {
        return null;
    }

    @Override
    public BadgeDto toDto(Badge badge) {
        if (badge == null) {
            return null;
        }
        Subject badgeSubject = badge.getSubject();
        BadgeDto badgeDto;
        if (isInitialized(badgeSubject) && badgeSubject != null) {
            badgeDto = new BadgeDto(badge.getId(),
                    badge.getName(), badge.getImage(), subjectMapper.toDto(badgeSubject),
                    badge.getType().name(), badge.getDescription());
        } else {
            badgeDto = new BadgeDto(badge.getId(),
                    badge.getName(), badge.getImage(),
                    badge.getType().name(), badge.getDescription());
        }
        if (includeTasks) {
            Hibernate.initialize(badge.getTasks());
            badgeDto.setChallenges(badge.getTasks().stream().map(simpleTaskMapper::toDto).collect(Collectors.toList()));
        }
        return badgeDto;
    }

    public BadgeDto toDto(AccountBadge accountBadge) {
        BadgeDto badgeDto = toDto(accountBadge.getBadge());
        badgeDto.setStatus(accountBadgeMapper.toDto(accountBadge));
        return badgeDto;
    }
}
