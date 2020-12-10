package com.aionemu.commons.utils;


































public class NetworkUtils
{
  public static boolean checkIPMatching(String pattern, String address) {
    if (pattern.equals("*.*.*.*") || pattern.equals("*")) {
      return true;
    }
    String[] mask = pattern.split("\\.");
    String[] ip_address = address.split("\\.");
    for (int i = 0; i < mask.length; i++) {
      
      if (!mask[i].equals("*") && !mask[i].equals(ip_address[i]))
      {
        if (mask[i].contains("-")) {
          
          byte min = Byte.parseByte(mask[i].split("-")[0]);
          byte max = Byte.parseByte(mask[i].split("-")[1]);
          byte ip = Byte.parseByte(ip_address[i]);
          if (ip < min || ip > max) {
            return false;
          }
        } else {
          return false;
        }  } 
    }  return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\NetworkUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
