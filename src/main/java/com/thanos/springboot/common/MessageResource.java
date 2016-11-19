package com.thanos.springboot.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author solarknight created on 2016/11/20 0:33
 * @version 1.0
 */
@Component
@ConfigurationProperties(locations = "classpath:message.yaml", prefix = "msg")
public class MessageResource {

    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
