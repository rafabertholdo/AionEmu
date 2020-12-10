/*     */ package com.aionemu.gameserver.utils;
/*     */ 
/*     */ import java.lang.management.LockInfo;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MonitorInfo;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeadlockDetector
/*     */   implements Runnable
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(DeadlockDetector.class);
/*     */   
/*  34 */   private int checkInterval = 0;
/*  35 */   private static String INDENT = "    ";
/*  36 */   private StringBuilder sb = null;
/*     */ 
/*     */   
/*     */   public DeadlockDetector(int checkInterval) {
/*  40 */     this.checkInterval = checkInterval * 1000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  46 */     boolean noDeadLocks = true;
/*     */     
/*  48 */     while (noDeadLocks) {
/*     */ 
/*     */       
/*     */       try {
/*  52 */         ThreadMXBean bean = ManagementFactory.getThreadMXBean();
/*  53 */         long[] threadIds = bean.findDeadlockedThreads();
/*     */         
/*  55 */         if (threadIds != null) {
/*  56 */           log.error("Deadlock detected!");
/*  57 */           this.sb = new StringBuilder();
/*  58 */           noDeadLocks = false;
/*     */           
/*  60 */           ThreadInfo[] infos = bean.getThreadInfo(threadIds);
/*  61 */           this.sb.append("\nTHREAD LOCK INFO: \n");
/*  62 */           for (ThreadInfo threadInfo : infos) {
/*     */             
/*  64 */             printThreadInfo(threadInfo);
/*  65 */             LockInfo[] lockInfos = threadInfo.getLockedSynchronizers();
/*  66 */             MonitorInfo[] monitorInfos = threadInfo.getLockedMonitors();
/*     */             
/*  68 */             printLockInfo(lockInfos);
/*  69 */             printMonitorInfo(threadInfo, monitorInfos);
/*     */           } 
/*     */           
/*  72 */           this.sb.append("\nTHREAD DUMPS: \n");
/*  73 */           for (ThreadInfo ti : bean.dumpAllThreads(true, true))
/*     */           {
/*  75 */             printThreadInfo(ti);
/*     */           }
/*  77 */           log.error(this.sb.toString());
/*     */         } 
/*  79 */         Thread.sleep(this.checkInterval);
/*     */       }
/*  81 */       catch (Exception ex) {
/*     */         
/*  83 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void printThreadInfo(ThreadInfo threadInfo) {
/*  90 */     printThread(threadInfo);
/*  91 */     this.sb.append(INDENT + threadInfo.toString() + "\n");
/*  92 */     StackTraceElement[] stacktrace = threadInfo.getStackTrace();
/*  93 */     MonitorInfo[] monitors = threadInfo.getLockedMonitors();
/*     */     
/*  95 */     for (int i = 0; i < stacktrace.length; i++) {
/*     */       
/*  97 */       StackTraceElement ste = stacktrace[i];
/*  98 */       this.sb.append(INDENT + "at " + ste.toString() + "\n");
/*  99 */       for (MonitorInfo mi : monitors) {
/*     */         
/* 101 */         if (mi.getLockedStackDepth() == i) {
/* 102 */           this.sb.append(INDENT + "  - locked " + mi + "\n");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void printThread(ThreadInfo ti) {
/* 110 */     this.sb.append("\nPrintThread\n");
/* 111 */     this.sb.append("\"" + ti.getThreadName() + "\"" + " Id=" + ti.getThreadId() + " in " + ti.getThreadState() + "\n");
/*     */     
/* 113 */     if (ti.getLockName() != null)
/*     */     {
/* 115 */       this.sb.append(" on lock=" + ti.getLockName() + "\n");
/*     */     }
/* 117 */     if (ti.isSuspended())
/*     */     {
/* 119 */       this.sb.append(" (suspended)\n");
/*     */     }
/* 121 */     if (ti.isInNative())
/*     */     {
/* 123 */       this.sb.append(" (running in native)\n");
/*     */     }
/* 125 */     if (ti.getLockOwnerName() != null)
/*     */     {
/* 127 */       this.sb.append(INDENT + " owned by " + ti.getLockOwnerName() + " Id=" + ti.getLockOwnerId() + "\n");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void printMonitorInfo(ThreadInfo threadInfo, MonitorInfo[] monitorInfos) {
/* 134 */     this.sb.append(INDENT + "Locked monitors: count = " + monitorInfos.length + "\n");
/* 135 */     for (MonitorInfo monitorInfo : monitorInfos) {
/*     */       
/* 137 */       this.sb.append(INDENT + "  - " + monitorInfo + " locked at " + "\n");
/* 138 */       this.sb.append(INDENT + "      " + monitorInfo.getLockedStackDepth() + " " + monitorInfo.getLockedStackFrame() + "\n");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void printLockInfo(LockInfo[] lockInfos) {
/* 145 */     this.sb.append(INDENT + "Locked synchronizers: count = " + lockInfos.length + "\n");
/* 146 */     for (LockInfo lockInfo : lockInfos)
/*     */     {
/* 148 */       this.sb.append(INDENT + "  - " + lockInfo + "\n");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\DeadlockDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */