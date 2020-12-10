package com.aionemu.commons.utils.concurrent;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import javolution.text.TextBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;


























public final class RunnableStatsManager
{
  private static final Logger log = Logger.getLogger(RunnableStatsManager.class);
  
  private static final Map<Class<?>, ClassStat> classStats = new HashMap<Class<?>, ClassStat>();

  
  private static final class ClassStat
  {
    private final String className;
    private final RunnableStatsManager.MethodStat runnableStat;
    private String[] methodNames = new String[0];
    private RunnableStatsManager.MethodStat[] methodStats = new RunnableStatsManager.MethodStat[0];

    
    private ClassStat(Class<?> clazz) {
      this.className = clazz.getName().replace("com.aionemu.gameserver.", "");
      this.runnableStat = new RunnableStatsManager.MethodStat(this.className, "run()");
      
      this.methodNames = new String[] { "run()" };
      this.methodStats = new RunnableStatsManager.MethodStat[] { this.runnableStat };
      
      RunnableStatsManager.classStats.put(clazz, this);
    }

    
    private RunnableStatsManager.MethodStat getRunnableStat() {
      return this.runnableStat;
    }


    
    private RunnableStatsManager.MethodStat getMethodStat(String methodName, boolean synchronizedAlready) {
      if (methodName == "run()") {
        return this.runnableStat;
      }
      for (int i = 0; i < this.methodNames.length; i++) {
        if (this.methodNames[i].equals(methodName))
          return this.methodStats[i]; 
      } 
      if (!synchronizedAlready)
      {
        synchronized (this) {
          
          return getMethodStat(methodName, true);
        } 
      }
      
      methodName = methodName.intern();
      
      RunnableStatsManager.MethodStat methodStat = new RunnableStatsManager.MethodStat(this.className, methodName);
      
      this.methodNames = (String[])ArrayUtils.add((Object[])this.methodNames, methodName);
      this.methodStats = (RunnableStatsManager.MethodStat[])ArrayUtils.add((Object[])this.methodStats, methodStat);
      
      return methodStat;
    }
  }
  
  private static final class MethodStat
  {
    private final ReentrantLock lock = new ReentrantLock();
    
    private final String className;
    
    private final String methodName;
    private long count;
    private long total;
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;

    
    private MethodStat(String className, String methodName) {
      this.className = className;
      this.methodName = methodName;
    }

    
    private void handleStats(long runTime) {
      this.lock.lock();
      
      try {
        this.count++;
        this.total += runTime;
        this.min = Math.min(this.min, runTime);
        this.max = Math.max(this.max, runTime);
      }
      finally {
        
        this.lock.unlock();
      } 
    }
  }

  
  private static ClassStat getClassStat(Class<?> clazz, boolean synchronizedAlready) {
    ClassStat classStat = classStats.get(clazz);
    
    if (classStat != null) {
      return classStat;
    }
    if (!synchronizedAlready)
    {
      synchronized (RunnableStatsManager.class) {
        
        return getClassStat(clazz, true);
      } 
    }
    
    return new ClassStat(clazz);
  }

  
  public static void handleStats(Class<? extends Runnable> clazz, long runTime) {
    getClassStat(clazz, false).getRunnableStat().handleStats(runTime);
  }

  
  public static void handleStats(Class<?> clazz, String methodName, long runTime) {
    getClassStat(clazz, false).getMethodStat(methodName, false).handleStats(runTime);
  }
  
  public enum SortBy
  {
    AVG("average"),
    COUNT("count"),
    TOTAL("total"),
    NAME("class"),
    METHOD("method"),
    MIN("min"),
    MAX("max");


    
    private final String xmlAttributeName;


    
    private final Comparator<RunnableStatsManager.MethodStat> comparator;



    
    SortBy(String xmlAttributeName) {
      this.comparator = new Comparator<RunnableStatsManager.MethodStat>()
        {
          public int compare(RunnableStatsManager.MethodStat o1, RunnableStatsManager.MethodStat o2) {
            Comparable c1 = RunnableStatsManager.SortBy.this.getComparableValueOf(o1);
            Comparable<Comparable> c2 = RunnableStatsManager.SortBy.this.getComparableValueOf(o2);
            if (c1 instanceof Number) {
              return c2.compareTo(c1);
            }
            String s1 = (String)c1;
            String s2 = (String)c2;
            int len1 = s1.length();
            int len2 = s2.length();
            int n = Math.min(len1, len2);
            for (int k = 0; k < n; k++) {
              char ch1 = s1.charAt(k);
              char ch2 = s2.charAt(k);
              if (ch1 != ch2) {
                if (Character.isUpperCase(ch1) != Character.isUpperCase(ch2)) {
                  return ch2 - ch1;
                }
                return ch1 - ch2;
              } 
            } 
            int result = len1 - len2;
            if (result != 0) {
              return result;
            }
            switch (RunnableStatsManager.SortBy.this) {
              case METHOD:
                return RunnableStatsManager.SortBy.NAME.comparator.compare(o1, o2);
            } 
            return 0;
          }
        };
      this.xmlAttributeName = xmlAttributeName;
    }


    
    private Comparable getComparableValueOf(RunnableStatsManager.MethodStat stat) {
      switch (this) {
        case AVG:
          return Long.valueOf(stat.total / stat.count);
        case COUNT:
          return Long.valueOf(stat.count);
        case TOTAL:
          return Long.valueOf(stat.total);
        case NAME:
          return stat.className;
        case METHOD:
          return stat.methodName;
        case MIN:
          return Long.valueOf(stat.min);
        case MAX:
          return Long.valueOf(stat.max);
      } 
      throw new InternalError();
    }


    
    static {
    
    }


    
    private static final SortBy[] VALUES = values();
  }

  
  public static void dumpClassStats() {
    dumpClassStats(null);
  }


  
  public static void dumpClassStats(SortBy sortBy) {
    List<MethodStat> methodStats = new ArrayList<MethodStat>();
    
    synchronized (RunnableStatsManager.class) {
      
      for (ClassStat classStat : classStats.values()) {
        for (MethodStat methodStat : classStat.methodStats) {
          if (methodStat.count > 0L)
            methodStats.add(methodStat); 
        } 
      } 
    }  if (sortBy != null) {
      Collections.sort(methodStats, sortBy.comparator);
    }
    List<String> lines = new ArrayList<String>();
    
    lines.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
    lines.add("<entries>");
    lines.add("\t<!-- This XML contains statistics about execution times. -->");
    lines.add("\t<!-- Submitted results will help the developers to optimize the server. -->");
    
    String[][] values = new String[SortBy.VALUES.length][methodStats.size()];
    int[] maxLength = new int[SortBy.VALUES.length];
    
    for (int i = 0; i < SortBy.VALUES.length; i++) {
      
      SortBy sort = SortBy.VALUES[i];
      
      for (int j = 0; j < methodStats.size(); j++) {
        String value;
        Comparable c = sort.getComparableValueOf(methodStats.get(j));


        
        if (c instanceof Number) {
          value = NumberFormat.getInstance(Locale.ENGLISH).format(((Number)c).longValue());
        } else {
          value = String.valueOf(c);
        } 
        values[i][j] = value;
        
        maxLength[i] = Math.max(maxLength[i], value.length());
      } 
    } 
    
    for (int k = 0; k < methodStats.size(); k++) {
      
      TextBuilder tb = TextBuilder.newInstance();
      tb.append("\t<entry ");
      
      EnumSet<SortBy> set = EnumSet.allOf(SortBy.class);
      
      if (sortBy != null)
      {
        switch (sortBy) {
          
          case METHOD:
          case NAME:
            appendAttribute(tb, SortBy.NAME, values[SortBy.NAME.ordinal()][k], maxLength[SortBy.NAME.ordinal()]);
            
            set.remove(SortBy.NAME);
            
            appendAttribute(tb, SortBy.METHOD, values[SortBy.METHOD.ordinal()][k], maxLength[SortBy.METHOD.ordinal()]);
            
            set.remove(SortBy.METHOD);
            break;
          default:
            appendAttribute(tb, sortBy, values[sortBy.ordinal()][k], maxLength[sortBy.ordinal()]);
            set.remove(sortBy);
            break;
        } 
      
      }
      for (SortBy sort : SortBy.VALUES) {
        if (set.contains(sort))
          appendAttribute(tb, sort, values[sort.ordinal()][k], maxLength[sort.ordinal()]); 
      } 
      tb.append("/>");
      
      lines.add(tb.toString());
      TextBuilder.recycle(tb);
    } 
    
    lines.add("</entries>");
    
    PrintStream ps = null;
    
    try {
      ps = new PrintStream("log/MethodStats-" + System.currentTimeMillis() + ".log");
      
      for (String line : lines) {
        ps.println(line);
      }
    } catch (Exception e) {
      
      log.warn("", e);
    }
    finally {
      
      IOUtils.closeQuietly(ps);
    } 
  }

  
  private static void appendAttribute(TextBuilder tb, SortBy sortBy, String value, int fillTo) {
    tb.append(sortBy.xmlAttributeName);
    tb.append("=");
    
    if (sortBy != SortBy.NAME && sortBy != SortBy.METHOD)
      for (int i = value.length(); i < fillTo; i++) {
        tb.append(" ");
      } 
    tb.append("\"");
    tb.append(value);
    tb.append("\" ");
    
    if (sortBy == SortBy.NAME || sortBy == SortBy.METHOD)
      for (int i = value.length(); i < fillTo; i++)
        tb.append(" ");  
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\concurrent\RunnableStatsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
