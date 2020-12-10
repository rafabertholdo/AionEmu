/*     */ package quest.sanctum;
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
/*     */ public class _1928ChasingaCriminal
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1928;
/*     */   
/*     */   public _1928ChasingaCriminal() {
/*  38 */     super(Integer.valueOf(1928));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203845).addOnQuestStart(1928);
/*  45 */     this.qe.setNpcQuestData(203845).addOnTalkEvent(1928);
/*  46 */     this.qe.setNpcQuestData(203063).addOnTalkEvent(1928);
/*  47 */     this.qe.setNpcQuestData(203170).addOnTalkEvent(1928);
/*  48 */     this.qe.setNpcQuestData(798150).addOnTalkEvent(1928);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     int targetId = 0;
/*  56 */     if (env.getVisibleObject() instanceof Npc)
/*  57 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1928);
/*  59 */     if (targetId == 203845) {
/*     */       
/*  61 */       if (qs == null)
/*     */       {
/*  63 */         if (env.getDialogId().intValue() == 25) {
/*  64 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  66 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  69 */     } else if (targetId == 203063) {
/*     */       
/*  71 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  73 */         if (env.getDialogId().intValue() == 25)
/*  74 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  75 */         if (env.getDialogId().intValue() == 10001) {
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
/*  87 */     } else if (targetId == 203170) {
/*     */       
/*  89 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  91 */         if (env.getDialogId().intValue() == 25)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  93 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  95 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  96 */           updateQuestStatus(player, qs);
/*  97 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  99 */           return true;
/*     */         } 
/*     */         
/* 102 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 105 */     } else if (targetId == 798150) {
/*     */       
/* 107 */       if (qs != null) {
/*     */         
/* 109 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 110 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 111 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 113 */           qs.setQuestVar(2);
/* 114 */           qs.setStatus(QuestStatus.REWARD);
/* 115 */           updateQuestStatus(player, qs);
/* 116 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 119 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 122 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_1928ChasingaCriminal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */