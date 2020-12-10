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
/*     */ public class _1900RingImbuedAether
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1900;
/*     */   
/*     */   public _1900RingImbuedAether() {
/*  38 */     super(Integer.valueOf(1900));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203757).addOnQuestStart(1900);
/*  45 */     this.qe.setNpcQuestData(203757).addOnTalkEvent(1900);
/*  46 */     this.qe.setNpcQuestData(203739).addOnTalkEvent(1900);
/*  47 */     this.qe.setNpcQuestData(203766).addOnTalkEvent(1900);
/*  48 */     this.qe.setNpcQuestData(203797).addOnTalkEvent(1900);
/*  49 */     this.qe.setNpcQuestData(203795).addOnTalkEvent(1900);
/*  50 */     this.qe.setNpcQuestData(203830).addOnTalkEvent(1900);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     int targetId = 0;
/*  58 */     if (env.getVisibleObject() instanceof Npc)
/*  59 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(1900);
/*  61 */     if (targetId == 203757) {
/*     */       
/*  63 */       if (qs == null)
/*     */       {
/*  65 */         if (env.getDialogId().intValue() == 25) {
/*  66 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  68 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  71 */     } else if (targetId == 203739) {
/*     */       
/*  73 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  75 */         if (env.getDialogId().intValue() == 25)
/*  76 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  77 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  79 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  80 */           updateQuestStatus(player, qs);
/*  81 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  83 */           return true;
/*     */         } 
/*     */         
/*  86 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  89 */     } else if (targetId == 203766) {
/*     */       
/*  91 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  93 */         if (env.getDialogId().intValue() == 25)
/*  94 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  95 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  97 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  98 */           updateQuestStatus(player, qs);
/*  99 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 101 */           return true;
/*     */         } 
/*     */         
/* 104 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 107 */     } else if (targetId == 203797) {
/*     */       
/* 109 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/* 111 */         if (env.getDialogId().intValue() == 25)
/* 112 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 113 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 115 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 116 */           updateQuestStatus(player, qs);
/* 117 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 119 */           return true;
/*     */         } 
/*     */         
/* 122 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 125 */     } else if (targetId == 203795) {
/*     */       
/* 127 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3)
/*     */       {
/* 129 */         if (env.getDialogId().intValue() == 25)
/* 130 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 131 */         if (env.getDialogId().intValue() == 10003) {
/*     */           
/* 133 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 134 */           updateQuestStatus(player, qs);
/* 135 */           qs.setStatus(QuestStatus.REWARD);
/* 136 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 138 */           return true;
/*     */         } 
/*     */         
/* 141 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 144 */     } else if (targetId == 203830) {
/*     */       
/* 146 */       if (qs != null) {
/*     */         
/* 148 */         if (env.getDialogId().intValue() == -1 && qs.getStatus() == QuestStatus.REWARD)
/* 149 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 150 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 152 */           qs.setQuestVar(4);
/* 153 */           qs.setStatus(QuestStatus.REWARD);
/* 154 */           updateQuestStatus(player, qs);
/* 155 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 158 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_1900RingImbuedAether.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */