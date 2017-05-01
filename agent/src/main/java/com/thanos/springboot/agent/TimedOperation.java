package com.thanos.springboot.agent;

/**
 * Created by solarknight on 2017/5/1.
 */
public class TimedOperation {

  public int incrTimed() {
    int j = 0;
    for (int i = 0; i < Integer.MAX_VALUE; i++) {
      j++;
      if (i % 2 == 0) {
        j--;
      }
    }
    return j;
  }
}
