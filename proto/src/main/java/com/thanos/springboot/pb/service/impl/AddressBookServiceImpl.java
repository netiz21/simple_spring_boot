package com.thanos.springboot.pb.service.impl;

import com.thanos.springboot.pb.proto.AddressBookProtos.Person;
import com.thanos.springboot.pb.service.AddressBookService;

/**
 * @author peiheng.zph created on 17/3/30 下午8:43
 * @version 1.0
 */
public class AddressBookServiceImpl implements AddressBookService {

  @Override
  public Person buildPersonDemo() {
    Person john = Person.newBuilder()
        .setId(1234)
        .setName("John Doe")
        .setEmail("jode@example.com")
        .addPhones(Person.PhoneNumber.newBuilder()
            .setNumber("555-4321").build()).build();
    return john;
  }
}
