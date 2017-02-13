package com.thanos.monitor.ext.logback.support.parser;

import com.thanos.monitor.ext.logback.core.rule.LogMonitorRule;
import com.thanos.monitor.ext.logback.core.rule.RegularLogMonitorRule;
import com.thanos.monitor.ext.logback.util.CollectionUtils;
import com.thanos.monitor.ext.logback.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peiheng.zph created on 17/2/12 下午5:33
 * @version 1.0
 */
public class LogMonitorRuleParser {

  public static List<LogMonitorRule> parse(String monitorRuleStr) {
    monitorRuleStr = escapeBackslashAsRegex(monitorRuleStr);

    List<RegularLogMonitorRule> rules = JsonUtil.parseArray(monitorRuleStr, RegularLogMonitorRule.class);
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

  /**
   * 对json中的正则进行处理，转成java能正确处理的形式
   *
   * @param target target
   * @return proper regex string to process
   */
  private static String escapeBackslashAsRegex(String target) {
    return target.replaceAll("\\\\", "\\\\\\\\");
  }

}
