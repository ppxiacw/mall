package com.atguigu.gulimall.product.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class testController {
    @Value("${bb.info}")
    public String info;
    @Value("${cc.info}")
    private  String cc;

    @GetMapping("/test")
    public  String test(){
        return info+""+cc;
    }

}
