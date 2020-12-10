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
/*     */ public class _1471FakeStigma
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1471;
/*     */   
/*     */   public _1471FakeStigma() {
/*  38 */     super(Integer.valueOf(1471));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203991).addOnQuestStart(1471);
/*  45 */     this.qe.setNpcQuestData(203991).addOnTalkEvent(1471);
/*  46 */     this.qe.setNpcQuestData(203703).addOnTalkEvent(1471);
/*  47 */     this.qe.setNpcQuestData(798048).addOnTalkEvent(1471);
/*  48 */     this.qe.setNpcQuestData(798024).addOnTalkEvent(1471);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     int targetId = 0;
/*  56 */     if (env.getVisibleObject() instanceof Npc)
/*  57 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1471);
/*  59 */     if (targetId == 203991) {
/*     */       
/*  61 */       if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */       {
/*  63 */         if (env.getDialogId().intValue() == 25) {
/*  64 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  66 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/*  70 */     else if (targetId == 203703) {
/*     */       
/*  72 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  74 */         if (env.getDialogId().intValue() == 25)
/*  75 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  76 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  78 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  79 */           updateQuestStatus(player, qs);
/*  80 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  82 */           return true;
/*     */         } 
/*     */         
/*  85 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  87 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  89 */         if (env.getDialogId().intValue() == 25)
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  91 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  93 */           qs.setQuestVar(3);
/*  94 */           qs.setStatus(QuestStatus.REWARD);
/*  95 */           updateQuestStatus(player, qs);
/*  96 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  99 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 101 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 103 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/* 106 */     else if (targetId == 798024) {
/*     */       
/* 108 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 110 */         if (env.getDialogId().intValue() == 25)
/* 111 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 112 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 114 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 115 */           updateQuestStatus(player, qs);
/* 116 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 118 */           return true;
/*     */         } 
/*     */         
/* 121 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 124 */     } else if (targetId == 798048) {
/*     */       
/* 126 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
/*     */         
/* 128 */         if (env.getDialogId().intValue() == 25)
/* 129 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 130 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 132 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 133 */           updateQuestStatus(player, qs);
/* 134 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 136 */           return true;
/*     */         } 
/*     */         
/* 139 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 144 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1471FakeStigma.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */