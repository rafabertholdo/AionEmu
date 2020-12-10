/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.SkillList;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
/*     */ import com.aionemu.gameserver.skillengine.model.learn.SkillLearnTemplate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SkillLearnService
/*     */ {
/*     */   public static void addNewSkills(Player player, boolean isNewCharacter) {
/*  42 */     int level = player.getCommonData().getLevel();
/*  43 */     PlayerClass playerClass = player.getCommonData().getPlayerClass();
/*  44 */     Race playerRace = player.getCommonData().getRace();
/*     */     
/*  46 */     if (isNewCharacter)
/*     */     {
/*  48 */       player.setSkillList(new SkillList());
/*     */     }
/*     */     
/*  51 */     addSkills(player, level, playerClass, playerRace, isNewCharacter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addMissingSkills(Player player) {
/*  61 */     int level = player.getCommonData().getLevel();
/*  62 */     PlayerClass playerClass = player.getCommonData().getPlayerClass();
/*  63 */     Race playerRace = player.getCommonData().getRace();
/*     */     
/*  65 */     for (int i = 0; i <= level; i++)
/*     */     {
/*  67 */       addSkills(player, i, playerClass, playerRace, false);
/*     */     }
/*     */     
/*  70 */     if (!playerClass.isStartingClass()) {
/*     */       
/*  72 */       PlayerClass startinClass = PlayerClass.getStartingClassFor(playerClass);
/*     */       
/*  74 */       for (int j = 1; j < 10; j++)
/*     */       {
/*  76 */         addSkills(player, j, startinClass, playerRace, false);
/*     */       }
/*     */       
/*  79 */       if (player.getSkillList().getSkillEntry(30001) != null) {
/*     */         
/*  81 */         int skillLevel = player.getSkillList().getSkillLevel(30001);
/*  82 */         player.getSkillList().removeSkill(player, 30001);
/*  83 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player));
/*  84 */         player.getSkillList().addSkill(player, 30002, skillLevel, true);
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   private static void addSkills(Player player, int level, PlayerClass playerClass, Race playerRace, boolean isNewCharacter) {
/* 101 */     SkillLearnTemplate[] skillTemplates = DataManager.SKILL_TREE_DATA.getTemplatesFor(playerClass, level, playerRace);
/*     */ 
/*     */     
/* 104 */     SkillList playerSkillList = player.getSkillList();
/*     */     
/* 106 */     for (SkillLearnTemplate template : skillTemplates) {
/*     */       
/* 108 */       if (checkLearnIsPossible(playerSkillList, template))
/*     */       {
/*     */         
/* 111 */         playerSkillList.addSkill(player, template.getSkillId(), template.getSkillLevel(), !isNewCharacter);
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   private static boolean checkLearnIsPossible(SkillList playerSkillList, SkillLearnTemplate template) {
/* 126 */     if (playerSkillList.isSkillPresent(template.getSkillId())) {
/* 127 */       return true;
/*     */     }
/* 129 */     if ((CustomConfig.SKILL_AUTOLEARN && !template.isStigma()) || (CustomConfig.STIGMA_AUTOLEARN && template.isStigma())) {
/* 130 */       return true;
/*     */     }
/* 132 */     if (template.isAutolearn()) {
/* 133 */       return true;
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\SkillLearnService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */