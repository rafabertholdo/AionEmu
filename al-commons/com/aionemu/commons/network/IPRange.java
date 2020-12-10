package com.aionemu.commons.network;

import java.util.Arrays;














































public class IPRange
{
  private final long min;
  private final long max;
  private final byte[] address;
  
  public IPRange(String min, String max, String address) {
    this.min = toLong(toByteArray(min));
    this.max = toLong(toByteArray(max));
    this.address = toByteArray(address);
  }











  
  public IPRange(byte[] min, byte[] max, byte[] address) {
    this.min = toLong(min);
    this.max = toLong(max);
    this.address = address;
  }








  
  public boolean isInRange(String address) {
    long addr = toLong(toByteArray(address));
    return (addr >= this.min && addr <= this.max);
  }






  
  public byte[] getAddress() {
    return this.address;
  }






  
  public byte[] getMinAsByteArray() {
    return toBytes(this.min);
  }






  
  public byte[] getMaxAsByteArray() {
    return toBytes(this.max);
  }








  
  private static long toLong(byte[] bytes) {
    long result = 0L;
    result += (bytes[3] & 0xFF);
    result += ((bytes[2] & 0xFF) << 8);
    result += ((bytes[1] & 0xFF) << 16);
    result += (bytes[0] << 24);
    return result & 0xFFFFFFFFL;
  }








  
  private static byte[] toBytes(long val) {
    byte[] result = new byte[4];
    result[3] = (byte)(int)(val & 0xFFL);
    result[2] = (byte)(int)(val >> 8L & 0xFFL);
    result[1] = (byte)(int)(val >> 16L & 0xFFL);
    result[0] = (byte)(int)(val >> 24L & 0xFFL);
    return result;
  }








  
  public static byte[] toByteArray(String address) {
    byte[] result = new byte[4];
    String[] strings = address.split("\\.");
    for (int i = 0, n = strings.length; i < n; i++)
    {
      result[i] = (byte)Integer.parseInt(strings[i]);
    }
    
    return result;
  }









  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof IPRange)) {
      return false;
    }
    IPRange ipRange = (IPRange)o;
    return (this.max == ipRange.max && this.min == ipRange.min && Arrays.equals(this.address, ipRange.address));
  }







  
  public int hashCode() {
    int result = (int)(this.min ^ this.min >>> 32L);
    result = 31 * result + (int)(this.max ^ this.max >>> 32L);
    result = 31 * result + Arrays.hashCode(this.address);
    return result;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\IPRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
