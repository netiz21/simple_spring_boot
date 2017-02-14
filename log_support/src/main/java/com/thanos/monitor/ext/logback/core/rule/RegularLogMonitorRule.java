package com.thanos.monitor.ext.logback.core.rule;

import com.thanos.monitor.ext.logback.core.condition.LogMonitorCondition;
import com.thanos.monitor.ext.logback.core.condition.RegularLogMonitorCondition;
import com.thanos.monitor.ext.logback.core.expression.MathOperator;
import com.thanos.monitor.ext.logback.core.expression.SupportedTimeUnit;
import com.thanos.monitor.ext.logback.core.expression.ThresholdExpression;
import com.thanos.monitor.ext.logback.core.targ.LogMonitorTarget;
import com.thanos.monitor.ext.logback.core.targ.RegularLogMonitorTarget;
import com.thanos.monitor.ext.logback.core.trigger.AlertType;
import com.thanos.monitor.ext.logback.core.trigger.LogMonitorTrigger;
import com.thanos.monitor.ext.logback.core.trigger.RegularMultiLogMonitorTrigger;
import com.thanos.monitor.ext.logback.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author peiheng.zph created on 17/2/12 下午6:37
 * @version 1.0
 */
public class RegularLogMonitorRule implements LogMonitorRule {

  private static final String EXPRESSION_REGEX = "(\\d+)\\s*([smhd])\\s*([<>]=?)\\s*(\\d+)";
  private static final Pattern EXPRESSION_PATTERN = Pattern.compile(EXPRESSION_REGEX);

  private volatile boolean initialized = false;

  private String targetPattern;
  private String targetLevel = "ALL";
  private String threshold;
  private List<String> alertType;
  private String alertMsg;
  private boolean ignoreOrigin = true;

  private LogMonitorTarget monitorTarget;
  private LogMonitorCondition monitorCondition;
  private LogMonitorTrigger monitorTrigger;

  @Override
  public LogMonitorTarget target() {
    return monitorTarget;
  }

  @Override
  public LogMonitorCondition condition() {
    return monitorCondition;
  }

  @Override
  public LogMonitorTrigger trigger() {
    return monitorTrigger;
  }

  public void initialize() {
    if (initialized) {
      return;
    }

    checkState();
    initialized = true;

    // generate monitor target
    ThresholdExpression expr = parseThresholdExpression(threshold);
    monitorTarget = generateTarget(targetPattern, targetLevel, expr.getTimeCount(),
        expr.getTimeUnit(), ignoreOrigin);

    // generate monitor condition
    monitorCondition = generateCondition(expr.getMathOperator(), expr.getThreshold());

    // generate monitor trigger
    if (CollectionUtil.isEmpty(alertType)) {
      alertType = new ArrayList<String>();
      alertType.add(AlertType.LOG.getType());
    }
    monitorTrigger = generateTrigger(alertMsg, alertType);
  }

  private void checkState() {
    if (targetPattern == null) {
      throw new IllegalArgumentException("targetPattern can't be null!");
    }
    if (threshold == null) {
      throw new IllegalArgumentException("threshold can't be null!");
    }
    if (!EXPRESSION_PATTERN.matcher(threshold).matches()) {
      throw new IllegalArgumentException("Threshold is illegal!");
    }
    if (alertMsg == null) {
      throw new IllegalArgumentException("alertMsg can't be null!");
    }
  }

  private ThresholdExpression parseThresholdExpression(String threshold) {
    Matcher matcher = EXPRESSION_PATTERN.matcher(threshold);
    if (!matcher.find()) {
      throw new IllegalStateException("Monitor rule is illegal");
    }

    ThresholdExpression expression = new ThresholdExpression();
    expression.setTimeCount(Integer.parseInt(matcher.group(1)));
    expression.setTimeUnit(SupportedTimeUnit.of(matcher.group(2)).toTimeUnit());
    expression.setMathOperator(MathOperator.of(matcher.group(3)));
    expression.setThreshold(Integer.parseInt(matcher.group(4)));
    return expression;
  }

  private LogMonitorTarget generateTarget(String targetPattern, String targetLevel, int timeCount,
                                          TimeUnit timeUnit, boolean ignoreOrigin) {
    return RegularLogMonitorTarget.of(targetPattern, targetLevel, timeCount, timeUnit, ignoreOrigin);
  }

  private LogMonitorCondition generateCondition(MathOperator mathOperator, double threshold) {
    return RegularLogMonitorCondition.of(mathOperator, threshold);
  }

  private LogMonitorTrigger generateTrigger(String alertMsg, List<String> alertTypes) {
    return RegularMultiLogMonitorTrigger.of(alertMsg, alertTypes);
  }

  public String getTargetPattern() {
    return targetPattern;
  }

  public void setTargetPattern(String targetPattern) {
    this.targetPattern = targetPattern;
  }

  public String getTargetLevel() {
    return targetLevel;
  }

  public void setTargetLevel(String targetLevel) {
    this.targetLevel = targetLevel;
  }

  public String getThreshold() {
    return threshold;
  }

  public void setThreshold(String threshold) {
    this.threshold = threshold;
  }

  public List<String> getAlertType() {
    return alertType;
  }

  public void setAlertType(List<String> alertType) {
    this.alertType = alertType;
  }

  public String getAlertMsg() {
    return alertMsg;
  }

  public void setAlertMsg(String alertMsg) {
    this.alertMsg = alertMsg;
  }

  public boolean isIgnoreOrigin() {
    return ignoreOrigin;
  }

  public void setIgnoreOrigin(boolean ignoreOrigin) {
    this.ignoreOrigin = ignoreOrigin;
  }
}
