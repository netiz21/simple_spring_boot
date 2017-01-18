package com.thanos.springboot.common.core.rule;

import com.alibaba.fastjson.JSON;
import com.thanos.springboot.common.core.condition.TimerMonitorCondition;
import com.thanos.springboot.common.core.operator.MathOperator;
import com.thanos.springboot.common.core.target.TimerMonitorTarget;
import com.thanos.springboot.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author peiheng.zph created on 2017/1/18 22:11
 * @version 1.0
 */
public interface TimerMonitorRule extends MonitorRule<String, Double> {

    TimerMonitorTarget target();

    TimerMonitorCondition condition();

    public static class TimeMonitorRuleParser {
        public static List<TimerMonitorRule> parse(String monitorRuleStr) {
            List<RegularTimerMonitorRule> rules = JSON.parseArray(monitorRuleStr, RegularTimerMonitorRule.class);
            if (CollectionUtils.isEmpty(rules)) {
                return CollectionUtils.emptyList();
            }

            List<TimerMonitorRule> list = new ArrayList<TimerMonitorRule>();
            for (RegularTimerMonitorRule rule : rules) {
                rule.initTargetAndCondition();
                list.add(rule);
            }
            return list;
        }

        private static class RegularTimerMonitorRule implements TimerMonitorRule {
            private static final Pattern pattern = Pattern.compile("(\\d+)\\s?([hmd])\\s.([<>]=.)\\s?(\\d+)");

            private String keyword;
            private String threshold;

            private ThresholdExpression thresholdExpression;

            private TimerMonitorTarget target;
            private TimerMonitorCondition condition;

            @Override
            public TimerMonitorTarget target() {
                return target;
            }

            @Override
            public TimerMonitorCondition condition() {
                return condition;
            }

            private void initTargetAndCondition() {
                checkState();
                thresholdExpression = parseThresholdExpression(threshold);
                target = generateTarget(keyword, thresholdExpression);
                condition = generateCondition(thresholdExpression);
            }

            private ThresholdExpression parseThresholdExpression(String threshold) {
                Matcher matcher = pattern.matcher(threshold);
                while (matcher.find()) {
                    ThresholdExpression expression = new ThresholdExpression();
                    expression.setTimeCount(Integer.valueOf(matcher.group(1)));

                    expression.setMathOperator(MathOperator.of(matcher.group(3)));
                    expression.setThreshold(Integer.valueOf(matcher.group(4)));
                    return expression;
                }
                return null;
            }

            private TimerMonitorTarget generateTarget(final String keyword, final ThresholdExpression thresholdExpression) {
                return new TimerMonitorTarget() {
                    @Override
                    public int timeCount() {
                        return thresholdExpression.getTimeCount();
                    }

                    @Override
                    public TimeUnit timeUnit() {
                        return thresholdExpression.getTimeUnit();
                    }

                    @Override
                    public String value() {
                        return keyword;
                    }
                };
            }

            private TimerMonitorCondition generateCondition(final ThresholdExpression thresholdExpression) {
                return new TimerMonitorCondition() {
                    @Override
                    public MathOperator operator() {
                        return thresholdExpression.getMathOperator();
                    }

                    @Override
                    public double threshold() {
                        return thresholdExpression.getThreshold();
                    }

                    @Override
                    public boolean meets(Double input) {
                        return thresholdExpression.getMathOperator().matches(input, thresholdExpression.getThreshold());
                    }
                };
            }

            private void checkState() {
                if (keyword == null) {
                    throw new IllegalStateException("Keyword is null!");
                }
                if (threshold == null) {
                    throw new IllegalStateException("Threshold is null!");
                }
                if (!pattern.matcher(threshold).matches()) {
                    throw new IllegalStateException("Threshold is not illegal!");
                }
            }


            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public String getThreshold() {
                return threshold;
            }

            public void setThreshold(String threshold) {
                this.threshold = threshold;
            }
        }

        private static class ThresholdExpression {
            private int timeCount;
            private TimeUnit timeUnit;
            private MathOperator mathOperator;
            private double threshold;

            public int getTimeCount() {
                return timeCount;
            }

            public void setTimeCount(int timeCount) {
                this.timeCount = timeCount;
            }

            public TimeUnit getTimeUnit() {
                return timeUnit;
            }

            public void setTimeUnit(TimeUnit timeUnit) {
                this.timeUnit = timeUnit;
            }

            public MathOperator getMathOperator() {
                return mathOperator;
            }

            public void setMathOperator(MathOperator mathOperator) {
                this.mathOperator = mathOperator;
            }

            public double getThreshold() {
                return threshold;
            }

            public void setThreshold(double threshold) {
                this.threshold = threshold;
            }
        }
    }
}
