/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.legion.LegionHistory;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_TABS;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class CM_LEGION_TABS
/*    */   extends AionClientPacket
/*    */ {
/* 36 */   private static final Logger log = Logger.getLogger(CM_LEGION_TABS.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int page;
/*    */ 
/*    */ 
/*    */   
/*    */   private int tab;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CM_LEGION_TABS(int opcode) {
/* 51 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 60 */     this.page = readD();
/* 61 */     this.tab = readC();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 70 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 71 */     Collection<LegionHistory> history = activePlayer.getLegion().getLegionHistory();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 76 */     if (this.page > 3) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 82 */     if (history.size() < this.page * 8) {
/*    */       return;
/*    */     }
/* 85 */     switch (this.tab) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       case 0:
/* 91 */         log.debug("Requested History Tab Page: " + this.page);
/* 92 */         if (!history.isEmpty()) {
/* 93 */           PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_LEGION_TABS(history, this.page));
/*    */         }
/*    */         break;
/*    */ 
/*    */       
/*    */       case 1:
/* 99 */         log.debug("Requested Reward Tab Page: " + this.page);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_LEGION_TABS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */