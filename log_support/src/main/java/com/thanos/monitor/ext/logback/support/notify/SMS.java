package com.thanos.monitor.ext.logback.support.notify;

import com.thanos.monitor.ext.logback.support.contact.ContactInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author peiheng.zph created on 17/2/13 下午11:13
 * @version 1.0
 */
public final class SMS {
  private static final Logger logger = LoggerFactory.getLogger(SMS.class);

  private SMS() {
  }

  public static void send(String msg, List<ContactInfo> contactInfo) {
  }
}
