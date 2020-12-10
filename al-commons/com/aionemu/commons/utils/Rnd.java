package com.aionemu.commons.utils;





















public class Rnd
{
  private static final MTRandom rnd = new MTRandom();





  
  public static float get() {
    return rnd.nextFloat();
  }








  
  public static int get(int n) {
    return (int)Math.floor(rnd.nextDouble() * n);
  }







  
  public static int get(int min, int max) {
    return min + (int)Math.floor(rnd.nextDouble() * (max - min + 1));
  }





  
  public static int nextInt(int n) {
    return (int)Math.floor(rnd.nextDouble() * n);
  }




  
  public static int nextInt() {
    return rnd.nextInt();
  }




  
  public static double nextDouble() {
    return rnd.nextDouble();
  }




  
  public static double nextGaussian() {
    return rnd.nextGaussian();
  }




  
  public static boolean nextBoolean() {
    return rnd.nextBoolean();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\Rnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
