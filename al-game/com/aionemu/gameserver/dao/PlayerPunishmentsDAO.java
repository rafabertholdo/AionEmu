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
/*    */ public abstract class PlayerPunishmentsDAO
/*    */   implements DAO
/*    */ {
/*    */   public final String getClassName() {
/* 31 */     return PlayerPunishmentsDAO.class.getName();
/*    */   }
/*    */   
/*    */   public abstract void loadPlayerPunishments(Player paramPlayer);
/*    */   
/*    */   public abstract void storePlayerPunishments(Player paramPlayer);
/*    */   
/*    */   public abstract void punishPlayer(Player paramPlayer, int paramInt);
/*    */   
/*    */   public abstract void unpunishPlayer(Player paramPlayer);
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\PlayerPunishmentsDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */