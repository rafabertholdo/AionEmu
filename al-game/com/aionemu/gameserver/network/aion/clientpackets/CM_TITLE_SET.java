/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.listeners.TitleChangeListener;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_SET;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_UPDATE;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ public class CM_TITLE_SET
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int titleId;
/*    */   
/*    */   public CM_TITLE_SET(int opcode) {
/* 44 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 53 */     this.titleId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 62 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 63 */     sendPacket((AionServerPacket)new SM_TITLE_SET(this.titleId));
/* 64 */     PacketSendUtility.broadcastPacket((VisibleObject)player, (AionServerPacket)new SM_TITLE_UPDATE(player, this.titleId));
/* 65 */     if (player.getCommonData().getTitleId() > 0)
/*    */     {
/* 67 */       if (player.getGameStats() != null)
/*    */       {
/* 69 */         TitleChangeListener.onTitleChange((CreatureGameStats)player.getGameStats(), player.getCommonData().getTitleId(), false);
/*    */       }
/*    */     }
/* 72 */     player.getCommonData().setTitleId(this.titleId);
/* 73 */     if (player.getGameStats() != null)
/*    */     {
/* 75 */       TitleChangeListener.onTitleChange((CreatureGameStats)player.getGameStats(), this.titleId, true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_TITLE_SET.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */