package com.thanos.springboot.pb.service;

import com.thanos.springboot.pb.proto.AddressBookProtos;

/**
 * @author solarknight created on 17/3/29 下午8:09
 * @version 1.0
 */
public interface AddressBookService {
  AddressBookProtos.Person buildPersonDemo();
}
