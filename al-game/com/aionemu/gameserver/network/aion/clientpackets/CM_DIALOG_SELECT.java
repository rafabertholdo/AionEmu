/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.services.ClassChangeService;
/*    */ import com.aionemu.gameserver.world.World;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CM_DIALOG_SELECT
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int targetObjectId;
/*    */   private int dialogId;
/*    */   private int unk1;
/*    */   private int lastPage;
/*    */   private int questId;
/* 49 */   private static final Logger log = Logger.getLogger(CM_DIALOG_SELECT.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CM_DIALOG_SELECT(int opcode) {
/* 57 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 66 */     this.targetObjectId = readD();
/* 67 */     this.dialogId = readH();
/* 68 */     this.unk1 = readH();
/* 69 */     this.lastPage = readH();
/* 70 */     this.questId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 79 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 80 */     if (player == null) {
/*    */       return;
/*    */     }
/* 83 */     if (this.targetObjectId == 0) {
/*    */       
/* 85 */       if (QuestEngine.getInstance().onDialog(new QuestEnv(null, player, Integer.valueOf(this.questId), Integer.valueOf(this.dialogId)))) {
/*    */         return;
/*    */       }
/* 88 */       ClassChangeService.changeClassToSelection(player, this.dialogId);
/*    */       
/*    */       return;
/*    */     } 
/* 92 */     AionObject object = World.getInstance().findAionObject(this.targetObjectId);
/*    */     
/* 94 */     if (object instanceof Creature) {
/*    */       
/* 96 */       Creature creature = (Creature)object;
/* 97 */       creature.getController().onDialogSelect(this.dialogId, player, this.questId);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_DIALOG_SELECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */