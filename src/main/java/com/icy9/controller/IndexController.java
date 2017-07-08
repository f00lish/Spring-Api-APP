package com.icy9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 * API测试
 * Created by f00lish on 2017/7/4.
 */

@Controller
@RequestMapping(value = "/", name = "API测试")
public class IndexController {

    //屏蔽swagger上的显示，这里只是为了做个例子，实际上这个可以配置到WebMvcConfig中
    @ApiIgnore
    @RequestMapping(value = "/index" ,method = RequestMethod.GET)
    public String index()
    {
        return "redirect:swagger-ui.html";
    }

}
