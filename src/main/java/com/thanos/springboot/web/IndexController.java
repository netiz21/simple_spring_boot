package com.thanos.springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author solarknight created on 2016/11/20 0:16
 * @version 1.0
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello, world!";
    }
}
