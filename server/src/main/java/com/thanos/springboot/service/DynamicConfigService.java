package com.thanos.springboot.service;

/**
 * @author solarknight created on 2018/3/25 15:55
 * @version 1.0
 */
public interface DynamicConfigService {

  <T> T getOrDefault(String key, T defaultValue);
}
