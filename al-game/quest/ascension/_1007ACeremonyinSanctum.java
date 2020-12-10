/*     */ package quest.ascension;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TELEPORT_LOC;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
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
/*     */ public class _1007ACeremonyinSanctum
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1007;
/*     */   
/*     */   public _1007ACeremonyinSanctum() {
/*  44 */     super(Integer.valueOf(1007));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     if (CustomConfig.ENABLE_SIMPLE_2NDCLASS)
/*     */       return; 
/*  52 */     this.qe.addQuestLvlUp(1007);
/*  53 */     this.qe.setNpcQuestData(790001).addOnTalkEvent(1007);
/*  54 */     this.qe.setNpcQuestData(203725).addOnTalkEvent(1007);
/*  55 */     this.qe.setNpcQuestData(203752).addOnTalkEvent(1007);
/*  56 */     this.qe.setNpcQuestData(203758).addOnTalkEvent(1007);
/*  57 */     this.qe.setNpcQuestData(203759).addOnTalkEvent(1007);
/*  58 */     this.qe.setNpcQuestData(203760).addOnTalkEvent(1007);
/*  59 */     this.qe.setNpcQuestData(203761).addOnTalkEvent(1007);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  65 */     Player player = env.getPlayer();
/*  66 */     QuestState qs = player.getQuestStateList().getQuestState(1007);
/*  67 */     if (qs == null) {
/*  68 */       return false;
/*     */     }
/*  70 */     int var = qs.getQuestVars().getQuestVars();
/*  71 */     int targetId = 0;
/*  72 */     if (env.getVisibleObject() instanceof Npc) {
/*  73 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  75 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  77 */       if (targetId == 790001) {
/*     */         
/*  79 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  82 */             if (var == 0)
/*  83 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */           case 10000:
/*  85 */             if (var == 0) {
/*     */               
/*  87 */               qs.setQuestVar(1);
/*  88 */               updateQuestStatus(player, qs);
/*  89 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/*     */               
/*  91 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_TELEPORT_LOC(110010000, 1313.0F, 1512.0F, 568.0F));
/*  92 */               TeleportService.scheduleTeleportTask(player, 110010000, 1313.0F, 1512.0F, 568.0F);
/*  93 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*  97 */       } else if (targetId == 203725) {
/*     */         
/*  99 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 102 */             if (var == 1)
/* 103 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 1353:
/* 105 */             if (var == 1) {
/*     */               
/* 107 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 92));
/* 108 */               return false;
/*     */             } 
/*     */           case 10001:
/* 111 */             if (var == 1) {
/*     */               
/* 113 */               qs.setQuestVarById(0, var + 1);
/* 114 */               updateQuestStatus(player, qs);
/* 115 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 117 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 121 */       } else if (targetId == 203752) {
/*     */         
/* 123 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 126 */             if (var == 2)
/* 127 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */           case 1694:
/* 129 */             if (var == 2) {
/*     */               
/* 131 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 91));
/* 132 */               return false;
/*     */             } 
/*     */           case 10002:
/* 135 */             if (var == 2) {
/*     */               
/* 137 */               PlayerClass playerClass = PlayerClass.getStartingClassFor(player.getCommonData().getPlayerClass());
/* 138 */               if (playerClass == PlayerClass.WARRIOR) {
/* 139 */                 qs.setQuestVar(10);
/* 140 */               } else if (playerClass == PlayerClass.SCOUT) {
/* 141 */                 qs.setQuestVar(20);
/* 142 */               } else if (playerClass == PlayerClass.MAGE) {
/* 143 */                 qs.setQuestVar(30);
/* 144 */               } else if (playerClass == PlayerClass.PRIEST) {
/* 145 */                 qs.setQuestVar(40);
/* 146 */               }  qs.setStatus(QuestStatus.REWARD);
/* 147 */               updateQuestStatus(player, qs);
/* 148 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 150 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       } 
/* 155 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 157 */       if (targetId == 203758 && var == 10) {
/*     */         
/* 159 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case -1:
/* 162 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */           case 1009:
/* 164 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           case 8:
/*     */           case 9:
/*     */           case 10:
/*     */           case 11:
/*     */           case 12:
/*     */           case 13:
/*     */           case 14:
/*     */           case 15:
/*     */           case 16:
/*     */           case 17:
/* 175 */             if (QuestService.questFinish(env, 0)) {
/*     */               
/* 177 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 178 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 182 */       } else if (targetId == 203759 && var == 20) {
/*     */         
/* 184 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case -1:
/* 187 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */           case 1009:
/* 189 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           case 8:
/*     */           case 9:
/*     */           case 10:
/*     */           case 11:
/*     */           case 12:
/*     */           case 13:
/*     */           case 14:
/*     */           case 15:
/*     */           case 16:
/*     */           case 17:
/* 200 */             if (QuestService.questFinish(env, 1)) {
/*     */               
/* 202 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 203 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 207 */       } else if (targetId == 203760 && var == 30) {
/*     */         
/* 209 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case -1:
/* 212 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
/*     */           case 1009:
/* 214 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */           case 8:
/*     */           case 9:
/*     */           case 10:
/*     */           case 11:
/*     */           case 12:
/*     */           case 13:
/*     */           case 14:
/*     */           case 15:
/*     */           case 16:
/*     */           case 17:
/* 225 */             if (QuestService.questFinish(env, 2)) {
/*     */               
/* 227 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 228 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 232 */       } else if (targetId == 203761 && var == 40) {
/*     */         
/* 234 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case -1:
/* 237 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
/*     */           case 1009:
/* 239 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 8);
/*     */           case 8:
/*     */           case 9:
/*     */           case 10:
/*     */           case 11:
/*     */           case 12:
/*     */           case 13:
/*     */           case 14:
/*     */           case 15:
/*     */           case 16:
/*     */           case 17:
/* 250 */             if (QuestService.questFinish(env, 3)) {
/*     */               
/* 252 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 253 */               return true;
/*     */             }  break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 258 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 263 */     Player player = env.getPlayer();
/* 264 */     QuestState qs = player.getQuestStateList().getQuestState(1007);
/* 265 */     if (qs != null) {
/* 266 */       return false;
/*     */     }
/* 268 */     QuestState qs2 = player.getQuestStateList().getQuestState(1006);
/* 269 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/* 270 */       return false; 
/* 271 */     env.setQuestId(Integer.valueOf(1007));
/* 272 */     QuestService.startQuest(env, QuestStatus.START);
/* 273 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ascension\_1007ACeremonyinSanctum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */