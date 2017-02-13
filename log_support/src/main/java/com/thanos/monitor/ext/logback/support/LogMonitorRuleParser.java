package com.thanos.monitor.ext.logback.support;

import com.alibaba.fastjson.JSON;
import com.thanos.monitor.ext.logback.core.rule.LogMonitorRule;
import com.thanos.monitor.ext.logback.core.rule.RegularLogMonitorRule;
import com.thanos.monitor.ext.logback.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peiheng.zph created on 17/2/12 下午5:33
 * @version 1.0
 */
public class LogMonitorRuleParser {

  public static List<LogMonitorRule> parse(String monitorRuleStr) {
    List<RegularLogMonitorRule> rules = JSON.parseArray(monitorRuleStr, RegularLogMonitorRule.class);
    if (CollectionUtils.isEmpty(rules)) {
      return CollectionUtils.emptyList();
    }

    List<LogMonitorRule> list = new ArrayList<LogMonitorRule>();
    for (RegularLogMonitorRule rule : rules) {
      rule.initialize();
      list.add(rule);
    }
    return list;
  }

  public static String escapeBackslash(String target, String replace) {
    return target.replaceAll("\\\\", replace);
  }

  public static void main(String[] args) {
    String s = "[{\"targetPattern\":\"BindException\",\"threshold\":\"3h > 5\",\"alertMsg\":\"BindException exceed threshold!\"}]";
    String tmp = escapeBackslash(s, "@#@");
    List<RegularLogMonitorRule> rules = JSON.parseArray(tmp, RegularLogMonitorRule.class);
    System.out.println(rules);
  }
}
