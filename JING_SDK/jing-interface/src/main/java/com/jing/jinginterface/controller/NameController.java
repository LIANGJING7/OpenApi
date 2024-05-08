package com.jing.jinginterface.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/name")
public class NameController {

    @PostMapping("/name")
    public String getName(HttpServletRequest request) {
        return "你访问的是"+"哈哈哈哈";
    }

}
