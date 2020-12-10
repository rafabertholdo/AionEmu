/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*    */ public class SM_TARGET_SELECTED
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Player player;
/*    */   private int level;
/*    */   private int maxHp;
/*    */   private int currentHp;
/*    */   private int targetObjId;
/*    */   
/*    */   public SM_TARGET_SELECTED(Player player) {
/* 40 */     this.player = player;
/* 41 */     if (player.getTarget() instanceof Creature) {
/*    */       
/* 43 */       this.level = ((Creature)player.getTarget()).getLevel();
/* 44 */       this.maxHp = ((Creature)player.getTarget()).getLifeStats().getMaxHp();
/* 45 */       this.currentHp = ((Creature)player.getTarget()).getLifeStats().getCurrentHp();
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 50 */       this.level = 1;
/* 51 */       this.maxHp = 1;
/* 52 */       this.currentHp = 1;
/*    */     } 
/*    */     
/* 55 */     if (player.getTarget() != null) {
/* 56 */       this.targetObjId = player.getTarget().getObjectId();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 65 */     writeD(buf, this.targetObjId);
/* 66 */     writeH(buf, this.level);
/* 67 */     writeD(buf, this.maxHp);
/* 68 */     writeD(buf, this.currentHp);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_TARGET_SELECTED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */