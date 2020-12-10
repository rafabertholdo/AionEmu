/*    */ package com.aionemu.gameserver.dao;
/*    */ 
/*    */ import com.aionemu.commons.database.dao.DAO;
/*    */ import com.aionemu.gameserver.model.siege.SiegeLocation;
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
/*    */ public abstract class SiegeDAO
/*    */   implements DAO
/*    */ {
/*    */   public final String getClassName() {
/* 33 */     return SiegeDAO.class.getName();
/*    */   }
/*    */   
/*    */   public abstract boolean loadSiegeLocations(FastMap<Integer, SiegeLocation> paramFastMap);
/*    */   
/*    */   public abstract boolean updateSiegeLocation(SiegeLocation paramSiegeLocation);
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\SiegeDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */