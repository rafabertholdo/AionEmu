/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class _2020KeepingtheBlackClawTribeinCheck
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2020;
/*     */   
/*     */   public _2020KeepingtheBlackClawTribeinCheck() {
/*  40 */     super(Integer.valueOf(2020));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(2020);
/*  47 */     this.qe.setNpcQuestData(203665).addOnTalkEvent(2020);
/*  48 */     this.qe.setNpcQuestData(203668).addOnTalkEvent(2020);
/*  49 */     this.qe.setNpcQuestData(210562).addOnKillEvent(2020);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  55 */     Player player = env.getPlayer();
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(2020);
/*  57 */     if (qs == null) {
/*  58 */       return false;
/*     */     }
/*  60 */     int var = qs.getQuestVarById(0);
/*  61 */     int targetId = 0;
/*  62 */     if (env.getVisibleObject() instanceof Npc) {
/*  63 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  65 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  67 */       switch (targetId) {
/*     */         
/*     */         case 203665:
/*  70 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  73 */               if (var == 0)
/*  74 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */               break;
/*     */             case 10000:
/*  77 */               if (var == 0) {
/*     */                 
/*  79 */                 qs.setQuestVarById(0, var + 1);
/*  80 */                 updateQuestStatus(player, qs);
/*  81 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  82 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203668:
/*  87 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  90 */               if (var == 1)
/*  91 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  92 */               if (var == 5)
/*  93 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  94 */               if (var == 6)
/*  95 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */               break;
/*     */             case 10001:
/*     */             case 10002:
/*  99 */               if (var == 1 || var == 5) {
/*     */                 
/* 101 */                 qs.setQuestVarById(0, var + 1);
/* 102 */                 updateQuestStatus(player, qs);
/* 103 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 104 */                 return true;
/*     */               } 
/*     */             case 33:
/* 107 */               if (var == 6) {
/*     */                 
/* 109 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/* 111 */                   qs.setStatus(QuestStatus.REWARD);
/* 112 */                   updateQuestStatus(player, qs);
/* 113 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */                 } 
/*     */                 
/* 116 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 121 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 123 */       if (targetId == 203668)
/* 124 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 132 */     Player player = env.getPlayer();
/* 133 */     QuestState qs = player.getQuestStateList().getQuestState(2020);
/* 134 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 135 */       return false;
/*     */     }
/* 137 */     int var = qs.getQuestVarById(0);
/* 138 */     int targetId = 0;
/* 139 */     if (env.getVisibleObject() instanceof Npc) {
/* 140 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 142 */     if (targetId == 210562 && var >= 2 && var < 5) {
/*     */       
/* 144 */       qs.setQuestVarById(0, var + 1);
/* 145 */       updateQuestStatus(player, qs);
/* 146 */       return true;
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 154 */     Player player = env.getPlayer();
/* 155 */     QuestState qs = player.getQuestStateList().getQuestState(2020);
/* 156 */     boolean lvlCheck = QuestService.checkLevelRequirement(2020, player.getCommonData().getLevel());
/* 157 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/* 158 */       return false; 
/* 159 */     qs.setStatus(QuestStatus.START);
/* 160 */     updateQuestStatus(player, qs);
/* 161 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2020KeepingtheBlackClawTribeinCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */