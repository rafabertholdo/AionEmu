/*     */ package com.aionemu.commons.utils.concurrent;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javolution.text.TextBuilder;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.ArrayUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RunnableStatsManager
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(RunnableStatsManager.class);
/*     */   
/*  48 */   private static final Map<Class<?>, ClassStat> classStats = new HashMap<Class<?>, ClassStat>();
/*     */ 
/*     */   
/*     */   private static final class ClassStat
/*     */   {
/*     */     private final String className;
/*     */     private final RunnableStatsManager.MethodStat runnableStat;
/*  55 */     private String[] methodNames = new String[0];
/*  56 */     private RunnableStatsManager.MethodStat[] methodStats = new RunnableStatsManager.MethodStat[0];
/*     */ 
/*     */     
/*     */     private ClassStat(Class<?> clazz) {
/*  60 */       this.className = clazz.getName().replace("com.aionemu.gameserver.", "");
/*  61 */       this.runnableStat = new RunnableStatsManager.MethodStat(this.className, "run()");
/*     */       
/*  63 */       this.methodNames = new String[] { "run()" };
/*  64 */       this.methodStats = new RunnableStatsManager.MethodStat[] { this.runnableStat };
/*     */       
/*  66 */       RunnableStatsManager.classStats.put(clazz, this);
/*     */     }
/*     */ 
/*     */     
/*     */     private RunnableStatsManager.MethodStat getRunnableStat() {
/*  71 */       return this.runnableStat;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private RunnableStatsManager.MethodStat getMethodStat(String methodName, boolean synchronizedAlready) {
/*  77 */       if (methodName == "run()") {
/*  78 */         return this.runnableStat;
/*     */       }
/*  80 */       for (int i = 0; i < this.methodNames.length; i++) {
/*  81 */         if (this.methodNames[i].equals(methodName))
/*  82 */           return this.methodStats[i]; 
/*     */       } 
/*  84 */       if (!synchronizedAlready)
/*     */       {
/*  86 */         synchronized (this) {
/*     */           
/*  88 */           return getMethodStat(methodName, true);
/*     */         } 
/*     */       }
/*     */       
/*  92 */       methodName = methodName.intern();
/*     */       
/*  94 */       RunnableStatsManager.MethodStat methodStat = new RunnableStatsManager.MethodStat(this.className, methodName);
/*     */       
/*  96 */       this.methodNames = (String[])ArrayUtils.add((Object[])this.methodNames, methodName);
/*  97 */       this.methodStats = (RunnableStatsManager.MethodStat[])ArrayUtils.add((Object[])this.methodStats, methodStat);
/*     */       
/*  99 */       return methodStat;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MethodStat
/*     */   {
/* 105 */     private final ReentrantLock lock = new ReentrantLock();
/*     */     
/*     */     private final String className;
/*     */     
/*     */     private final String methodName;
/*     */     private long count;
/*     */     private long total;
/* 112 */     private long min = Long.MAX_VALUE;
/* 113 */     private long max = Long.MIN_VALUE;
/*     */ 
/*     */     
/*     */     private MethodStat(String className, String methodName) {
/* 117 */       this.className = className;
/* 118 */       this.methodName = methodName;
/*     */     }
/*     */ 
/*     */     
/*     */     private void handleStats(long runTime) {
/* 123 */       this.lock.lock();
/*     */       
/*     */       try {
/* 126 */         this.count++;
/* 127 */         this.total += runTime;
/* 128 */         this.min = Math.min(this.min, runTime);
/* 129 */         this.max = Math.max(this.max, runTime);
/*     */       }
/*     */       finally {
/*     */         
/* 133 */         this.lock.unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static ClassStat getClassStat(Class<?> clazz, boolean synchronizedAlready) {
/* 140 */     ClassStat classStat = classStats.get(clazz);
/*     */     
/* 142 */     if (classStat != null) {
/* 143 */       return classStat;
/*     */     }
/* 145 */     if (!synchronizedAlready)
/*     */     {
/* 147 */       synchronized (RunnableStatsManager.class) {
/*     */         
/* 149 */         return getClassStat(clazz, true);
/*     */       } 
/*     */     }
/*     */     
/* 153 */     return new ClassStat(clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void handleStats(Class<? extends Runnable> clazz, long runTime) {
/* 158 */     getClassStat(clazz, false).getRunnableStat().handleStats(runTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void handleStats(Class<?> clazz, String methodName, long runTime) {
/* 163 */     getClassStat(clazz, false).getMethodStat(methodName, false).handleStats(runTime);
/*     */   }
/*     */   
/*     */   public enum SortBy
/*     */   {
/* 168 */     AVG("average"),
/* 169 */     COUNT("count"),
/* 170 */     TOTAL("total"),
/* 171 */     NAME("class"),
/* 172 */     METHOD("method"),
/* 173 */     MIN("min"),
/* 174 */     MAX("max");
/*     */ 
/*     */ 
/*     */     
/*     */     private final String xmlAttributeName;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Comparator<RunnableStatsManager.MethodStat> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SortBy(String xmlAttributeName) {
/*     */       this.comparator = new Comparator<RunnableStatsManager.MethodStat>()
/*     */         {
/*     */           public int compare(RunnableStatsManager.MethodStat o1, RunnableStatsManager.MethodStat o2) {
/*     */             Comparable c1 = RunnableStatsManager.SortBy.this.getComparableValueOf(o1);
/*     */             Comparable<Comparable> c2 = RunnableStatsManager.SortBy.this.getComparableValueOf(o2);
/*     */             if (c1 instanceof Number) {
/*     */               return c2.compareTo(c1);
/*     */             }
/*     */             String s1 = (String)c1;
/*     */             String s2 = (String)c2;
/*     */             int len1 = s1.length();
/*     */             int len2 = s2.length();
/*     */             int n = Math.min(len1, len2);
/*     */             for (int k = 0; k < n; k++) {
/*     */               char ch1 = s1.charAt(k);
/*     */               char ch2 = s2.charAt(k);
/*     */               if (ch1 != ch2) {
/*     */                 if (Character.isUpperCase(ch1) != Character.isUpperCase(ch2)) {
/*     */                   return ch2 - ch1;
/*     */                 }
/*     */                 return ch1 - ch2;
/*     */               } 
/*     */             } 
/*     */             int result = len1 - len2;
/*     */             if (result != 0) {
/*     */               return result;
/*     */             }
/*     */             switch (RunnableStatsManager.SortBy.this) {
/*     */               case METHOD:
/*     */                 return RunnableStatsManager.SortBy.NAME.comparator.compare(o1, o2);
/*     */             } 
/*     */             return 0;
/*     */           }
/*     */         };
/*     */       this.xmlAttributeName = xmlAttributeName;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Comparable getComparableValueOf(RunnableStatsManager.MethodStat stat) {
/*     */       switch (this) {
/*     */         case AVG:
/*     */           return Long.valueOf(stat.total / stat.count);
/*     */         case COUNT:
/*     */           return Long.valueOf(stat.count);
/*     */         case TOTAL:
/*     */           return Long.valueOf(stat.total);
/*     */         case NAME:
/*     */           return stat.className;
/*     */         case METHOD:
/*     */           return stat.methodName;
/*     */         case MIN:
/*     */           return Long.valueOf(stat.min);
/*     */         case MAX:
/*     */           return Long.valueOf(stat.max);
/*     */       } 
/*     */       throw new InternalError();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 255 */     private static final SortBy[] VALUES = values();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dumpClassStats() {
/* 260 */     dumpClassStats(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void dumpClassStats(SortBy sortBy) {
/* 266 */     List<MethodStat> methodStats = new ArrayList<MethodStat>();
/*     */     
/* 268 */     synchronized (RunnableStatsManager.class) {
/*     */       
/* 270 */       for (ClassStat classStat : classStats.values()) {
/* 271 */         for (MethodStat methodStat : classStat.methodStats) {
/* 272 */           if (methodStat.count > 0L)
/* 273 */             methodStats.add(methodStat); 
/*     */         } 
/*     */       } 
/* 276 */     }  if (sortBy != null) {
/* 277 */       Collections.sort(methodStats, sortBy.comparator);
/*     */     }
/* 279 */     List<String> lines = new ArrayList<String>();
/*     */     
/* 281 */     lines.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
/* 282 */     lines.add("<entries>");
/* 283 */     lines.add("\t<!-- This XML contains statistics about execution times. -->");
/* 284 */     lines.add("\t<!-- Submitted results will help the developers to optimize the server. -->");
/*     */     
/* 286 */     String[][] values = new String[SortBy.VALUES.length][methodStats.size()];
/* 287 */     int[] maxLength = new int[SortBy.VALUES.length];
/*     */     
/* 289 */     for (int i = 0; i < SortBy.VALUES.length; i++) {
/*     */       
/* 291 */       SortBy sort = SortBy.VALUES[i];
/*     */       
/* 293 */       for (int j = 0; j < methodStats.size(); j++) {
/*     */         String value;
/* 295 */         Comparable c = sort.getComparableValueOf(methodStats.get(j));
/*     */ 
/*     */ 
/*     */         
/* 299 */         if (c instanceof Number) {
/* 300 */           value = NumberFormat.getInstance(Locale.ENGLISH).format(((Number)c).longValue());
/*     */         } else {
/* 302 */           value = String.valueOf(c);
/*     */         } 
/* 304 */         values[i][j] = value;
/*     */         
/* 306 */         maxLength[i] = Math.max(maxLength[i], value.length());
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     for (int k = 0; k < methodStats.size(); k++) {
/*     */       
/* 312 */       TextBuilder tb = TextBuilder.newInstance();
/* 313 */       tb.append("\t<entry ");
/*     */       
/* 315 */       EnumSet<SortBy> set = EnumSet.allOf(SortBy.class);
/*     */       
/* 317 */       if (sortBy != null)
/*     */       {
/* 319 */         switch (sortBy) {
/*     */           
/*     */           case METHOD:
/*     */           case NAME:
/* 323 */             appendAttribute(tb, SortBy.NAME, values[SortBy.NAME.ordinal()][k], maxLength[SortBy.NAME.ordinal()]);
/*     */             
/* 325 */             set.remove(SortBy.NAME);
/*     */             
/* 327 */             appendAttribute(tb, SortBy.METHOD, values[SortBy.METHOD.ordinal()][k], maxLength[SortBy.METHOD.ordinal()]);
/*     */             
/* 329 */             set.remove(SortBy.METHOD);
/*     */             break;
/*     */           default:
/* 332 */             appendAttribute(tb, sortBy, values[sortBy.ordinal()][k], maxLength[sortBy.ordinal()]);
/* 333 */             set.remove(sortBy);
/*     */             break;
/*     */         } 
/*     */       
/*     */       }
/* 338 */       for (SortBy sort : SortBy.VALUES) {
/* 339 */         if (set.contains(sort))
/* 340 */           appendAttribute(tb, sort, values[sort.ordinal()][k], maxLength[sort.ordinal()]); 
/*     */       } 
/* 342 */       tb.append("/>");
/*     */       
/* 344 */       lines.add(tb.toString());
/* 345 */       TextBuilder.recycle(tb);
/*     */     } 
/*     */     
/* 348 */     lines.add("</entries>");
/*     */     
/* 350 */     PrintStream ps = null;
/*     */     
/*     */     try {
/* 353 */       ps = new PrintStream("log/MethodStats-" + System.currentTimeMillis() + ".log");
/*     */       
/* 355 */       for (String line : lines) {
/* 356 */         ps.println(line);
/*     */       }
/* 358 */     } catch (Exception e) {
/*     */       
/* 360 */       log.warn("", e);
/*     */     }
/*     */     finally {
/*     */       
/* 364 */       IOUtils.closeQuietly(ps);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendAttribute(TextBuilder tb, SortBy sortBy, String value, int fillTo) {
/* 370 */     tb.append(sortBy.xmlAttributeName);
/* 371 */     tb.append("=");
/*     */     
/* 373 */     if (sortBy != SortBy.NAME && sortBy != SortBy.METHOD)
/* 374 */       for (int i = value.length(); i < fillTo; i++) {
/* 375 */         tb.append(" ");
/*     */       } 
/* 377 */     tb.append("\"");
/* 378 */     tb.append(value);
/* 379 */     tb.append("\" ");
/*     */     
/* 381 */     if (sortBy == SortBy.NAME || sortBy == SortBy.METHOD)
/* 382 */       for (int i = value.length(); i < fillTo; i++)
/* 383 */         tb.append(" ");  
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\concurrent\RunnableStatsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */