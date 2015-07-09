package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.util.Constant;

/**
 * Created by ainurminibaev on 27.03.15.
 */
@Controller
@Api(value = "Login", description = "Login based on username and password")
@RequestMapping(Constant.API_URI_PREFIX)
public class LoginController {

    @ApiOperation(httpMethod = "POST", value = "Proxy method for auth: really we use POST to /login. be careful")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password) {
        //stub method; never used, only for swagger; saw AuthenticationFilter.java
        return null;
    }

}
