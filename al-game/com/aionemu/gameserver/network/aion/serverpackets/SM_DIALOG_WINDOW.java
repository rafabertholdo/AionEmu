/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class SM_DIALOG_WINDOW
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int targetObjectId;
/*    */   private int dialogID;
/* 33 */   private int questId = 0;
/*    */ 
/*    */   
/*    */   public SM_DIALOG_WINDOW(int targetObjectId, int dlgID) {
/* 37 */     this.targetObjectId = targetObjectId;
/* 38 */     this.dialogID = dlgID;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_DIALOG_WINDOW(int targetObjectId, int dlgID, int questId) {
/* 43 */     this.targetObjectId = targetObjectId;
/* 44 */     this.dialogID = dlgID;
/* 45 */     this.questId = questId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 54 */     writeD(buf, this.targetObjectId);
/* 55 */     writeH(buf, this.dialogID);
/* 56 */     writeD(buf, this.questId);
/* 57 */     writeH(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DIALOG_WINDOW.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */