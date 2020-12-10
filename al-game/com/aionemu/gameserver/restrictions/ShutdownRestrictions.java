/*     */ package com.aionemu.gameserver.restrictions;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class ShutdownRestrictions
/*     */   extends AbstractRestrictions
/*     */ {
/*     */   public boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
/*  33 */     if (isInShutdownProgress(player)) {
/*     */       
/*  35 */       PacketSendUtility.sendMessage(player, "You are in shutdown progress!");
/*  36 */       return true;
/*     */     } 
/*     */     
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttack(Player player, VisibleObject target) {
/*  45 */     if (isInShutdownProgress(player)) {
/*     */       
/*  47 */       PacketSendUtility.sendMessage(player, "You cannot attack in Shutdown progress!");
/*  48 */       return false;
/*     */     } 
/*     */     
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAffectBySkill(Player player, VisibleObject target) {
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseSkill(Player player, Skill skill) {
/*  63 */     if (isInShutdownProgress(player)) {
/*     */       
/*  65 */       PacketSendUtility.sendMessage(player, "You cannot use skills in Shutdown progress!");
/*  66 */       return false;
/*     */     } 
/*     */     
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canChat(Player player) {
/*  75 */     if (isInShutdownProgress(player)) {
/*     */       
/*  77 */       PacketSendUtility.sendMessage(player, "You cannot chat in Shutdown progress!");
/*  78 */       return false;
/*     */     } 
/*     */     
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInviteToGroup(Player player, Player target) {
/*  87 */     if (isInShutdownProgress(player)) {
/*     */       
/*  89 */       PacketSendUtility.sendMessage(player, "You cannot invite members to group in Shutdown progress!");
/*  90 */       return false;
/*     */     } 
/*     */     
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInviteToAlliance(Player player, Player target) {
/*  99 */     if (isInShutdownProgress(player)) {
/*     */       
/* 101 */       PacketSendUtility.sendMessage(player, "You cannot invite members to alliance in Shutdown progress!");
/* 102 */       return false;
/*     */     } 
/*     */     
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canChangeEquip(Player player) {
/* 111 */     if (isInShutdownProgress(player)) {
/*     */       
/* 113 */       PacketSendUtility.sendMessage(player, "You cannot equip / unequip item in Shutdown progress!");
/* 114 */       return false;
/*     */     } 
/*     */     
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isInShutdownProgress(Player player) {
/* 122 */     return player.getController().isInShutdownProgress();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\restrictions\ShutdownRestrictions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */