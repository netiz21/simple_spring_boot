package com.thanos.springboot.common.demo.lambda;

import java.util.function.Function;

/**
 * @author solarknight created on 2018/1/7 17:30
 * @version 1.0
 */
@FunctionalInterface
public interface Player extends Function<String, String> {
}
