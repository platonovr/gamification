package ru.kpfu.itis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.kpfu.itis.model.SimpleAuthUser;
import ru.kpfu.jbl.auth.AuthenticationFilter;
import ru.kpfu.jbl.auth.config.*;
import ru.kpfu.jbl.auth.ep.LogoutRequestHandler;
import ru.kpfu.jbl.auth.ep.RestAuthenticationEntryPoint;
import ru.kpfu.jbl.auth.ep.WriteLogoutSuccessHandler;
import ru.kpfu.jbl.auth.service.TokenService;
import ru.kpfu.jbl.auth.service.impl.SecurityContextHolderServiceImpl;

@Configuration
@EnableWebMvcSecurity
@Import(value = {AuthSecurityModuleConfig.class,
        ShaProvidersConfig.class,
        AuthWebServiceConfig.class,
        MongoTokenServiceConfig.class,
        SpringMongoConfig.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("webAuthProvider")
    private AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("tokenAuthProvider")
    private AuthenticationProvider tokenAuthenticationProvider;

    @Autowired
    TokenService tokenService;

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    LogoutRequestHandler logoutRequestHandler;

    @Autowired
    WriteLogoutSuccessHandler writeLogoutSuccessHandler;

    private ObjectMapper mapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(writeLogoutSuccessHandler)
                .addLogoutHandler(logoutRequestHandler)
                .and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                antMatchers("/api/*").permitAll().
                antMatchers("/registration").permitAll().
//                antMatchers("/api/v1/user/**").hasAnyRole("STUDENT", "ADMIN", "TEACHER", "ANONYMOUS").
//                antMatchers("/api/v1/rating/**").hasAnyRole("STUDENT", "ADMIN", "TEACHER", "ANONYMOUS").
//                antMatchers("/api/v1/challenge/**").hasAnyRole("STUDENT", "ADMIN", "TEACHER", "ANONYMOUS").
        antMatchers("/api/v1/challenge/my").hasAnyRole("ADMIN", "TEACHER").
                antMatchers("/api/v1/groups/**").permitAll().
                antMatchers("/api/v1/challenge/{\\d+}/enroll").hasRole("STUDENT").
                antMatchers("/api/v1/**").hasAnyRole("STUDENT", "ADMIN", "TEACHER", "ANONYMOUS").
                and().
                exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAuthenticationEntryPoint);

        http.addFilterBefore(new AuthenticationFilter(authenticationManager(), mapper), BasicAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider).
                authenticationProvider(tokenAuthenticationProvider);
    }

    @Bean(name = "myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public SecurityContextHolderServiceImpl<SimpleAuthUser> securityContextHolderService() {
        return new SecurityContextHolderServiceImpl<>(tokenService);
    }
}