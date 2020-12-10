package com.aionemu.gameserver.utils;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import org.apache.log4j.Logger;

public class DeadlockDetector implements Runnable {
  private static final Logger log = Logger.getLogger(DeadlockDetector.class);

  private int checkInterval = 0;
  private static String INDENT = "    ";
  private StringBuilder sb = null;

  public DeadlockDetector(int checkInterval) {
    this.checkInterval = checkInterval * 1000;
  }

  public void run() {
    boolean noDeadLocks = true;

    while (noDeadLocks) {

      try {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.findDeadlockedThreads();

        if (threadIds != null) {
          log.error("Deadlock detected!");
          this.sb = new StringBuilder();
          noDeadLocks = false;

          ThreadInfo[] infos = bean.getThreadInfo(threadIds);
          this.sb.append("\nTHREAD LOCK INFO: \n");
          for (ThreadInfo threadInfo : infos) {

            printThreadInfo(threadInfo);
            LockInfo[] lockInfos = threadInfo.getLockedSynchronizers();
            MonitorInfo[] monitorInfos = threadInfo.getLockedMonitors();

            printLockInfo(lockInfos);
            printMonitorInfo(threadInfo, monitorInfos);
          }

          this.sb.append("\nTHREAD DUMPS: \n");
          for (ThreadInfo ti : bean.dumpAllThreads(true, true)) {
            printThreadInfo(ti);
          }
          log.error(this.sb.toString());
        }
        Thread.sleep(this.checkInterval);
      } catch (Exception ex) {

        ex.printStackTrace();
      }
    }
  }

  private void printThreadInfo(ThreadInfo threadInfo) {
    printThread(threadInfo);
    this.sb.append(INDENT + threadInfo.toString() + "\n");
    StackTraceElement[] stacktrace = threadInfo.getStackTrace();
    MonitorInfo[] monitors = threadInfo.getLockedMonitors();

    for (int i = 0; i < stacktrace.length; i++) {

      StackTraceElement ste = stacktrace[i];
      this.sb.append(INDENT + "at " + ste.toString() + "\n");
      for (MonitorInfo mi : monitors) {

        if (mi.getLockedStackDepth() == i) {
          this.sb.append(INDENT + "  - locked " + mi + "\n");
        }
      }
    }
  }

  private void printThread(ThreadInfo ti) {
    this.sb.append("\nPrintThread\n");
    this.sb.append("\"" + ti.getThreadName() + "\"" + " Id=" + ti.getThreadId() + " in " + ti.getThreadState() + "\n");

    if (ti.getLockName() != null) {
      this.sb.append(" on lock=" + ti.getLockName() + "\n");
    }
    if (ti.isSuspended()) {
      this.sb.append(" (suspended)\n");
    }
    if (ti.isInNative()) {
      this.sb.append(" (running in native)\n");
    }
    if (ti.getLockOwnerName() != null) {
      this.sb.append(INDENT + " owned by " + ti.getLockOwnerName() + " Id=" + ti.getLockOwnerId() + "\n");
    }
  }

  private void printMonitorInfo(ThreadInfo threadInfo, MonitorInfo[] monitorInfos) {
    this.sb.append(INDENT + "Locked monitors: count = " + monitorInfos.length + "\n");
    for (MonitorInfo monitorInfo : monitorInfos) {

      this.sb.append(INDENT + "  - " + monitorInfo + " locked at " + "\n");
      this.sb.append(
          INDENT + "      " + monitorInfo.getLockedStackDepth() + " " + monitorInfo.getLockedStackFrame() + "\n");
    }
  }

  private void printLockInfo(LockInfo[] lockInfos) {
    this.sb.append(INDENT + "Locked synchronizers: count = " + lockInfos.length + "\n");
    for (LockInfo lockInfo : lockInfos) {
      this.sb.append(INDENT + "  - " + lockInfo + "\n");
    }
  }
}
