package ru.kpfu.itis.processing.badges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.BadgeConstants;
import ru.kpfu.itis.processing.badges.impl.AuthBadgeChecker;
import ru.kpfu.itis.processing.badges.impl.Badge10TaskEndChecker;
import ru.kpfu.itis.processing.badges.impl.Badge5TaskEndChecker;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ainurminibaev on 14.09.15.
 */
@Component
public class BadgesPackImpl implements BadgesPack {

    private Map<Long, AbstractBadgeChecker> badgeMap;

    @Autowired
    Badge10TaskEndChecker badge10TaskEndChecker;

    @Autowired
    AuthBadgeChecker authBadgeChecker;

    @Autowired
    Badge5TaskEndChecker badge5TaskEndChecker;


    @PostConstruct
    public void postConstruct() {
        badgeMap = new HashMap<>();
        badgeMap.put(BadgeConstants.AUTH_BADGE_ID, authBadgeChecker);
        badgeMap.put(BadgeConstants.BADGE_10_TASK_END, badge10TaskEndChecker);
        badgeMap.put(BadgeConstants.BADGE_5_TASK_END, badge5TaskEndChecker);
    }


    @Override
    public AbstractBadgeChecker getBadge(Long code) {
        return badgeMap.get(code);
    }

}
