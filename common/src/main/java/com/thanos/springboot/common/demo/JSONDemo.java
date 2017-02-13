package com.thanos.springboot.common.demo;

import com.alibaba.fastjson.JSON;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author peiheng.zph created on 17/2/13 上午10:46
 * @version 1.0
 */
public class JSONDemo {

  public static void main(String[] args) {
    String json = "{\"name\": \"T\\\\dm\", \"age\": 10}";
    User user = JSON.parseObject(json, User.class);
    System.out.println(user);
    Pattern pattern1 = Pattern.compile("T\\dm");
    Pattern pattern2 = Pattern.compile(user.name);
    Matcher matcher1 = pattern1.matcher("T5m");
    Matcher matcher2 = pattern2.matcher("T5m");
    System.out.println(matcher1.find());
    System.out.println(matcher2.find());
  }

  private static class User {
    private String name;
    private int age;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    @Override
    public String toString() {
      return "User{" +
          "name='" + name + '\'' +
          ", age=" + age +
          '}';
    }
  }
}
