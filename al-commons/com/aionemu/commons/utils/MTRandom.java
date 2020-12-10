/*     */ package com.aionemu.commons.utils;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class MTRandom
/*     */   extends Random
/*     */ {
/*     */   private static final long serialVersionUID = -515082678588212038L;
/*     */   private static final int UPPER_MASK = -2147483648;
/*     */   private static final int LOWER_MASK = 2147483647;
/*     */   private static final int N = 624;
/*     */   private static final int M = 397;
/*  68 */   private static final int[] MAGIC = new int[] { 0, -1727483681 };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAGIC_FACTOR1 = 1812433253;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAGIC_FACTOR2 = 1664525;
/*     */ 
/*     */   
/*     */   private static final int MAGIC_FACTOR3 = 1566083941;
/*     */ 
/*     */   
/*     */   private static final int MAGIC_MASK1 = -1658038656;
/*     */ 
/*     */   
/*     */   private static final int MAGIC_MASK2 = -272236544;
/*     */ 
/*     */   
/*     */   private static final int MAGIC_SEED = 19650218;
/*     */ 
/*     */   
/*     */   private static final long DEFAULT_SEED = 5489L;
/*     */ 
/*     */   
/*     */   private transient int[] mt;
/*     */ 
/*     */   
/*     */   private transient int mti;
/*     */ 
/*     */   
/*     */   private transient boolean compat = false;
/*     */ 
/*     */   
/*     */   private transient int[] ibuf;
/*     */ 
/*     */ 
/*     */   
/*     */   public MTRandom() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public MTRandom(boolean compatible) {
/* 112 */     super(0L);
/* 113 */     this.compat = compatible;
/* 114 */     setSeed(this.compat ? 5489L : System.currentTimeMillis());
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
/*     */   public MTRandom(long seed) {
/* 126 */     super(seed);
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
/*     */   public MTRandom(byte[] buf) {
/* 142 */     super(0L);
/* 143 */     setSeed(buf);
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
/*     */   public MTRandom(int[] buf) {
/* 159 */     super(0L);
/* 160 */     setSeed(buf);
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
/*     */   
/*     */   private void setSeed(int seed) {
/* 178 */     if (this.mt == null) {
/* 179 */       this.mt = new int[624];
/*     */     }
/*     */     
/* 182 */     this.mt[0] = seed;
/* 183 */     for (this.mti = 1; this.mti < 624; this.mti++)
/*     */     {
/* 185 */       this.mt[this.mti] = 1812433253 * (this.mt[this.mti - 1] ^ this.mt[this.mti - 1] >>> 30) + this.mti;
/*     */     }
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
/*     */ 
/*     */   
/*     */   public final synchronized void setSeed(long seed) {
/* 205 */     if (this.compat) {
/*     */       
/* 207 */       setSeed((int)seed);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       if (this.ibuf == null) {
/* 218 */         this.ibuf = new int[2];
/*     */       }
/* 220 */       this.ibuf[0] = (int)seed;
/* 221 */       this.ibuf[1] = (int)(seed >>> 32L);
/* 222 */       setSeed(this.ibuf);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSeed(byte[] buf) {
/* 243 */     setSeed(pack(buf));
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
/*     */   public final synchronized void setSeed(int[] buf) {
/* 259 */     int length = buf.length;
/* 260 */     if (length == 0) {
/* 261 */       throw new IllegalArgumentException("Seed buffer may not be empty");
/*     */     }
/* 263 */     int i = 1, j = 0, k = (624 > length) ? 624 : length;
/* 264 */     setSeed(19650218);
/* 265 */     for (; k > 0; k--) {
/*     */       
/* 267 */       this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1664525) + buf[j] + j;
/* 268 */       i++;
/* 269 */       j++;
/* 270 */       if (i >= 624) {
/*     */         
/* 272 */         this.mt[0] = this.mt[623];
/* 273 */         i = 1;
/*     */       } 
/* 275 */       if (j >= length)
/* 276 */         j = 0; 
/*     */     } 
/* 278 */     for (k = 623; k > 0; k--) {
/*     */       
/* 280 */       this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1566083941) - i;
/* 281 */       i++;
/* 282 */       if (i >= 624) {
/*     */         
/* 284 */         this.mt[0] = this.mt[623];
/* 285 */         i = 1;
/*     */       } 
/*     */     } 
/* 288 */     this.mt[0] = Integer.MIN_VALUE;
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
/*     */   protected final synchronized int next(int bits) {
/* 325 */     if (this.mti >= 624) {
/*     */       int kk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 336 */       for (kk = 0; kk < 227; kk++) {
/*     */         
/* 338 */         int j = this.mt[kk] & Integer.MIN_VALUE | this.mt[kk + 1] & Integer.MAX_VALUE;
/* 339 */         this.mt[kk] = this.mt[kk + 397] ^ j >>> 1 ^ MAGIC[j & 0x1];
/*     */       } 
/* 341 */       for (; kk < 623; kk++) {
/*     */         
/* 343 */         int j = this.mt[kk] & Integer.MIN_VALUE | this.mt[kk + 1] & Integer.MAX_VALUE;
/* 344 */         this.mt[kk] = this.mt[kk + -227] ^ j >>> 1 ^ MAGIC[j & 0x1];
/*     */       } 
/* 346 */       int i = this.mt[623] & Integer.MIN_VALUE | this.mt[0] & Integer.MAX_VALUE;
/* 347 */       this.mt[623] = this.mt[396] ^ i >>> 1 ^ MAGIC[i & 0x1];
/*     */       
/* 349 */       this.mti = 0;
/*     */     } 
/*     */     
/* 352 */     int y = this.mt[this.mti++];
/*     */ 
/*     */     
/* 355 */     y ^= y >>> 11;
/* 356 */     y ^= y << 7 & 0x9D2C5680;
/* 357 */     y ^= y << 15 & 0xEFC60000;
/* 358 */     y ^= y >>> 18;
/*     */     
/* 360 */     return y >>> 32 - bits;
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
/*     */   public static int[] pack(byte[] buf) {
/* 400 */     int blen = buf.length, ilen = buf.length + 3 >>> 2;
/* 401 */     int[] ibuf = new int[ilen];
/* 402 */     for (int n = 0; n < ilen; n++) {
/*     */       
/* 404 */       int m = n + 1 << 2;
/* 405 */       if (m > blen)
/* 406 */         m = blen;  int k;
/* 407 */       for (k = buf[--m] & 0xFF; (m & 0x3) != 0; k = k << 8 | buf[--m] & 0xFF);
/*     */       
/* 409 */       ibuf[n] = k;
/*     */     } 
/* 411 */     return ibuf;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\MTRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */