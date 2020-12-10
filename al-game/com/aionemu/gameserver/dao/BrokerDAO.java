/*    */ package com.aionemu.gameserver.dao;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.BrokerItem;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BrokerDAO
/*    */   implements IDFactoryAwareDAO
/*    */ {
/*    */   public abstract List<BrokerItem> loadBroker();
/*    */   
/*    */   public abstract boolean store(BrokerItem paramBrokerItem);
/*    */   
/*    */   public final String getClassName() {
/* 16 */     return BrokerDAO.class.getName();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\BrokerDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */