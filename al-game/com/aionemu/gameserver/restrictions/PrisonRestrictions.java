/*     */ package com.aionemu.gameserver.restrictions;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.WorldMapType;
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
/*     */ public class PrisonRestrictions
/*     */   extends AbstractRestrictions
/*     */ {
/*     */   public boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
/*  34 */     if (isInPrison(player)) {
/*     */       
/*  36 */       PacketSendUtility.sendMessage(player, "You are in prison!");
/*  37 */       return true;
/*     */     } 
/*     */     
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttack(Player player, VisibleObject target) {
/*  46 */     if (isInPrison(player)) {
/*     */       
/*  48 */       PacketSendUtility.sendMessage(player, "You cannot attack in prison!");
/*  49 */       return false;
/*     */     } 
/*     */     
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseSkill(Player player, Skill skill) {
/*  58 */     if (isInPrison(player)) {
/*     */       
/*  60 */       PacketSendUtility.sendMessage(player, "You cannot use skills in prison!");
/*  61 */       return false;
/*     */     } 
/*     */     
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAffectBySkill(Player player, VisibleObject target) {
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canChat(Player player) {
/*  77 */     if (isInPrison(player)) {
/*     */       
/*  79 */       PacketSendUtility.sendMessage(player, "You cannot chat in prison!");
/*  80 */       return false;
/*     */     } 
/*     */     
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInviteToGroup(Player player, Player target) {
/*  89 */     if (isInPrison(player)) {
/*     */       
/*  91 */       PacketSendUtility.sendMessage(player, "You cannot invite members to group in prison!");
/*  92 */       return false;
/*     */     } 
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInviteToAlliance(Player player, Player target) {
/* 101 */     if (isInPrison(player)) {
/*     */       
/* 103 */       PacketSendUtility.sendMessage(player, "You cannot invite members to alliance in prison!");
/* 104 */       return false;
/*     */     } 
/*     */     
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canChangeEquip(Player player) {
/* 113 */     if (isInPrison(player)) {
/*     */       
/* 115 */       PacketSendUtility.sendMessage(player, "You cannot equip / unequip item in prison!");
/* 116 */       return false;
/*     */     } 
/*     */     
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseItem(Player player) {
/* 125 */     if (isInPrison(player)) {
/*     */       
/* 127 */       PacketSendUtility.sendMessage(player, "You cannot use item in prison!");
/* 128 */       return false;
/*     */     } 
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isInPrison(Player player) {
/* 135 */     return (player.isInPrison() || player.getWorldId() == WorldMapType.PRISON.getId());
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\restrictions\PrisonRestrictions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */