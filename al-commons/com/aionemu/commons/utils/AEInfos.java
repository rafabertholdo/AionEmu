package com.aionemu.commons.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;























public class AEInfos
{
  private static final Logger log = Logger.getLogger(AEInfos.class);




  
  public static void printSection(String s) {
    s = "=[ " + s + " ]";
    
    while (s.length() < 79) {
      s = "-" + s;
    }
    System.out.println(s);
  }

  
  public static String[] getMemoryInfo() {
    double max = (Runtime.getRuntime().maxMemory() / 1024L);
    double allocated = (Runtime.getRuntime().totalMemory() / 1024L);
    double nonAllocated = max - allocated;
    double cached = (Runtime.getRuntime().freeMemory() / 1024L);
    double used = allocated - cached;
    double useable = max - used;
    DecimalFormat df = new DecimalFormat(" (0.0000'%')");
    DecimalFormat df2 = new DecimalFormat(" # 'KB'");
    return new String[] { "+----", "| Global Memory Informations at " + getRealTime().toString() + ":", "|    |", "| Allowed Memory:" + df2.format(max), "|    |= Allocated Memory:" + df2.format(allocated) + df.format(allocated / max * 100.0D), "|    |= Non-Allocated Memory:" + df2.format(nonAllocated) + df.format(nonAllocated / max * 100.0D), "| Allocated Memory:" + df2.format(allocated), "|    |= Used Memory:" + df2.format(used) + df.format(used / max * 100.0D), "|    |= Unused (cached) Memory:" + df2.format(cached) + df.format(cached / max * 100.0D), "| Useable Memory:" + df2.format(useable) + df.format(useable / max * 100.0D), "+----" };
  }













  
  public static String[] getCPUInfo() {
    return new String[] { "Avaible CPU(s): " + Runtime.getRuntime().availableProcessors(), "Processor(s) Identifier: " + System.getenv("PROCESSOR_IDENTIFIER") };
  }




  
  public static String[] getOSInfo() {
    return new String[] { "OS: " + System.getProperty("os.name") + " Build: " + System.getProperty("os.version"), "OS Arch: " + System.getProperty("os.arch") };
  }




  
  public static String[] getJREInfo() {
    return new String[] { "Java Platform Information", "Java Runtime  Name: " + System.getProperty("java.runtime.name"), "Java Version: " + System.getProperty("java.version"), "Java Class Version: " + System.getProperty("java.class.version") };
  }






  
  public static String[] getJVMInfo() {
    return new String[] { "Virtual Machine Information (JVM)", "JVM Name: " + System.getProperty("java.vm.name"), "JVM installation directory: " + System.getProperty("java.home"), "JVM version: " + System.getProperty("java.vm.version"), "JVM Vendor: " + System.getProperty("java.vm.vendor"), "JVM Info: " + System.getProperty("java.vm.info") };
  }








  
  public static String getRealTime() {
    SimpleDateFormat String = new SimpleDateFormat("H:mm:ss");
    return String.format(new Date());
  }

  
  public static void printMemoryInfo() {
    for (String line : getMemoryInfo()) {
      log.info(line);
    }
  }
  
  public static void printCPUInfo() {
    for (String line : getCPUInfo()) {
      log.info(line);
    }
  }
  
  public static void printOSInfo() {
    for (String line : getOSInfo()) {
      log.info(line);
    }
  }
  
  public static void printJREInfo() {
    for (String line : getJREInfo()) {
      log.info(line);
    }
  }
  
  public static void printJVMInfo() {
    for (String line : getJVMInfo()) {
      log.info(line);
    }
  }
  
  public static void printRealTime() {
    log.info(getRealTime().toString());
  }

  
  public static void printAllInfos() {
    printOSInfo();
    printCPUInfo();
    printJREInfo();
    printJVMInfo();
    printMemoryInfo();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\AEInfos.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
