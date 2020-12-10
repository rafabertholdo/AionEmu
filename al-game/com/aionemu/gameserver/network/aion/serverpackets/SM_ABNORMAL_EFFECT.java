/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Collection;
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
/*    */ public class SM_ABNORMAL_EFFECT
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int effectedId;
/*    */   private int abnormals;
/*    */   private Collection<Effect> effects;
/*    */   
/*    */   public SM_ABNORMAL_EFFECT(int effectedId, int abnormals, Collection<Effect> effects) {
/* 37 */     this.effects = effects;
/* 38 */     this.abnormals = abnormals;
/* 39 */     this.effectedId = effectedId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 45 */     writeD(buf, this.effectedId);
/* 46 */     writeC(buf, 1);
/* 47 */     writeD(buf, 0);
/* 48 */     writeD(buf, this.abnormals);
/*    */     
/* 50 */     writeH(buf, this.effects.size());
/*    */     
/* 52 */     for (Effect effect : this.effects) {
/*    */       
/* 54 */       writeH(buf, effect.getSkillId());
/* 55 */       writeC(buf, effect.getSkillLevel());
/* 56 */       writeC(buf, effect.getTargetSlot());
/* 57 */       writeD(buf, effect.getElapsedTime());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ABNORMAL_EFFECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */