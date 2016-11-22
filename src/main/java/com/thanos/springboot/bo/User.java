package com.thanos.springboot.bo;

/**
 * @author solarknight created on 2016/11/21 0:12
 * @version 1.0
 */
public class User {

    private Long id;
    private String name;
    private Integer sex;
    private String descp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
