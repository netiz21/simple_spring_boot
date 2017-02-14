package com.thanos.monitor.ext.logback.support.notify;

import com.thanos.monitor.ext.logback.support.contact.ContactInfo;
import com.thanos.monitor.ext.logback.util.JsonUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author peiheng.zph created on 17/2/13 下午11:13
 * @version 1.0
 */
public final class DING {
  private static final Logger logger = LoggerFactory.getLogger(DING.class);

  private static final String DING_HOST = "https://dd.uc.cn/msg/send";
  private static final String CLIENT_ID = "13";
  private static final String MSG_TYPE = "text";
  private static final String SECRET = "0f29cb926ce69bb25bc7aca36d2d8f9f";
  private static final String DEFAULT_CHARSET = "utf-8";

  private static final String MOBILE_SPLITTER = ",";

  private DING() {
  }

  public static void send(String msg, List<ContactInfo> contactInfo) {
    HttpClient httpClient = new HttpClient();
    PostMethod method = new PostMethod(DING_HOST);
    Map<String, String[]> allParams = new HashMap<String, String[]>();

    allParams.put("clientId", new String[]{CLIENT_ID});
    String nonce = UUID.randomUUID().toString();
    allParams.put("nonce", new String[]{nonce});

    // 业务参数
    String message = msg + "\n" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss.SSS");
    String mobiles = joinContactInfo(contactInfo);

    allParams.put("mobiles", new String[]{mobiles});
    allParams.put("message", new String[]{message});
    allParams.put("type", new String[]{MSG_TYPE});

    String sign = computeSign(Integer.parseInt(CLIENT_ID), SECRET, nonce, allParams);
    method.addParameter(new NameValuePair("clientId", CLIENT_ID));
    method.addParameter(new NameValuePair("mobiles", mobiles));
    method.addParameter(new NameValuePair("message", message));
    method.addParameter(new NameValuePair("nonce", nonce));
    method.addParameter(new NameValuePair("signature", sign));
    method.addParameter(new NameValuePair("type", MSG_TYPE));

    method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARSET);
    try {
      httpClient.executeMethod(method);
    } catch (Exception e) {
      logger.error("Send ding msg throw exception, msg = {}", message, e);
    }

    String responseJson = null;
    if (method.getStatusCode() == 200) {
      try {
        responseJson = method.getResponseBodyAsString();
        DingResp resp = JsonUtil.parseObject(responseJson, DingResp.class);
        if (resp.getCode() != 0) {
          logger.error("Send ding msg fail, response = {}", responseJson);
        }
      } catch (IOException e) {
        logger.error("Send ding msg throw exception, msg = {}", message, e);
      }
      logger.info(responseJson);
    }
  }

  private static String joinContactInfo(List<ContactInfo> contactInfo) {
    StringBuilder sb = new StringBuilder();
    for (ContactInfo contact : contactInfo) {
      sb.append(contact.getPhone());
      sb.append(MOBILE_SPLITTER);
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  private static String computeSign(int clientId, String clientSecret, String nonce, Map<String, String[]> allParams) {
    List<String> keys = new ArrayList<String>();
    for (String key : allParams.keySet()) {
      if (key.equals("signature") || key.equals("body") || key.equals("action") || key.equals("controller")) {
        continue;
      }
      keys.add(key);
    }

    // 先按 key 升序排列
    Collections.sort(keys);
    List<String> items = new ArrayList<String>();
    for (String key : keys) {
      List<String> values = new ArrayList<String>();
      for (String value : allParams.get(key)) {
        values.add(value);
      }

      if (values.size() > 0) {
        // 再按 value 升序排列
        Collections.sort(values);
        for (String value : values) {
          items.add(key + "=" + value);
        }
      } else {
        items.add(key + "=");
      }
    }

    StringBuilder sb = new StringBuilder();
    for (String item : items) {
      sb.append(item);
    }
    String content = sb.toString();

    // 验证摘要，算法：md5(APPID+SECRET_KEY+REQ_ID+签名内容)
    String text = clientId + clientSecret + nonce + content;

    return DigestUtils.md5Hex(text).toLowerCase();
  }

  private static class DingResp {
    private int code;
    private String message;
    private Object data;

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public Object getData() {
      return data;
    }

    public void setData(Object data) {
      this.data = data;
    }
  }

}
