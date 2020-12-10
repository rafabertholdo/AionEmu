/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
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
/*    */ public class SM_RESURRECT
/*    */   extends AionServerPacket
/*    */ {
/*    */   private String name;
/*    */   private int skillId;
/*    */   
/*    */   public SM_RESURRECT(Creature creature) {
/* 37 */     this(creature, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_RESURRECT(Creature creature, int skillId) {
/* 42 */     this.name = creature.getName();
/* 43 */     this.skillId = skillId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 52 */     writeS(buf, this.name);
/* 53 */     writeH(buf, this.skillId);
/* 54 */     writeD(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_RESURRECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */