package com.thanos.monitor.ext.logback.support.contact;

import com.thanos.monitor.ext.logback.util.CollectionUtil;
import com.thanos.monitor.ext.logback.util.JsonUtil;

import java.util.List;

/**
 * @author peiheng.zph created on 17/2/13 下午10:31
 * @version 1.0
 */
public final class MonitorContacts {

  private static List<ContactInfo> contactInfoList = null;

  private static volatile boolean initialized = false;

  public static void init(String contactJsonStr) {
    contactInfoList = CollectionUtil.unmodifiableList(JsonUtil.parseArray(contactJsonStr,
        ContactInfo.class));
    initialized = true;
  }

  public static List<ContactInfo> allContact() {
    checkState();

    if (CollectionUtil.isEmpty(contactInfoList)) {
      return CollectionUtil.emptyList();
    }
    return contactInfoList;
  }

  private static void checkState() {
    if (!initialized) {
      throw new IllegalStateException("Contacts not prepared");
    }
  }

}
