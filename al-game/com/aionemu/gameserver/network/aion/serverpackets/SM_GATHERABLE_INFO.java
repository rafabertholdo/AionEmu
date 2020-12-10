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
/*    */ public class SM_GATHERABLE_INFO
/*    */   extends AionServerPacket
/*    */ {
/*    */   private VisibleObject visibleObject;
/*    */   
/*    */   public SM_GATHERABLE_INFO(VisibleObject visibleObject) {
/* 36 */     this.visibleObject = visibleObject;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 42 */     writeF(buf, this.visibleObject.getX());
/* 43 */     writeF(buf, this.visibleObject.getY());
/* 44 */     writeF(buf, this.visibleObject.getZ());
/* 45 */     writeD(buf, this.visibleObject.getObjectId());
/* 46 */     writeD(buf, this.visibleObject.getSpawn().getStaticid());
/* 47 */     writeD(buf, this.visibleObject.getObjectTemplate().getTemplateId());
/* 48 */     writeH(buf, 1);
/* 49 */     writeC(buf, 0);
/* 50 */     writeD(buf, this.visibleObject.getObjectTemplate().getNameId());
/* 51 */     writeH(buf, 0);
/* 52 */     writeH(buf, 0);
/* 53 */     writeH(buf, 0);
/* 54 */     writeC(buf, 100);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_GATHERABLE_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */