package com.bao.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther Bao
 * @date 2019/11/30 15:26
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }


}
