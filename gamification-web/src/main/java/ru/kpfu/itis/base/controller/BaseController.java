package ru.kpfu.itis.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * Created by romanplatonov on 16.06.15.
 */


@Controller
public class BaseController {

    @RequestMapping(value = "/base/common", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> test() {
        return Arrays.asList(1, 2, 3, 4);
    }

}
