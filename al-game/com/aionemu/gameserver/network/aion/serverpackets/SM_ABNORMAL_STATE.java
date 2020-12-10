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
/*    */ 
/*    */ public class SM_ABNORMAL_STATE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Collection<Effect> effects;
/*    */   private int abnormals;
/*    */   
/*    */   public SM_ABNORMAL_STATE(Collection<Effect> effects, int abnormals) {
/* 37 */     this.effects = effects;
/* 38 */     this.abnormals = abnormals;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 44 */     writeD(buf, this.abnormals);
/* 45 */     writeH(buf, this.effects.size());
/*    */     
/* 47 */     for (Effect effect : this.effects) {
/*    */       
/* 49 */       writeD(buf, effect.getEffectorId());
/* 50 */       writeH(buf, effect.getSkillId());
/* 51 */       writeC(buf, effect.getSkillLevel());
/* 52 */       writeC(buf, effect.getTargetSlot());
/* 53 */       writeD(buf, effect.getElapsedTime());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ABNORMAL_STATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */