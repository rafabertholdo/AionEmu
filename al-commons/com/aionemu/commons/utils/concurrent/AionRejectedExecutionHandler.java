package com.aionemu.commons.utils.concurrent;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.log4j.Logger;

public final class AionRejectedExecutionHandler implements RejectedExecutionHandler {
  private static final Logger log = Logger.getLogger(AionRejectedExecutionHandler.class);

  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    if (executor.isShutdown()) {
      return;
    }
    log.warn(r + " from " + executor, new RejectedExecutionException());

    if (Thread.currentThread().getPriority() > 5) {
      (new Thread(r)).start();
    } else {
      r.run();
    }
  }
}
