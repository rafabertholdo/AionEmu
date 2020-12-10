/*     */ package com.aionemu.commons.utils;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AEInfos
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(AEInfos.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printSection(String s) {
/*  39 */     s = "=[ " + s + " ]";
/*     */     
/*  41 */     while (s.length() < 79) {
/*  42 */       s = "-" + s;
/*     */     }
/*  44 */     System.out.println(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getMemoryInfo() {
/*  49 */     double max = (Runtime.getRuntime().maxMemory() / 1024L);
/*  50 */     double allocated = (Runtime.getRuntime().totalMemory() / 1024L);
/*  51 */     double nonAllocated = max - allocated;
/*  52 */     double cached = (Runtime.getRuntime().freeMemory() / 1024L);
/*  53 */     double used = allocated - cached;
/*  54 */     double useable = max - used;
/*  55 */     DecimalFormat df = new DecimalFormat(" (0.0000'%')");
/*  56 */     DecimalFormat df2 = new DecimalFormat(" # 'KB'");
/*  57 */     return new String[] { "+----", "| Global Memory Informations at " + getRealTime().toString() + ":", "|    |", "| Allowed Memory:" + df2.format(max), "|    |= Allocated Memory:" + df2.format(allocated) + df.format(allocated / max * 100.0D), "|    |= Non-Allocated Memory:" + df2.format(nonAllocated) + df.format(nonAllocated / max * 100.0D), "| Allocated Memory:" + df2.format(allocated), "|    |= Used Memory:" + df2.format(used) + df.format(used / max * 100.0D), "|    |= Unused (cached) Memory:" + df2.format(cached) + df.format(cached / max * 100.0D), "| Useable Memory:" + df2.format(useable) + df.format(useable / max * 100.0D), "+----" };
/*     */   }
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
/*     */   public static String[] getCPUInfo() {
/*  74 */     return new String[] { "Avaible CPU(s): " + Runtime.getRuntime().availableProcessors(), "Processor(s) Identifier: " + System.getenv("PROCESSOR_IDENTIFIER") };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getOSInfo() {
/*  82 */     return new String[] { "OS: " + System.getProperty("os.name") + " Build: " + System.getProperty("os.version"), "OS Arch: " + System.getProperty("os.arch") };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getJREInfo() {
/*  90 */     return new String[] { "Java Platform Information", "Java Runtime  Name: " + System.getProperty("java.runtime.name"), "Java Version: " + System.getProperty("java.version"), "Java Class Version: " + System.getProperty("java.class.version") };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getJVMInfo() {
/* 100 */     return new String[] { "Virtual Machine Information (JVM)", "JVM Name: " + System.getProperty("java.vm.name"), "JVM installation directory: " + System.getProperty("java.home"), "JVM version: " + System.getProperty("java.vm.version"), "JVM Vendor: " + System.getProperty("java.vm.vendor"), "JVM Info: " + System.getProperty("java.vm.info") };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRealTime() {
/* 112 */     SimpleDateFormat String = new SimpleDateFormat("H:mm:ss");
/* 113 */     return String.format(new Date());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void printMemoryInfo() {
/* 118 */     for (String line : getMemoryInfo()) {
/* 119 */       log.info(line);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printCPUInfo() {
/* 124 */     for (String line : getCPUInfo()) {
/* 125 */       log.info(line);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printOSInfo() {
/* 130 */     for (String line : getOSInfo()) {
/* 131 */       log.info(line);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printJREInfo() {
/* 136 */     for (String line : getJREInfo()) {
/* 137 */       log.info(line);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printJVMInfo() {
/* 142 */     for (String line : getJVMInfo()) {
/* 143 */       log.info(line);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printRealTime() {
/* 148 */     log.info(getRealTime().toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void printAllInfos() {
/* 153 */     printOSInfo();
/* 154 */     printCPUInfo();
/* 155 */     printJREInfo();
/* 156 */     printJVMInfo();
/* 157 */     printMemoryInfo();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\AEInfos.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */