package com.example.springboot001.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello! spring boot!";
    }

    /**
     * POST 接口返回 Java 对象；Spring MVC 会自动将其序列化为 JSON 响应。
     */
    @GetMapping("/hello2")
    public HelloResponse helloPost() {
        return new HelloResponse("hello! spring boot!", "POST");
    }

    /**
     * 响应数据结构。record 会自动生成构造器和访问方法，适合简单、不可变的 DTO。
     */
    public record HelloResponse(String message, String method) {
    }
}
