package com.thanos.springboot.common.demo;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peiheng.zph created on 17/3/4 下午5:40
 * @version 1.0
 */
public class GenericReflectionDemo {

  public static <E> void iterateList(List<E> list) {
    doReflection(list);
    System.out.println(JSON.toJSONString(list));
  }

  private static <E> void doReflection(List<E> list) {
    List<Object> list1 = (List<Object>) list;
    Object obj = list1.get(0);
    System.out.println(obj.getClass());
  }

  public static void main(String[] args) {
    List<Integer> integerList = new ArrayList<>();
    integerList.add(1);
    integerList.add(3);
    integerList.add(2);
    GenericReflectionDemo.iterateList(integerList);
  }
}
