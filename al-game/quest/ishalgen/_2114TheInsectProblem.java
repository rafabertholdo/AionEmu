/*     */ package quest.ishalgen;
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
/*     */ public class _2114TheInsectProblem
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2114;
/*     */   
/*     */   public _2114TheInsectProblem() {
/*  39 */     super(Integer.valueOf(2114));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(203533).addOnQuestStart(2114);
/*  46 */     this.qe.setNpcQuestData(203533).addOnTalkEvent(2114);
/*  47 */     this.qe.setNpcQuestData(210734).addOnKillEvent(2114);
/*  48 */     this.qe.setNpcQuestData(210380).addOnKillEvent(2114);
/*  49 */     this.qe.setNpcQuestData(210381).addOnKillEvent(2114);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  55 */     Player player = env.getPlayer();
/*  56 */     int targetId = 0;
/*  57 */     if (env.getVisibleObject() instanceof Npc)
/*  58 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  59 */     QuestState qs = player.getQuestStateList().getQuestState(2114);
/*  60 */     if (targetId == 203533)
/*     */     {
/*  62 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  64 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  67 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */           case 10000:
/*  69 */             if (QuestService.startQuest(env, QuestStatus.START)) {
/*     */               
/*  71 */               qs = player.getQuestStateList().getQuestState(2114);
/*  72 */               qs.setQuestVar(1);
/*  73 */               updateQuestStatus(player, qs);
/*  74 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  75 */               return true;
/*     */             } 
/*     */           case 10001:
/*  78 */             if (QuestService.startQuest(env, QuestStatus.START)) {
/*     */               
/*  80 */               qs = player.getQuestStateList().getQuestState(2114);
/*  81 */               qs.setQuestVar(11);
/*  82 */               updateQuestStatus(player, qs);
/*  83 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  84 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*  88 */       } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/*  90 */         int var = qs.getQuestVarById(0);
/*  91 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case -1:
/*  94 */             if (var == 10)
/*  95 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  96 */             if (var == 20)
/*  97 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6); 
/*     */           case 17:
/*  99 */             if (QuestService.questFinish(env, var / 10 - 1)) {
/*     */               
/* 101 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 102 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       }  } 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 113 */     Player player = env.getPlayer();
/* 114 */     QuestState qs = player.getQuestStateList().getQuestState(2114);
/* 115 */     if (qs == null) {
/* 116 */       return false;
/*     */     }
/* 118 */     int var = qs.getQuestVarById(0);
/* 119 */     int targetId = 0;
/* 120 */     if (env.getVisibleObject() instanceof Npc) {
/* 121 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 123 */     if (qs.getStatus() != QuestStatus.START)
/* 124 */       return false; 
/* 125 */     switch (targetId) {
/*     */       
/*     */       case 210734:
/* 128 */         if (var >= 1 && var < 10) {
/*     */           
/* 130 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 131 */           updateQuestStatus(player, qs);
/* 132 */           return true;
/*     */         } 
/* 134 */         if (var == 10) {
/*     */           
/* 136 */           qs.setStatus(QuestStatus.REWARD);
/* 137 */           updateQuestStatus(player, qs);
/* 138 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 139 */           return true;
/*     */         } 
/*     */       case 210380:
/*     */       case 210381:
/* 143 */         if (var >= 11 && var < 20) {
/*     */           
/* 145 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 146 */           updateQuestStatus(player, qs);
/* 147 */           return true;
/*     */         } 
/* 149 */         if (var == 20) {
/*     */           
/* 151 */           qs.setStatus(QuestStatus.REWARD);
/* 152 */           updateQuestStatus(player, qs);
/* 153 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */           return true;
/*     */         }  break;
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2114TheInsectProblem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */