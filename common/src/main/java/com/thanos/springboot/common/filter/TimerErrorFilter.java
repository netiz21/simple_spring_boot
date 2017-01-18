package com.thanos.springboot.common.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.spi.FilterReply;
import com.alibaba.fastjson.JSON;
import com.thanos.springboot.common.core.processor.MonitorProcessor;
import com.thanos.springboot.common.core.rule.MonitorRule;
import com.thanos.springboot.common.core.rule.TimerMonitorRule;
import com.thanos.springboot.common.core.target.MonitorTarget;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.List;

/**
 * @author peiheng.zph created on 2017/1/18 21:15
 * @version 1.0
 */
public class TimerErrorFilter extends BaseMonitorFilter<String, Double> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TimerErrorFilter.class);

    @Override
    protected List<MonitorRule<String, Double>> parseMonitorRules(String monitorRuleStr) {

        return null;
    }

    @Override
    protected List<MonitorProcessor<String, Double>> initProcessors() {
        return null;
    }

    @Override
    protected boolean isLevelMatch(Level level) {
        return false;
    }

    @Override
    protected FilterReply onNormalProcess(Marker marker, Logger logger, Level level, String format, Object[] params) {
        return null;
    }

    @Override
    protected FilterReply onErrorProcess(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        return null;
    }
}
