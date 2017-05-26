package com.thanos.springboot.common.demo.polygene;

import com.thanos.springboot.common.demo.polygene.impl.SpeakerMixin;

import org.qi4j.api.mixin.Mixins;

/**
 * Composite Oriented Programming Demo
 * {@see https://polygene.apache.org/}
 *
 * @author solarknight created on 17/5/25 下午5:13
 * @version 1.0
 */
@Mixins(SpeakerMixin.class)
public interface Speaker {
  String sayHello();
}
