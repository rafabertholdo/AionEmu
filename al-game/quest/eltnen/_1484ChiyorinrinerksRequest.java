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
/*     */ public class _1484ChiyorinrinerksRequest
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1484;
/*     */   
/*     */   public _1484ChiyorinrinerksRequest() {
/*  37 */     super(Integer.valueOf(1484));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  43 */     this.qe.setNpcQuestData(798127).addOnQuestStart(1484);
/*  44 */     this.qe.setNpcQuestData(798127).addOnTalkEvent(1484);
/*  45 */     this.qe.setNpcQuestData(204045).addOnTalkEvent(1484);
/*  46 */     this.qe.setNpcQuestData(204048).addOnTalkEvent(1484);
/*  47 */     this.qe.setNpcQuestData(204011).addOnTalkEvent(1484);
/*  48 */     this.qe.setNpcQuestData(798126).addOnTalkEvent(1484);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     int targetId = 0;
/*  56 */     if (env.getVisibleObject() instanceof Npc)
/*  57 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1484);
/*  59 */     if (targetId == 798127) {
/*     */       
/*  61 */       if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */       {
/*  63 */         if (env.getDialogId().intValue() == 25) {
/*  64 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  66 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  69 */     } else if (targetId == 204045) {
/*     */       
/*  71 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  73 */         if (env.getDialogId().intValue() == 25)
/*  74 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  75 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  77 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  78 */           updateQuestStatus(player, qs);
/*  79 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  81 */           return true;
/*     */         } 
/*     */         
/*  84 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  87 */     } else if (targetId == 204048) {
/*     */       
/*  89 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  91 */         if (env.getDialogId().intValue() == 25)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  93 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  95 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  96 */           updateQuestStatus(player, qs);
/*  97 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 1693));
/*     */           
/*  99 */           return true;
/*     */         } 
/*     */         
/* 102 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 105 */     } else if (targetId == 204011) {
/*     */       
/* 107 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/* 109 */         if (env.getDialogId().intValue() == 25)
/* 110 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 111 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 113 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 114 */           updateQuestStatus(player, qs);
/* 115 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 1693));
/*     */           
/* 117 */           return true;
/*     */         } 
/*     */         
/* 120 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 123 */     } else if (targetId == 798126) {
/*     */       
/* 125 */       if (qs != null) {
/*     */         
/* 127 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 128 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 129 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 131 */           qs.setQuestVar(3);
/* 132 */           qs.setStatus(QuestStatus.REWARD);
/* 133 */           updateQuestStatus(player, qs);
/* 134 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 137 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 140 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1484ChiyorinrinerksRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */