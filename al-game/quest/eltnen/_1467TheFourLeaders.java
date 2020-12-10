/*     */ package quest.eltnen;
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
/*     */ public class _1467TheFourLeaders
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1467;
/*     */   
/*     */   public _1467TheFourLeaders() {
/*  39 */     super(Integer.valueOf(1467));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(204045).addOnQuestStart(1467);
/*  46 */     this.qe.setNpcQuestData(204045).addOnTalkEvent(1467);
/*  47 */     this.qe.setNpcQuestData(211696).addOnKillEvent(1467);
/*  48 */     this.qe.setNpcQuestData(211697).addOnKillEvent(1467);
/*  49 */     this.qe.setNpcQuestData(211698).addOnKillEvent(1467);
/*  50 */     this.qe.setNpcQuestData(211699).addOnKillEvent(1467);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1467);
/*     */     
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc) {
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  63 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */     {
/*  65 */       if (targetId == 204045) {
/*     */         
/*  67 */         switch (env.getDialogId().intValue()) {
/*     */ 
/*     */           
/*     */           case 25:
/*  71 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*     */ 
/*     */           
/*     */           case 1002:
/*  75 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */ 
/*     */           
/*     */           case 10000:
/*  79 */             QuestService.startQuest(env, QuestStatus.START);
/*  80 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  81 */             updateQuestStatus(player, qs);
/*  82 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/*  84 */             return true;
/*     */ 
/*     */           
/*     */           case 10001:
/*  88 */             QuestService.startQuest(env, QuestStatus.START);
/*  89 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 2);
/*  90 */             updateQuestStatus(player, qs);
/*  91 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/*  93 */             return true;
/*     */ 
/*     */           
/*     */           case 10002:
/*  97 */             QuestService.startQuest(env, QuestStatus.START);
/*  98 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 3);
/*  99 */             updateQuestStatus(player, qs);
/* 100 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 102 */             return true;
/*     */ 
/*     */           
/*     */           case 10003:
/* 106 */             QuestService.startQuest(env, QuestStatus.START);
/* 107 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 4);
/* 108 */             updateQuestStatus(player, qs);
/* 109 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 111 */             return true;
/*     */         } 
/*     */         
/* 114 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 119 */     if (qs == null) {
/* 120 */       return false;
/*     */     }
/* 122 */     if (qs.getStatus() == QuestStatus.REWARD)
/*     */     {
/* 124 */       if (targetId == 204045)
/*     */       {
/* 126 */         switch (env.getDialogId().intValue()) {
/*     */ 
/*     */           
/*     */           case -1:
/* 130 */             switch (qs.getQuestVarById(0)) {
/*     */ 
/*     */               
/*     */               case 1:
/* 134 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */ 
/*     */               
/*     */               case 2:
/* 138 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */ 
/*     */               
/*     */               case 3:
/* 142 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */ 
/*     */               
/*     */               case 4:
/* 146 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 8);
/*     */             } 
/*     */           
/*     */           
/*     */           
/*     */           case 17:
/* 152 */             QuestService.questFinish(env, qs.getQuestVarById(0) - 1);
/* 153 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 155 */             return true;
/*     */         } 
/*     */       
/*     */       }
/*     */     }
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 166 */     Player player = env.getPlayer();
/* 167 */     QuestState qs = player.getQuestStateList().getQuestState(1467);
/*     */     
/* 169 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/*     */     {
/* 171 */       return false;
/*     */     }
/*     */     
/* 174 */     int var = 0;
/* 175 */     int targetId = 0;
/*     */     
/* 177 */     if (env.getVisibleObject() instanceof Npc) {
/* 178 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 180 */     switch (targetId) {
/*     */ 
/*     */       
/*     */       case 211696:
/* 184 */         if (qs.getQuestVarById(0) == 1)
/*     */         {
/* 186 */           if (var == 0) {
/*     */             
/* 188 */             var = 1;
/* 189 */             qs.setStatus(QuestStatus.REWARD);
/* 190 */             updateQuestStatus(player, qs);
/* 191 */             return true;
/*     */           } 
/*     */         }
/*     */ 
/*     */       
/*     */       case 211697:
/* 197 */         if (qs.getQuestVarById(0) == 2)
/*     */         {
/* 199 */           if (var == 0) {
/*     */             
/* 201 */             var = 1;
/* 202 */             qs.setStatus(QuestStatus.REWARD);
/* 203 */             updateQuestStatus(player, qs);
/* 204 */             return true;
/*     */           } 
/*     */         }
/*     */ 
/*     */       
/*     */       case 211698:
/* 210 */         if (qs.getQuestVarById(0) == 3)
/*     */         {
/* 212 */           if (var == 0) {
/*     */             
/* 214 */             var = 1;
/* 215 */             qs.setStatus(QuestStatus.REWARD);
/* 216 */             updateQuestStatus(player, qs);
/* 217 */             return true;
/*     */           } 
/*     */         }
/*     */ 
/*     */       
/*     */       case 211699:
/* 223 */         if (qs.getQuestVarById(0) == 4)
/*     */         {
/* 225 */           if (var == 0) {
/*     */             
/* 227 */             var = 1;
/* 228 */             qs.setStatus(QuestStatus.REWARD);
/* 229 */             updateQuestStatus(player, qs);
/* 230 */             return true;
/*     */           } 
/*     */         }
/*     */         break;
/*     */     } 
/* 235 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1467TheFourLeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */