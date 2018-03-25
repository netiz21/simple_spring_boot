package com.thanos.springboot.common;

import lombok.Data;

/**
 * @author solarknight created on 2018/3/25 19:13
 * @version 1.0
 */
@Data
public class ConfigChangeEvent {
  private String configName;
  private String originValue;
  private String currentValue;
}
