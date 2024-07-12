package com.joy.joyojcodesandbox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: joyoj-code-sandbox
 * @ClassName: MainController
 * @description: 主控制器
 * @author: Jason
 * @create: 2024-07-12 20:18
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@RestController("/")
public class MainController {


    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }
}
