package com.thanos.springboot.common.demo;

import java.util.concurrent.TimeUnit;

/**
 * The Java Virtual Machine continues to execute threads until either of the following occurs:<br/>
 * - The exit method of class Runtime has been called and the security manager has permitted the exit operation to take place. <br/>
 * - All threads that are not daemon threads have died, either by returning from the call to the run method or by throwing an
 * exception that propagates beyond the run method.
 *
 * @author solarknight created on 17/5/24 下午4:34
 * @version 1.0
 */
public class ThreadFinishDemo {
  public static void main(String[] args) {

    // The newly created thread is initially marked as being a daemon thread if
    // and only if the thread creating it is currently marked as a daemon thread.
    // The method setDaemon may be used to change whether or not a thread is a daemon.
    Thread t = new Thread(() -> {
      try {

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Sub thread finishes");

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

//    t.setDaemon(true);
    t.start();

    System.out.println("Main thread finishes");
  }
}
