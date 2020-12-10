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
/*     */ public class _1319PrioritesMoney
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1319;
/*     */   
/*     */   public _1319PrioritesMoney() {
/*  39 */     super(Integer.valueOf(1319));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(203908).addOnQuestStart(1319);
/*  46 */     this.qe.setNpcQuestData(203908).addOnTalkEvent(1319);
/*  47 */     this.qe.setNpcQuestData(203923).addOnTalkEvent(1319);
/*  48 */     this.qe.setNpcQuestData(203910).addOnTalkEvent(1319);
/*  49 */     this.qe.setNpcQuestData(203906).addOnTalkEvent(1319);
/*  50 */     this.qe.setNpcQuestData(203915).addOnTalkEvent(1319);
/*  51 */     this.qe.setNpcQuestData(203907).addOnTalkEvent(1319);
/*  52 */     this.qe.setNpcQuestData(798050).addOnTalkEvent(1319);
/*  53 */     this.qe.setNpcQuestData(798049).addOnTalkEvent(1319);
/*  54 */     this.qe.setNpcQuestData(798046).addOnTalkEvent(1319);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  60 */     Player player = env.getPlayer();
/*  61 */     int targetId = 0;
/*  62 */     if (env.getVisibleObject() instanceof Npc)
/*  63 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  64 */     QuestState qs = player.getQuestStateList().getQuestState(1319);
/*  65 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  67 */       if (targetId == 203908) {
/*     */         
/*  69 */         if (env.getDialogId().intValue() == 25) {
/*  70 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  72 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } else {
/*  75 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/*  77 */         if (env.getDialogId().intValue() == 25)
/*  78 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
/*  79 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  81 */           qs.setQuestVar(8);
/*  82 */           qs.setStatus(QuestStatus.REWARD);
/*  83 */           updateQuestStatus(player, qs);
/*  84 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  87 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */       
/*  90 */       if (targetId == 203923) {
/*     */ 
/*     */         
/*  93 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */         {
/*  95 */           if (env.getDialogId().intValue() == 25)
/*  96 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  97 */           if (env.getDialogId().intValue() == 10000) {
/*     */             
/*  99 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 100 */             updateQuestStatus(player, qs);
/* 101 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 103 */             return true;
/*     */           } 
/*     */           
/* 106 */           return defaultQuestStartDialog(env);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 111 */       else if (targetId == 203910) {
/*     */         
/* 113 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */         {
/* 115 */           if (env.getDialogId().intValue() == 25)
/* 116 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 117 */           if (env.getDialogId().intValue() == 10001) {
/*     */             
/* 119 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 120 */             updateQuestStatus(player, qs);
/* 121 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 123 */             return true;
/*     */           } 
/*     */           
/* 126 */           return defaultQuestStartDialog(env);
/*     */         }
/*     */       
/*     */       }
/* 130 */       else if (targetId == 203906) {
/*     */         
/* 132 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */         {
/* 134 */           if (env.getDialogId().intValue() == 25)
/* 135 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 136 */           if (env.getDialogId().intValue() == 10002) {
/*     */             
/* 138 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 139 */             updateQuestStatus(player, qs);
/* 140 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 142 */             return true;
/*     */           } 
/*     */           
/* 145 */           return defaultQuestStartDialog(env);
/*     */         }
/*     */       
/*     */       }
/* 149 */       else if (targetId == 203915) {
/*     */         
/* 151 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3)
/*     */         {
/* 153 */           if (env.getDialogId().intValue() == 25)
/* 154 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 155 */           if (env.getDialogId().intValue() == 10003) {
/*     */             
/* 157 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 158 */             updateQuestStatus(player, qs);
/* 159 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 161 */             return true;
/*     */           } 
/*     */           
/* 164 */           return defaultQuestStartDialog(env);
/*     */         }
/*     */       
/*     */       }
/* 168 */       else if (targetId == 203907) {
/*     */         
/* 170 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4)
/*     */         {
/* 172 */           if (env.getDialogId().intValue() == 25)
/* 173 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 174 */           if (env.getDialogId().intValue() == 10004) {
/*     */             
/* 176 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 177 */             updateQuestStatus(player, qs);
/* 178 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 180 */             return true;
/*     */           } 
/*     */           
/* 183 */           return defaultQuestStartDialog(env);
/*     */         }
/*     */       
/*     */       }
/* 187 */       else if (targetId == 798050) {
/*     */         
/* 189 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5)
/*     */         {
/* 191 */           if (env.getDialogId().intValue() == 25)
/* 192 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 193 */           if (env.getDialogId().intValue() == 10005) {
/*     */             
/* 195 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 196 */             updateQuestStatus(player, qs);
/* 197 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 199 */             return true;
/*     */           } 
/*     */           
/* 202 */           return defaultQuestStartDialog(env);
/*     */         }
/*     */       
/*     */       }
/* 206 */       else if (targetId == 798049) {
/*     */         
/* 208 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 6)
/*     */         {
/* 210 */           if (env.getDialogId().intValue() == 25)
/* 211 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 212 */           if (env.getDialogId().intValue() == 10006) {
/*     */             
/* 214 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 215 */             updateQuestStatus(player, qs);
/* 216 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 218 */             return true;
/*     */           } 
/*     */           
/* 221 */           return defaultQuestStartDialog(env);
/*     */         }
/*     */       
/*     */       }
/* 225 */       else if (targetId == 798046) {
/*     */         
/* 227 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 7) {
/*     */           
/* 229 */           if (env.getDialogId().intValue() == 25)
/* 230 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
/* 231 */           if (env.getDialogId().intValue() == 10007) {
/*     */             
/* 233 */             qs.setStatus(QuestStatus.REWARD);
/* 234 */             updateQuestStatus(player, qs);
/* 235 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 237 */             return true;
/*     */           } 
/*     */           
/* 240 */           return defaultQuestStartDialog(env);
/*     */         } 
/*     */       } 
/*     */     } 
/* 244 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1319PrioritesMoney.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */