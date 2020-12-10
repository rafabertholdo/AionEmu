/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
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
/*    */ 
/*    */ public class SM_LOOKATOBJECT
/*    */   extends AionServerPacket
/*    */ {
/*    */   private VisibleObject visibleObject;
/*    */   private int targetObjectId;
/*    */   private int heading;
/*    */   
/*    */   public SM_LOOKATOBJECT(VisibleObject visibleObject) {
/* 38 */     this.visibleObject = visibleObject;
/* 39 */     if (visibleObject.getTarget() != null) {
/*    */       
/* 41 */       this.targetObjectId = visibleObject.getTarget().getObjectId();
/* 42 */       this.heading = Math.abs(128 - visibleObject.getTarget().getHeading());
/*    */     }
/*    */     else {
/*    */       
/* 46 */       this.targetObjectId = 0;
/* 47 */       this.heading = visibleObject.getHeading();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 57 */     writeD(buf, this.visibleObject.getObjectId());
/* 58 */     writeD(buf, this.targetObjectId);
/* 59 */     writeC(buf, this.heading);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LOOKATOBJECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */