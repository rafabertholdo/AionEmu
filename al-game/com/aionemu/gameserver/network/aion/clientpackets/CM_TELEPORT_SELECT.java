/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.templates.teleport.TelelocationTemplate;
/*    */ import com.aionemu.gameserver.model.templates.teleport.TeleportType;
/*    */ import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.TeleportService;
/*    */ import com.aionemu.gameserver.world.World;
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
/*    */ public class CM_TELEPORT_SELECT
/*    */   extends AionClientPacket
/*    */ {
/*    */   public int targetObjectId;
/*    */   public int locId;
/*    */   public TelelocationTemplate _tele;
/*    */   private TeleporterTemplate teleport;
/*    */   
/*    */   public CM_TELEPORT_SELECT(int opcode) {
/* 46 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 56 */     this.targetObjectId = readD();
/* 57 */     this.locId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 66 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 68 */     Npc npc = (Npc)World.getInstance().findAionObject(this.targetObjectId);
/*    */     
/* 70 */     if (activePlayer == null || activePlayer.getLifeStats().isAlreadyDead()) {
/*    */       return;
/*    */     }
/* 73 */     this.teleport = DataManager.TELEPORTER_DATA.getTeleporterTemplate(npc.getNpcId());
/*    */     
/* 75 */     switch (this.teleport.getType()) {
/*    */       
/*    */       case FLIGHT:
/* 78 */         TeleportService.flightTeleport(this.teleport, this.locId, activePlayer);
/*    */         break;
/*    */       case REGULAR:
/* 81 */         TeleportService.regularTeleport(this.teleport, this.locId, activePlayer);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_TELEPORT_SELECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */