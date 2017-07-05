package com.icy9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * API测试
 * Created by f00lish on 2017/7/4.
 */

@Controller
@RequestMapping(value = "/", name = "API测试")
public class IndexController {

    @RequestMapping(value = "/index" ,method = RequestMethod.GET)
    public String index()
    {
        return "redirect:swagger-ui.html";
    }

}
