/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAlliance;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Monster;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.services.AllianceService;
/*     */ import com.aionemu.gameserver.services.DropService;
/*     */ import com.aionemu.gameserver.services.GroupService;
/*     */ import com.aionemu.gameserver.utils.stats.StatFunctions;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldType;
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
/*     */ public class MonsterController
/*     */   extends NpcController
/*     */ {
/*     */   public void doReward() {
/*  41 */     AionObject winner = getOwner().getAggroList().getMostDamage();
/*     */     
/*  43 */     if (winner == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  48 */     if (winner instanceof PlayerAlliance) {
/*     */       
/*  50 */       AllianceService.getInstance().doReward((PlayerAlliance)winner, getOwner());
/*     */     }
/*  52 */     else if (winner instanceof PlayerGroup) {
/*     */       
/*  54 */       GroupService.getInstance().doReward((PlayerGroup)winner, getOwner());
/*     */     }
/*  56 */     else if (((Player)winner).isInGroup()) {
/*     */       
/*  58 */       GroupService.getInstance().doReward(((Player)winner).getPlayerGroup(), getOwner());
/*     */     }
/*     */     else {
/*     */       
/*  62 */       super.doReward();
/*     */       
/*  64 */       Player player = (Player)winner;
/*     */ 
/*     */       
/*  67 */       long expReward = StatFunctions.calculateSoloExperienceReward(player, (Creature)getOwner());
/*  68 */       player.getCommonData().addExp(expReward);
/*     */ 
/*     */       
/*  71 */       int currentDp = player.getCommonData().getDp();
/*  72 */       int dpReward = StatFunctions.calculateSoloDPReward(player, (Creature)getOwner());
/*  73 */       player.getCommonData().setDp(dpReward + currentDp);
/*     */ 
/*     */       
/*  76 */       WorldType worldType = World.getInstance().getWorldMap(player.getWorldId()).getWorldType();
/*  77 */       if (worldType == WorldType.ABYSS) {
/*     */         
/*  79 */         int apReward = StatFunctions.calculateSoloAPReward(player, (Creature)getOwner());
/*  80 */         player.getCommonData().addAp(apReward);
/*     */       } 
/*     */       
/*  83 */       QuestEngine.getInstance().onKill(new QuestEnv((VisibleObject)getOwner(), player, Integer.valueOf(0), Integer.valueOf(0)));
/*     */ 
/*     */       
/*  86 */       DropService.getInstance().registerDrop((Npc)getOwner(), player, player.getLevel());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRespawn() {
/*  93 */     super.onRespawn();
/*  94 */     DropService.getInstance().unregisterDrop((Npc)getOwner());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Monster getOwner() {
/* 100 */     return (Monster)super.getOwner();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\MonsterController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */