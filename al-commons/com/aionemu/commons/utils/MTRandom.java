package com.aionemu.commons.utils;

import java.util.Random;
























































public class MTRandom
  extends Random
{
  private static final long serialVersionUID = -515082678588212038L;
  private static final int UPPER_MASK = -2147483648;
  private static final int LOWER_MASK = 2147483647;
  private static final int N = 624;
  private static final int M = 397;
  private static final int[] MAGIC = new int[] { 0, -1727483681 };


  
  private static final int MAGIC_FACTOR1 = 1812433253;


  
  private static final int MAGIC_FACTOR2 = 1664525;

  
  private static final int MAGIC_FACTOR3 = 1566083941;

  
  private static final int MAGIC_MASK1 = -1658038656;

  
  private static final int MAGIC_MASK2 = -272236544;

  
  private static final int MAGIC_SEED = 19650218;

  
  private static final long DEFAULT_SEED = 5489L;

  
  private transient int[] mt;

  
  private transient int mti;

  
  private transient boolean compat = false;

  
  private transient int[] ibuf;


  
  public MTRandom() {}


  
  public MTRandom(boolean compatible) {
    super(0L);
    this.compat = compatible;
    setSeed(this.compat ? 5489L : System.currentTimeMillis());
  }








  
  public MTRandom(long seed) {
    super(seed);
  }












  
  public MTRandom(byte[] buf) {
    super(0L);
    setSeed(buf);
  }












  
  public MTRandom(int[] buf) {
    super(0L);
    setSeed(buf);
  }














  
  private void setSeed(int seed) {
    if (this.mt == null) {
      this.mt = new int[624];
    }
    
    this.mt[0] = seed;
    for (this.mti = 1; this.mti < 624; this.mti++)
    {
      this.mt[this.mti] = 1812433253 * (this.mt[this.mti - 1] ^ this.mt[this.mti - 1] >>> 30) + this.mti;
    }
  }















  
  public final synchronized void setSeed(long seed) {
    if (this.compat) {
      
      setSeed((int)seed);


    
    }
    else {



      
      if (this.ibuf == null) {
        this.ibuf = new int[2];
      }
      this.ibuf[0] = (int)seed;
      this.ibuf[1] = (int)(seed >>> 32L);
      setSeed(this.ibuf);
    } 
  }
















  
  public final void setSeed(byte[] buf) {
    setSeed(pack(buf));
  }












  
  public final synchronized void setSeed(int[] buf) {
    int length = buf.length;
    if (length == 0) {
      throw new IllegalArgumentException("Seed buffer may not be empty");
    }
    int i = 1, j = 0, k = (624 > length) ? 624 : length;
    setSeed(19650218);
    for (; k > 0; k--) {
      
      this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1664525) + buf[j] + j;
      i++;
      j++;
      if (i >= 624) {
        
        this.mt[0] = this.mt[623];
        i = 1;
      } 
      if (j >= length)
        j = 0; 
    } 
    for (k = 623; k > 0; k--) {
      
      this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1566083941) - i;
      i++;
      if (i >= 624) {
        
        this.mt[0] = this.mt[623];
        i = 1;
      } 
    } 
    this.mt[0] = Integer.MIN_VALUE;
  }

































  
  protected final synchronized int next(int bits) {
    if (this.mti >= 624) {
      int kk;








      
      for (kk = 0; kk < 227; kk++) {
        
        int j = this.mt[kk] & Integer.MIN_VALUE | this.mt[kk + 1] & Integer.MAX_VALUE;
        this.mt[kk] = this.mt[kk + 397] ^ j >>> 1 ^ MAGIC[j & 0x1];
      } 
      for (; kk < 623; kk++) {
        
        int j = this.mt[kk] & Integer.MIN_VALUE | this.mt[kk + 1] & Integer.MAX_VALUE;
        this.mt[kk] = this.mt[kk + -227] ^ j >>> 1 ^ MAGIC[j & 0x1];
      } 
      int i = this.mt[623] & Integer.MIN_VALUE | this.mt[0] & Integer.MAX_VALUE;
      this.mt[623] = this.mt[396] ^ i >>> 1 ^ MAGIC[i & 0x1];
      
      this.mti = 0;
    } 
    
    int y = this.mt[this.mti++];

    
    y ^= y >>> 11;
    y ^= y << 7 & 0x9D2C5680;
    y ^= y << 15 & 0xEFC60000;
    y ^= y >>> 18;
    
    return y >>> 32 - bits;
  }




































  
  public static int[] pack(byte[] buf) {
    int blen = buf.length, ilen = buf.length + 3 >>> 2;
    int[] ibuf = new int[ilen];
    for (int n = 0; n < ilen; n++) {
      
      int m = n + 1 << 2;
      if (m > blen)
        m = blen;  int k;
      for (k = buf[--m] & 0xFF; (m & 0x3) != 0; k = k << 8 | buf[--m] & 0xFF);
      
      ibuf[n] = k;
    } 
    return ibuf;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\MTRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
