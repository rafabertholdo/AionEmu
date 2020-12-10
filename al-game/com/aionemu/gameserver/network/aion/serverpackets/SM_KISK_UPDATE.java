/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Kisk;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_KISK_UPDATE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int objId;
/*    */   private int useMask;
/*    */   private int currentMembers;
/*    */   private int maxMembers;
/*    */   private int remainingRessurects;
/*    */   private int maxRessurects;
/*    */   private int remainingLifetime;
/*    */   
/*    */   public SM_KISK_UPDATE(Kisk kisk) {
/* 49 */     this.objId = kisk.getObjectId();
/* 50 */     this.useMask = kisk.getUseMask();
/* 51 */     this.currentMembers = kisk.getCurrentMemberCount();
/* 52 */     this.maxMembers = kisk.getMaxMembers();
/* 53 */     this.remainingRessurects = kisk.getRemainingResurrects();
/* 54 */     this.maxRessurects = kisk.getMaxRessurects();
/* 55 */     this.remainingLifetime = kisk.getRemainingLifetime();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 61 */     writeD(buf, this.objId);
/* 62 */     writeD(buf, this.useMask);
/* 63 */     writeD(buf, this.currentMembers);
/* 64 */     writeD(buf, this.maxMembers);
/* 65 */     writeD(buf, this.remainingRessurects);
/* 66 */     writeD(buf, this.maxRessurects);
/* 67 */     writeD(buf, this.remainingLifetime);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_KISK_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */