package ru.kpfu.itis.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.service.AccountService;

import java.util.Arrays;


@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private AccountService accountService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String password = authentication.getCredentials().toString();
        String login = authentication.getName();
        Account user = accountService.findUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        if (user.getPassword().equals(password)) {
            Authentication authToken =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            return authToken;
        } else {
            throw new BadCredentialsException("Bad user password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}