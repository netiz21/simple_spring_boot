package com.thanos.springboot.common.demo.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peiheng.zph created on 17/6/20 下午5:27
 * @version 1.0
 */
@Shine
public class MahouShoujo {
  private static final Logger logger = LoggerFactory.getLogger(MahouShoujo.class);

  public void attack() {
    logger.info("Magical Splash Flare!");
  }
}
