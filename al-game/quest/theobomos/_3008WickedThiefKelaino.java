/*     */ package quest.theobomos;
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
/*     */ public class _3008WickedThiefKelaino
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3008;
/*     */   
/*     */   public _3008WickedThiefKelaino() {
/*  37 */     super(Integer.valueOf(3008));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  43 */     this.qe.setNpcQuestData(798150).addOnQuestStart(3008);
/*  44 */     this.qe.setNpcQuestData(798150).addOnTalkEvent(3008);
/*  45 */     this.qe.setNpcQuestData(798138).addOnTalkEvent(3008);
/*  46 */     this.qe.setNpcQuestData(798146).addOnTalkEvent(3008);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     int targetId = 0;
/*  54 */     if (env.getVisibleObject() instanceof Npc)
/*  55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(3008);
/*  57 */     if (targetId == 798150) {
/*     */       
/*  59 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  61 */         if (env.getDialogId().intValue() == 25) {
/*  62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  64 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  66 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  68 */         if (env.getDialogId().intValue() == 25)
/*  69 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  70 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  72 */           qs.setQuestVar(3);
/*  73 */           updateQuestStatus(player, qs);
/*  74 */           qs.setStatus(QuestStatus.REWARD);
/*  75 */           updateQuestStatus(player, qs);
/*  76 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  79 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  81 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  83 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  86 */     else if (targetId == 798138) {
/*     */       
/*  88 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  90 */         if (env.getDialogId().intValue() == 25)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  92 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  94 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  95 */           updateQuestStatus(player, qs);
/*  96 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  97 */           return true;
/*     */         } 
/*     */         
/* 100 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 103 */     } else if (targetId == 798146) {
/*     */       
/* 105 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 107 */         if (env.getDialogId().intValue() == 25)
/* 108 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 109 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 111 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 112 */           updateQuestStatus(player, qs);
/* 113 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 114 */           return true;
/*     */         } 
/*     */         
/* 117 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 120 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 122 */       if (targetId == 798150)
/*     */       {
/* 124 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3008WickedThiefKelaino.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */