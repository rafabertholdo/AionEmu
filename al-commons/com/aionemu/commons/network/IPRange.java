/*     */ package com.aionemu.commons.network;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public class IPRange
/*     */ {
/*     */   private final long min;
/*     */   private final long max;
/*     */   private final byte[] address;
/*     */   
/*     */   public IPRange(String min, String max, String address) {
/*  57 */     this.min = toLong(toByteArray(min));
/*  58 */     this.max = toLong(toByteArray(max));
/*  59 */     this.address = toByteArray(address);
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
/*     */   public IPRange(byte[] min, byte[] max, byte[] address) {
/*  74 */     this.min = toLong(min);
/*  75 */     this.max = toLong(max);
/*  76 */     this.address = address;
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
/*     */   public boolean isInRange(String address) {
/*  88 */     long addr = toLong(toByteArray(address));
/*  89 */     return (addr >= this.min && addr <= this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getAddress() {
/*  99 */     return this.address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getMinAsByteArray() {
/* 109 */     return toBytes(this.min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getMaxAsByteArray() {
/* 119 */     return toBytes(this.max);
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
/*     */   private static long toLong(byte[] bytes) {
/* 131 */     long result = 0L;
/* 132 */     result += (bytes[3] & 0xFF);
/* 133 */     result += ((bytes[2] & 0xFF) << 8);
/* 134 */     result += ((bytes[1] & 0xFF) << 16);
/* 135 */     result += (bytes[0] << 24);
/* 136 */     return result & 0xFFFFFFFFL;
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
/*     */   private static byte[] toBytes(long val) {
/* 148 */     byte[] result = new byte[4];
/* 149 */     result[3] = (byte)(int)(val & 0xFFL);
/* 150 */     result[2] = (byte)(int)(val >> 8L & 0xFFL);
/* 151 */     result[1] = (byte)(int)(val >> 16L & 0xFFL);
/* 152 */     result[0] = (byte)(int)(val >> 24L & 0xFFL);
/* 153 */     return result;
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
/*     */   public static byte[] toByteArray(String address) {
/* 165 */     byte[] result = new byte[4];
/* 166 */     String[] strings = address.split("\\.");
/* 167 */     for (int i = 0, n = strings.length; i < n; i++)
/*     */     {
/* 169 */       result[i] = (byte)Integer.parseInt(strings[i]);
/*     */     }
/*     */     
/* 172 */     return result;
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
/*     */   public boolean equals(Object o) {
/* 185 */     if (this == o)
/* 186 */       return true; 
/* 187 */     if (!(o instanceof IPRange)) {
/* 188 */       return false;
/*     */     }
/* 190 */     IPRange ipRange = (IPRange)o;
/* 191 */     return (this.max == ipRange.max && this.min == ipRange.min && Arrays.equals(this.address, ipRange.address));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 202 */     int result = (int)(this.min ^ this.min >>> 32L);
/* 203 */     result = 31 * result + (int)(this.max ^ this.max >>> 32L);
/* 204 */     result = 31 * result + Arrays.hashCode(this.address);
/* 205 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\IPRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */