/*     */ package com.aionemu.gameserver.model.gameobjects.stats;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.items.ItemSlot;
/*     */ import com.aionemu.gameserver.model.items.NpcEquippedGear;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.model.templates.stats.NpcStatsTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.TreeSet;
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
/*     */ public class NpcGameStats
/*     */   extends CreatureGameStats<Npc>
/*     */ {
/*  37 */   int currentRunSpeed = 0;
/*     */ 
/*     */   
/*     */   public NpcGameStats(Npc owner) {
/*  41 */     super(owner);
/*     */     
/*  43 */     NpcStatsTemplate nst = owner.getObjectTemplate().getStatsTemplate();
/*     */     
/*  45 */     initStat(StatEnum.MAXHP, nst.getMaxHp() + Math.round(owner.getObjectTemplate().getHpGauge() * 1.5F * owner.getLevel()));
/*     */     
/*  47 */     initStat(StatEnum.MAXMP, nst.getMaxMp());
/*     */ 
/*     */     
/*  50 */     initStat(StatEnum.ATTACK_SPEED, 2000);
/*  51 */     initStat(StatEnum.PHYSICAL_DEFENSE, Math.round((nst.getPdef() / owner.getLevel() - 1.0F) * nst.getPdef() + (10 * owner.getLevel())));
/*     */     
/*  53 */     initStat(StatEnum.EVASION, Math.round(nst.getEvasion() * 2.3F + (owner.getLevel() * 10)));
/*  54 */     initStat(StatEnum.MAGICAL_RESIST, Math.round(nst.getMdef()));
/*  55 */     initStat(StatEnum.MAIN_HAND_POWER, nst.getPower());
/*  56 */     initStat(StatEnum.MAIN_HAND_ACCURACY, Math.round(nst.getAccuracy() * 2.3F + (owner.getLevel() * 10)));
/*  57 */     initStat(StatEnum.MAIN_HAND_CRITICAL, Math.round(nst.getCrit()));
/*  58 */     initStat(StatEnum.SPEED, Math.round(nst.getRunSpeedFight() * 1000.0F));
/*     */     
/*  60 */     initStat(StatEnum.MAGICAL_ACCURACY, 1500);
/*  61 */     initStat(StatEnum.BOOST_MAGICAL_SKILL, 1000);
/*     */     
/*  63 */     initStatsFromEquipment(owner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initStatsFromEquipment(Npc owner) {
/*  73 */     NpcEquippedGear equipment = owner.getObjectTemplate().getEquipment();
/*  74 */     if (equipment != null) {
/*     */       
/*  76 */       equipment.init();
/*     */       
/*  78 */       ItemTemplate itemTemplate = equipment.getItem(ItemSlot.MAIN_HAND);
/*  79 */       if (itemTemplate != null) {
/*     */         
/*  81 */         TreeSet<StatModifier> modifiers = itemTemplate.getModifiers();
/*  82 */         if (modifiers != null)
/*     */         {
/*  84 */           for (StatModifier modifier : modifiers) {
/*     */             
/*  86 */             if (modifier.getStat() == StatEnum.ATTACK_RANGE) {
/*  87 */               initStat(StatEnum.ATTACK_RANGE, modifier.apply(0, 0));
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (getCurrentStat(StatEnum.ATTACK_RANGE) == 0) {
/*  97 */       initStat(StatEnum.ATTACK_RANGE, 2000);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void recomputeStats() {
/* 103 */     super.recomputeStats();
/*     */     
/* 105 */     int newRunSpeed = getCurrentStat(StatEnum.SPEED);
/*     */     
/* 107 */     if (newRunSpeed != this.currentRunSpeed) {
/*     */       
/* 109 */       this.owner.getMoveController().setSpeed(newRunSpeed / 1000.0F);
/* 110 */       PacketSendUtility.broadcastPacket((VisibleObject)this.owner, (AionServerPacket)new SM_EMOTION((Creature)this.owner, EmotionType.START_EMOTE2, 0, 0));
/*     */     } 
/* 112 */     this.currentRunSpeed = newRunSpeed;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\NpcGameStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */