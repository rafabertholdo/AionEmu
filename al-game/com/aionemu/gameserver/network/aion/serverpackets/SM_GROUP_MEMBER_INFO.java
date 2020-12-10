/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
/*    */ import com.aionemu.gameserver.model.group.GroupEvent;
/*    */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.world.WorldPosition;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
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
/*    */ public class SM_GROUP_MEMBER_INFO
/*    */   extends AionServerPacket
/*    */ {
/*    */   private PlayerGroup group;
/*    */   private Player player;
/*    */   private GroupEvent event;
/*    */   
/*    */   public SM_GROUP_MEMBER_INFO(PlayerGroup group, Player player, GroupEvent event) {
/* 44 */     this.group = group;
/* 45 */     this.player = player;
/* 46 */     this.event = event;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 52 */     PlayerLifeStats pls = this.player.getLifeStats();
/* 53 */     PlayerCommonData pcd = this.player.getCommonData();
/* 54 */     WorldPosition wp = pcd.getPosition();
/*    */     
/* 56 */     writeD(buf, this.group.getGroupId());
/* 57 */     writeD(buf, this.player.getObjectId());
/* 58 */     writeD(buf, pls.getMaxHp());
/* 59 */     writeD(buf, pls.getCurrentHp());
/* 60 */     writeD(buf, pls.getMaxMp());
/* 61 */     writeD(buf, pls.getCurrentMp());
/* 62 */     writeD(buf, pls.getMaxFp());
/* 63 */     writeD(buf, pls.getCurrentFp());
/* 64 */     writeD(buf, wp.getMapId());
/* 65 */     writeD(buf, wp.getMapId());
/* 66 */     writeF(buf, wp.getX());
/* 67 */     writeF(buf, wp.getY());
/* 68 */     writeF(buf, wp.getZ());
/* 69 */     writeC(buf, pcd.getPlayerClass().getClassId());
/* 70 */     writeC(buf, pcd.getGender().getGenderId());
/* 71 */     writeC(buf, pcd.getLevel());
/* 72 */     writeC(buf, this.event.getId());
/* 73 */     writeH(buf, 1);
/* 74 */     if (this.event == GroupEvent.MOVEMENT) {
/*    */       return;
/*    */     }
/*    */     
/* 78 */     writeS(buf, pcd.getName());
/* 79 */     writeH(buf, 0);
/* 80 */     writeH(buf, 0);
/*    */     
/* 82 */     List<Effect> abnormalEffects = this.player.getEffectController().getAbnormalEffects();
/* 83 */     writeH(buf, abnormalEffects.size());
/* 84 */     for (Effect effect : abnormalEffects) {
/*    */       
/* 86 */       writeD(buf, effect.getEffectorId());
/* 87 */       writeH(buf, effect.getSkillId());
/* 88 */       writeC(buf, effect.getSkillLevel());
/* 89 */       writeC(buf, effect.getTargetSlot());
/* 90 */       writeD(buf, effect.getElapsedTime());
/*    */     } 
/* 92 */     writeD(buf, 9719);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_GROUP_MEMBER_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */