/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SM_ALLIANCE_MEMBER_INFO
/*     */   extends AionServerPacket
/*     */ {
/*     */   private PlayerAllianceMember member;
/*     */   private PlayerAllianceEvent event;
/*     */   
/*     */   public SM_ALLIANCE_MEMBER_INFO(PlayerAllianceMember member, PlayerAllianceEvent event) {
/*  42 */     this.member = member;
/*  43 */     this.event = event;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  49 */     PlayerCommonData pcd = this.member.getCommonData();
/*  50 */     WorldPosition wp = pcd.getPosition();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     if (!this.member.isOnline()) {
/*  58 */       this.event = PlayerAllianceEvent.DISCONNECTED;
/*     */     }
/*  60 */     writeD(buf, this.member.getAllianceId());
/*  61 */     writeD(buf, this.member.getObjectId());
/*  62 */     if (this.member.isOnline()) {
/*     */       
/*  64 */       PlayerLifeStats pls = this.member.getPlayer().getLifeStats();
/*  65 */       writeD(buf, pls.getMaxHp());
/*  66 */       writeD(buf, pls.getCurrentHp());
/*  67 */       writeD(buf, pls.getMaxMp());
/*  68 */       writeD(buf, pls.getCurrentMp());
/*  69 */       writeD(buf, pls.getMaxFp());
/*  70 */       writeD(buf, pls.getCurrentFp());
/*     */     }
/*     */     else {
/*     */       
/*  74 */       writeD(buf, 0);
/*  75 */       writeD(buf, 0);
/*  76 */       writeD(buf, 0);
/*  77 */       writeD(buf, 0);
/*  78 */       writeD(buf, 0);
/*  79 */       writeD(buf, 0);
/*     */     } 
/*  81 */     writeD(buf, wp.getMapId());
/*  82 */     writeD(buf, wp.getMapId());
/*  83 */     writeF(buf, wp.getX());
/*  84 */     writeF(buf, wp.getY());
/*  85 */     writeF(buf, wp.getZ());
/*  86 */     writeC(buf, pcd.getPlayerClass().getClassId());
/*  87 */     writeC(buf, pcd.getGender().getGenderId());
/*  88 */     writeC(buf, pcd.getLevel());
/*  89 */     writeC(buf, this.event.getId());
/*  90 */     writeH(buf, 0);
/*  91 */     switch (this.event) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case ENTER:
/*     */       case UPDATE:
/*     */       case RECONNECT:
/*     */       case MEMBER_GROUP_CHANGE:
/*     */       case APPOINT_VICE_CAPTAIN:
/*     */       case DEMOTE_VICE_CAPTAIN:
/*     */       case APPOINT_CAPTAIN:
/* 108 */         writeS(buf, pcd.getName());
/* 109 */         writeD(buf, 0);
/*     */         
/* 111 */         if (this.member.isOnline()) {
/*     */           
/* 113 */           List<Effect> abnormalEffects = this.member.getPlayer().getEffectController().getAbnormalEffects();
/* 114 */           writeH(buf, abnormalEffects.size());
/* 115 */           for (Effect effect : abnormalEffects) {
/*     */             
/* 117 */             writeD(buf, effect.getEffectorId());
/* 118 */             writeH(buf, effect.getSkillId());
/* 119 */             writeC(buf, effect.getSkillLevel());
/* 120 */             writeC(buf, effect.getTargetSlot());
/* 121 */             writeD(buf, effect.getElapsedTime());
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/* 126 */         writeH(buf, 0);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ALLIANCE_MEMBER_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */