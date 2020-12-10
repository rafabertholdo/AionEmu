/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
/*     */ import com.aionemu.gameserver.skillengine.effect.RebirthEffect;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class ReviveController
/*     */ {
/*     */   private Player player;
/*     */   private int rebirthResurrectPercent;
/*     */   
/*     */   public ReviveController(Player player) {
/*  47 */     this.player = player;
/*  48 */     this.rebirthResurrectPercent = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skillRevive() {
/*  56 */     revive(10, 10);
/*  57 */     PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.RESURRECT), true);
/*     */     
/*  59 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
/*  60 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
/*     */     
/*  62 */     if (this.player.isInPrison()) {
/*  63 */       TeleportService.teleportToPrison(this.player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebirthRevive() {
/*  71 */     if (this.rebirthResurrectPercent <= 0) {
/*     */       
/*  73 */       PacketSendUtility.sendMessage(this.player, "Error: Rebirth effect missing percent.");
/*  74 */       this.rebirthResurrectPercent = 5;
/*     */     } 
/*  76 */     revive(this.rebirthResurrectPercent, this.rebirthResurrectPercent);
/*  77 */     PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.RESURRECT), true);
/*     */     
/*  79 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
/*  80 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
/*     */     
/*  82 */     if (this.player.isInPrison()) {
/*  83 */       TeleportService.teleportToPrison(this.player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindRevive() {
/*  91 */     revive(25, 25);
/*  92 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
/*     */ 
/*     */     
/*  95 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
/*  96 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_PLAYER_INFO(this.player, false));
/*     */     
/*  98 */     if (this.player.isInPrison()) {
/*  99 */       TeleportService.teleportToPrison(this.player);
/*     */     } else {
/* 101 */       TeleportService.moveToBindLocation(this.player, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void kiskRevive() {
/* 106 */     Kisk kisk = this.player.getKisk();
/*     */     
/* 108 */     revive(25, 25);
/* 109 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
/*     */     
/* 111 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
/* 112 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_PLAYER_INFO(this.player, false));
/*     */     
/* 114 */     if (this.player.isInPrison()) {
/* 115 */       TeleportService.teleportToPrison(this.player);
/*     */     } else {
/*     */       
/* 118 */       TeleportService.moveToKiskLocation(this.player);
/* 119 */       kisk.resurrectionUsed(this.player);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void revive(int hpPercent, int mpPercent) {
/* 125 */     this.player.getLifeStats().setCurrentHpPercent(hpPercent);
/* 126 */     this.player.getLifeStats().setCurrentMpPercent(mpPercent);
/* 127 */     this.player.getCommonData().setDp(0);
/* 128 */     this.player.getLifeStats().triggerRestoreOnRevive();
/*     */     
/* 130 */     this.player.getController().onRespawn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void itemSelfRevive() {
/* 138 */     Item item = getSelfRezStone(this.player);
/* 139 */     if (item == null) {
/*     */ 
/*     */       
/* 142 */       PacketSendUtility.sendMessage(this.player, "Error: Couldn't find self-rez item.");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 147 */     int useDelay = item.getItemTemplate().getDelayTime();
/* 148 */     this.player.addItemCoolDown(item.getItemTemplate().getDelayId(), System.currentTimeMillis() + useDelay, useDelay / 1000);
/* 149 */     PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(this.player.getObjectId(), item.getObjectId(), item.getItemTemplate().getTemplateId()), true);
/*     */     
/* 151 */     ItemService.decreaseItemCount(this.player, item, 1L);
/*     */ 
/*     */     
/* 154 */     revive(15, 15);
/* 155 */     PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.RESURRECT), true);
/*     */     
/* 157 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
/* 158 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
/*     */     
/* 160 */     if (this.player.isInPrison()) {
/* 161 */       TeleportService.teleportToPrison(this.player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Item getSelfRezStone(Player player) {
/* 172 */     Item item = null;
/* 173 */     item = tryStone(161001001);
/* 174 */     if (item == null)
/* 175 */       item = tryStone(161000003); 
/* 176 */     if (item == null)
/* 177 */       item = tryStone(161000004); 
/* 178 */     if (item == null)
/* 179 */       item = tryStone(161000001); 
/* 180 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Item tryStone(int stoneId) {
/* 189 */     Item item = this.player.getInventory().getFirstItemByItemId(stoneId);
/* 190 */     if (item != null && this.player.isItemUseDisabled(item.getItemTemplate().getDelayId()))
/* 191 */       item = null; 
/* 192 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkForSelfRezItem(Player player) {
/* 202 */     return (getSelfRezStone(player) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkForSelfRezEffect(Player player) {
/* 213 */     List<Effect> effects = player.getEffectController().getAbnormalEffects();
/* 214 */     for (Effect effect : effects) {
/* 215 */       for (EffectTemplate template : effect.getEffectTemplates()) {
/*     */         
/* 217 */         if (template.getEffectid() == 160) {
/*     */           
/* 219 */           RebirthEffect rebirthEffect = (RebirthEffect)template;
/* 220 */           this.rebirthResurrectPercent = rebirthEffect.getResurrectPercent();
/* 221 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 225 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\ReviveController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */