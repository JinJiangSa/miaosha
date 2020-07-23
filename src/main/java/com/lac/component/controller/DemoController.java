package com.lac.component.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/")
    public String getHello() {
        return "hello";
    }
    @GetMapping("/user/{string}")
    public String test(@PathVariable String string) {
        return "Hello Nacos :" + string;
    }
    @GetMapping("/danbing2226/{string}")
    public String test1(@PathVariable String string) {
        return "灰色天空 :" + string;
    }
    @GetMapping("/xiawanan/{str}")
    public String test2(@PathVariable String str) {
        return "夏婉安的歌曲:"+str;
    }
    @GetMapping("/huisetiankong/{str}")
    public String test3(@PathVariable String str) {
        return "听了无数遍:"+str;
    }

}
