/*    */ package com.aionemu.gameserver.dao;
/*    */ 
/*    */ import com.aionemu.commons.database.dao.DAO;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.QuestStateList;
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
/*    */ public abstract class PlayerQuestListDAO
/*    */   implements DAO
/*    */ {
/*    */   public String getClassName() {
/* 35 */     return PlayerQuestListDAO.class.getName();
/*    */   }
/*    */   
/*    */   public abstract QuestStateList load(Player paramPlayer);
/*    */   
/*    */   public abstract void store(Player paramPlayer);
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\PlayerQuestListDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */