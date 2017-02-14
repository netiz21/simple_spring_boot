package com.thanos.monitor.ext.logback.support.notify;

import com.thanos.monitor.ext.logback.support.contact.ContactInfo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peiheng.zph created on 17/2/14 下午4:49
 * @version 1.0
 */
public class DINGTest {

  @Test
  public void send() throws Exception {
    List<ContactInfo> list = new ArrayList<ContactInfo>();
    list.add(buildContact("pei", "15940814130"));
    list.add(buildContact("lei", "18601018457"));
    DING.send("Today is Tuesday", list);

  }

  private ContactInfo buildContact(String name, String phone) {
    ContactInfo info = new ContactInfo();
    info.setName(name);
    info.setPhone(phone);
    return info;
  }
}