/*    */ package com.aionemu.gameserver.dao;
/*    */ 
/*    */ import com.aionemu.commons.database.dao.DAO;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*    */ 
/*    */ public abstract class PlayerSettingsDAO
/*    */   implements DAO
/*    */ {
/*    */   public final String getClassName() {
/* 35 */     return PlayerSettingsDAO.class.getName();
/*    */   }
/*    */   
/*    */   public abstract void saveSettings(Player paramPlayer);
/*    */   
/*    */   public abstract void loadSettings(Player paramPlayer);
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\PlayerSettingsDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */