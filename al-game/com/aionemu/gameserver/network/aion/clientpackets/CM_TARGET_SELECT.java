/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_SELECTED;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_UPDATE;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CM_TARGET_SELECT
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int targetObjectId;
/*    */   private int type;
/*    */   
/*    */   public CM_TARGET_SELECT(int opcode) {
/* 50 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 61 */     this.targetObjectId = readD();
/* 62 */     this.type = readC();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 71 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 72 */     if (player == null) {
/*    */       return;
/*    */     }
/* 75 */     AionObject obj = World.getInstance().findAionObject(this.targetObjectId);
/* 76 */     if (obj != null && obj instanceof VisibleObject) {
/*    */       
/* 78 */       if (this.type == 1)
/*    */       {
/* 80 */         if (((VisibleObject)obj).getTarget() == null)
/*    */           return; 
/* 82 */         player.setTarget(((VisibleObject)obj).getTarget());
/*    */       }
/*    */       else
/*    */       {
/* 86 */         player.setTarget((VisibleObject)obj);
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 91 */       player.setTarget(null);
/*    */     } 
/* 93 */     sendPacket((AionServerPacket)new SM_TARGET_SELECTED(player));
/* 94 */     PacketSendUtility.broadcastPacket((VisibleObject)player, (AionServerPacket)new SM_TARGET_UPDATE(player));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_TARGET_SELECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */