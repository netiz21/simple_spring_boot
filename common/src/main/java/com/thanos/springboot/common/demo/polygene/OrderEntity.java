package com.thanos.springboot.common.demo.polygene;

/**
 * @author solarknight created on 17/5/25 下午7:37
 * @version 1.0
 */
//@Mixins()
public interface OrderEntity extends Order, Confirmable, HasSequenceNumber, HasLineItems,
    HasIdentity {
}
