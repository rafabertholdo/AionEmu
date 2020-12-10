/*     */ package quest.heiron;
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
/*     */ public class _1578WhereDoRotronsComeFrom
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1578;
/*     */   
/*     */   public _1578WhereDoRotronsComeFrom() {
/*  37 */     super(Integer.valueOf(1578));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  43 */     this.qe.setNpcQuestData(730025).addOnQuestStart(1578);
/*  44 */     this.qe.setNpcQuestData(730025).addOnTalkEvent(1578);
/*  45 */     this.qe.setNpcQuestData(730024).addOnTalkEvent(1578);
/*  46 */     this.qe.setNpcQuestData(204560).addOnTalkEvent(1578);
/*  47 */     this.qe.setNpcQuestData(204579).addOnTalkEvent(1578);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*  55 */     if (env.getVisibleObject() instanceof Npc)
/*  56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1578);
/*  58 */     if (targetId == 730025) {
/*     */       
/*  60 */       if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */       {
/*  62 */         if (env.getDialogId().intValue() == 25) {
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  65 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  68 */     } else if (targetId == 730024) {
/*     */       
/*  70 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  72 */         if (env.getDialogId().intValue() == 25)
/*  73 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  74 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  76 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  77 */           updateQuestStatus(player, qs);
/*  78 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  80 */           return true;
/*     */         } 
/*     */         
/*  83 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  86 */     } else if (targetId == 204560) {
/*     */       
/*  88 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  90 */         if (env.getDialogId().intValue() == 25)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  92 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  94 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  95 */           updateQuestStatus(player, qs);
/*  96 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 1693));
/*     */           
/*  98 */           return true;
/*     */         } 
/*     */         
/* 101 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 104 */     } else if (targetId == 204579) {
/*     */       
/* 106 */       if (qs != null) {
/*     */         
/* 108 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 109 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 110 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 112 */           qs.setQuestVar(3);
/* 113 */           qs.setStatus(QuestStatus.REWARD);
/* 114 */           updateQuestStatus(player, qs);
/* 115 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 118 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1578WhereDoRotronsComeFrom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */