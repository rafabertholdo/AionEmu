/*    */ package com.aionemu.gameserver.model.broker;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.BrokerItem;
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
/*    */ public class BrokerPlayerCache
/*    */ {
/* 27 */   private BrokerItem[] brokerListCache = new BrokerItem[0];
/*    */   
/*    */   private int brokerMaskCache;
/*    */   
/*    */   private int brokerSoftTypeCache;
/*    */   
/*    */   private int brokerStartPageCache;
/*    */ 
/*    */   
/*    */   public BrokerItem[] getBrokerListCache() {
/* 37 */     return this.brokerListCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBrokerListCache(BrokerItem[] brokerListCache) {
/* 46 */     this.brokerListCache = brokerListCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBrokerMaskCache() {
/* 54 */     return this.brokerMaskCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBrokerMaskCache(int brokerMaskCache) {
/* 63 */     this.brokerMaskCache = brokerMaskCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBrokerSortTypeCache() {
/* 71 */     return this.brokerSoftTypeCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBrokerSortTypeCache(int brokerSoftTypeCache) {
/* 80 */     this.brokerSoftTypeCache = brokerSoftTypeCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBrokerStartPageCache() {
/* 88 */     return this.brokerStartPageCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBrokerStartPageCache(int brokerStartPageCache) {
/* 97 */     this.brokerStartPageCache = brokerStartPageCache;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\broker\BrokerPlayerCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */