package com.thanos.springboot.common.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import com.thanos.springboot.common.core.processor.MonitorProcessor;
import com.thanos.springboot.common.core.rule.MonitorRule;
import com.thanos.springboot.common.util.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.List;

/**
 * @author peiheng.zph created on 2017/1/18 21:12
 * @version 1.0
 */
public abstract class BaseMonitorFilter<E, T> extends TurboFilter {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseMonitorFilter.class);

    private String monitorRuleStr;

    private List<MonitorRule<E, T>> monitorRules;

    private List<MonitorProcessor<E, T>> monitorProcessors;

    protected abstract List<MonitorRule<E, T>> parseMonitorRules(String monitorRuleStr);

    protected abstract List<MonitorProcessor<E,T>> initProcessors();

    protected abstract boolean isLevelMatch(Level level);

    protected abstract FilterReply onNormalProcess(Marker marker, Logger logger, Level level, String format, Object[] params);

    protected abstract FilterReply onErrorProcess(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t);

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        if (!isLevelMatch(level)) {
            return FilterReply.NEUTRAL;
        }
        if (level == Level.ERROR || t != null) {
            return onErrorProcess(marker, logger, level, format, params, t);
        }
        return onNormalProcess(marker, logger, level, format, params);
    }

    @Override
    public void start() {
        super.start();
        initialize();
    }

    protected void initialize() {
        initMonitorRules();
        initMonitorProcessors();
    }

    protected void initMonitorRules() {
        List<MonitorRule<E, T>> rules = parseMonitorRules(monitorRuleStr);
        if (CollectionUtils.isEmpty(rules)) {
            logger.error("Parse no rules from rule str = {}", monitorRuleStr);
            throw new IllegalArgumentException("Error parse monitor rules!");
        }
        this.monitorRules = rules;
    }

    protected void initMonitorProcessors(){
        List<MonitorProcessor<E, T>> processors = initProcessors();
        if (CollectionUtils.isEmpty(processors)) {
            logger.error("No processors defined!");
            throw new IllegalStateException("No processors defined");
        }
        this.monitorProcessors = processors;
    }

    public void setMonitorRuleStr(String monitorRuleStr) {
        this.monitorRuleStr = monitorRuleStr;
    }
}
