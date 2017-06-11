package com.thanos.springboot.common.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author peiheng.zph created on 17/2/8 下午5:49
 * @version 1.0
 */
public class FluentIterableDemo {

  public static void main(String[] args) {
    List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
    List<Integer> filterResult = FluentIterable.from(list).filter(input -> input > 3).toList();
    System.out.println(JSON.toJSONString(filterResult));
  }
}
