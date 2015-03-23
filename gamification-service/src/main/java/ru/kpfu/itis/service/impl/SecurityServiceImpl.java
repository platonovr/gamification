package ru.kpfu.itis.service.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.service.SecurityService;

/**
 * Created by Дамир on 08.02.2015.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext() != null ? SecurityContextHolder.getContext().getAuthentication() : null;
        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        } else {
            return (Account) authentication.getPrincipal();
        }
    }
}
