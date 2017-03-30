package com.thanos.springboot.pb.service.impl;

import com.thanos.springboot.pb.proto.AddressBookProtos.Person;
import com.thanos.springboot.pb.service.AddressBookService;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author peiheng.zph created on 17/3/30 下午8:43
 * @version 1.0
 */
public class AddressBookServiceImpl implements AddressBookService {


  @Override
  public void tryWrite() {
    Person john = Person.newBuilder()
        .setName("John")
//        .setSex(Person.Sex.NEUTRAL)
        .build();
    try {
      FileOutputStream fileOutputStream = new FileOutputStream("person.txt");
      john.writeTo(fileOutputStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void tryRead() {
    try {
      Person person = Person.parseFrom(new FileInputStream("person.txt"));
      System.out.println(person);
      System.out.println(person.getSex());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    AddressBookService addressBookService = new AddressBookServiceImpl();
//    addressBookService.tryWrite();
    addressBookService.tryRead();
  }
}
