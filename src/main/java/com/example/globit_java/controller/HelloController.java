package com.example.globit_java.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    /**
     * Bài tập 1: Viết API "Hello world" và thực hiện sử dụng postman để call api này
     * GET /api/hello -> "Hello world"
     */
    @GetMapping("/hello")
    public String getHelloWorld() {
        return "Hello world";
    }

    /**
     * Demo @RequestParam: GET /api/hello-param?name=Nam
     */
    @GetMapping("/hello-param")
    public String helloParam(@RequestParam(defaultValue = "World") String name) {
        return "Hello " + name;
    }

    /**
     * Demo @PathVariable: GET /api/hello-path/Nam
     */
    @GetMapping("/hello-path/{name}")
    public String helloPath(@PathVariable String name) {
        return "Hello " + name;
    }

    /**
     * Demo @RequestBody: POST /api/hello-body
     */
    @PostMapping("/hello-body")
    public Map<String, Object> helloBody(@RequestBody Map<String, Object> body) {
        return body;
    }
}
