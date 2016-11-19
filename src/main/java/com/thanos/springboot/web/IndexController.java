package com.thanos.springboot.web;

import com.thanos.springboot.common.MessageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author solarknight created on 2016/11/20 0:16
 * @version 1.0
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private MessageResource messageResource;

    @RequestMapping("/hello")
    public String index() {
        return "Hello, world!";
    }

    @RequestMapping("/message")
    public String message(String name) {
        if (Objects.equals(messageResource.getName(), name)) {
            return messageResource.getContent();
        }
        return "Found no message by name " + name;
    }

    @RequestMapping("/reload")
    public String reload() {
        return "Reload success";
    }
}
