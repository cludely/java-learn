package com.example.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstController {

    @RequestMapping("/hello")
    public String hello() {
        return "first";
    }

    // 一个controller可以写多个方法
    @RequestMapping("/other")
    public String other() {
        return "other";
    }
}
