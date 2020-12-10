/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
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
/*    */ public class CM_SHOW_DIALOG
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int targetObjectId;
/*    */   
/*    */   public CM_SHOW_DIALOG(int opcode) {
/* 43 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 52 */     this.targetObjectId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 61 */     AionObject targetObject = World.getInstance().findAionObject(this.targetObjectId);
/* 62 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 64 */     if (targetObject == null || player == null) {
/*    */       return;
/*    */     }
/* 67 */     if (targetObject instanceof Npc) {
/*    */       
/* 69 */       ((Npc)targetObject).setTarget((VisibleObject)player);
/*    */ 
/*    */       
/* 72 */       PacketSendUtility.broadcastPacket((VisibleObject)targetObject, (AionServerPacket)new SM_LOOKATOBJECT((VisibleObject)targetObject));
/*    */ 
/*    */       
/* 75 */       ((Npc)targetObject).getController().onDialogRequest(player);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SHOW_DIALOG.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */