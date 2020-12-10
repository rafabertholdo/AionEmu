/*    */ package com.aionemu.gameserver.controllers.attack;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javolution.util.FastMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KillList
/*    */ {
/*    */   private static final long DAY_IN_MILLISECONDS = 86400000L;
/* 35 */   private FastMap<Integer, List<Long>> killList = new FastMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getKillsFor(int victimId) {
/* 45 */     List<Long> killTimes = (List<Long>)this.killList.get(Integer.valueOf(victimId));
/*    */     
/* 47 */     if (killTimes == null) {
/* 48 */       return 0;
/*    */     }
/* 50 */     long now = System.currentTimeMillis();
/* 51 */     int killCount = 0;
/*    */     
/* 53 */     for (Iterator<Long> i = killTimes.iterator(); i.hasNext(); ) {
/*    */       
/* 55 */       if (now - ((Long)i.next()).longValue() > 86400000L) {
/*    */         
/* 57 */         i.remove();
/*    */         
/*    */         continue;
/*    */       } 
/* 61 */       killCount++;
/*    */     } 
/*    */ 
/*    */     
/* 65 */     return killCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addKillFor(int victimId) {
/* 73 */     List<Long> killTimes = (List<Long>)this.killList.get(Integer.valueOf(victimId));
/* 74 */     if (killTimes == null) {
/*    */       
/* 76 */       killTimes = new ArrayList<Long>();
/* 77 */       this.killList.put(Integer.valueOf(victimId), killTimes);
/*    */     } 
/*    */     
/* 80 */     killTimes.add(Long.valueOf(System.currentTimeMillis()));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\KillList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */