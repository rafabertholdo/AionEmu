/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ public class ClassChangeService
/*     */ {
/*     */   public static void showClassChangeDialog(Player player) {
/*  44 */     if (CustomConfig.ENABLE_SIMPLE_2NDCLASS) {
/*     */       
/*  46 */       PlayerClass playerClass = player.getPlayerClass();
/*  47 */       Race playerRace = player.getCommonData().getRace();
/*  48 */       if (player.getLevel() >= 9 && Arrays.<Integer>asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(3), Integer.valueOf(6), Integer.valueOf(9) }).contains(Integer.valueOf(playerClass.ordinal())))
/*     */       {
/*  50 */         if (playerRace.ordinal() == 0) {
/*     */           
/*  52 */           switch (playerClass.ordinal()) {
/*     */             
/*     */             case 0:
/*  55 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 2375, 1006));
/*     */               break;
/*     */             case 3:
/*  58 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 2716, 1006));
/*     */               break;
/*     */             case 6:
/*  61 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 3057, 1006));
/*     */               break;
/*     */             case 9:
/*  64 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 3398, 1006));
/*     */               break;
/*     */           } 
/*     */         
/*  68 */         } else if (playerRace.ordinal() == 1) {
/*     */           
/*  70 */           switch (playerClass.ordinal()) {
/*     */             
/*     */             case 0:
/*  73 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 3057, 2008));
/*     */               break;
/*     */             case 3:
/*  76 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 3398, 2008));
/*     */               break;
/*     */             case 6:
/*  79 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 3739, 2008));
/*     */               break;
/*     */             case 9:
/*  82 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 4080, 2008));
/*     */               break;
/*     */           } 
/*     */         } 
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
/*     */   public static void changeClassToSelection(Player player, int dialogId) {
/*  97 */     if (CustomConfig.ENABLE_SIMPLE_2NDCLASS) {
/*     */       
/*  99 */       Race playerRace = player.getCommonData().getRace();
/* 100 */       if (playerRace.ordinal() == 0) {
/*     */         
/* 102 */         switch (dialogId) {
/*     */           
/*     */           case 2376:
/* 105 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("1")));
/*     */             break;
/*     */           case 2461:
/* 108 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("2")));
/*     */             break;
/*     */           case 2717:
/* 111 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("4")));
/*     */             break;
/*     */           case 2802:
/* 114 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("5")));
/*     */             break;
/*     */           case 3058:
/* 117 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("7")));
/*     */             break;
/*     */           case 3143:
/* 120 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("8")));
/*     */             break;
/*     */           case 3399:
/* 123 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("10")));
/*     */             break;
/*     */           case 3484:
/* 126 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("11")));
/*     */             break;
/*     */         } 
/* 129 */         addCompliteQuest(player, 1006);
/* 130 */         addCompliteQuest(player, 1007);
/*     */       }
/* 132 */       else if (playerRace.ordinal() == 1) {
/*     */         
/* 134 */         switch (dialogId) {
/*     */           
/*     */           case 3058:
/* 137 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("1")));
/*     */             break;
/*     */           case 3143:
/* 140 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("2")));
/*     */             break;
/*     */           case 3399:
/* 143 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("4")));
/*     */             break;
/*     */           case 3484:
/* 146 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("5")));
/*     */             break;
/*     */           case 3740:
/* 149 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("7")));
/*     */             break;
/*     */           case 3825:
/* 152 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("8")));
/*     */             break;
/*     */           case 4081:
/* 155 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("10")));
/*     */             break;
/*     */           case 4166:
/* 158 */             setClass(player, PlayerClass.getPlayerClassById(Byte.parseByte("11")));
/*     */             break;
/*     */         } 
/* 161 */         addCompliteQuest(player, 2008);
/* 162 */         addCompliteQuest(player, 2009);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addCompliteQuest(Player player, int questId) {
/* 169 */     QuestState qs = player.getQuestStateList().getQuestState(questId);
/* 170 */     if (qs == null) {
/*     */       
/* 172 */       player.getQuestStateList().addQuest(questId, new QuestState(questId, QuestStatus.COMPLETE, 0, 0));
/* 173 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(questId, QuestStatus.COMPLETE.value(), 0));
/*     */     }
/*     */     else {
/*     */       
/* 177 */       qs.setStatus(QuestStatus.COMPLETE);
/* 178 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(questId, qs.getStatus(), qs.getQuestVars().getQuestVars()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setClass(Player player, PlayerClass playerClass) {
/* 185 */     player.getCommonData().setPlayerClass(playerClass);
/* 186 */     player.getCommonData().upgradePlayer();
/* 187 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0, 0));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ClassChangeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */