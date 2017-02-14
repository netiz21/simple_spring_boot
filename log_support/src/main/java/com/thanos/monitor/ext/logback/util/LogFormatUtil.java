package com.thanos.monitor.ext.logback.util;

/**
 * @author peiheng.zph created on 17/2/14 上午11:33
 * @version 1.0
 */
public final class LogFormatUtil {

  private LogFormatUtil(){
  }

  public static String doFormat(String format, Object[] params) {
    if (StringUtil.isEmpty(format) || ArrayUtil.isEmpty(params)) {
      return format;
    }

    String[] sArray = format.split("\\{\\}");
    StringBuilder formattedMsg = new StringBuilder(format.length() << 1);
    for (int i = 0; i < sArray.length; i++) {
      formattedMsg.append(sArray[i]);
      if (i < params.length) {
        formattedMsg.append(params[i].toString());
      }
    }
    return formattedMsg.toString();
  }

}
