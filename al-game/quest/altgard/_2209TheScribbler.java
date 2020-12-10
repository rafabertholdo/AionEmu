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
/*     */ public class _2209TheScribbler
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2209;
/*     */   
/*     */   public _2209TheScribbler() {
/*  38 */     super(Integer.valueOf(2209));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203555).addOnQuestStart(2209);
/*  45 */     this.qe.setNpcQuestData(203555).addOnTalkEvent(2209);
/*  46 */     this.qe.setNpcQuestData(203562).addOnTalkEvent(2209);
/*  47 */     this.qe.setNpcQuestData(203592).addOnTalkEvent(2209);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*  55 */     if (env.getVisibleObject() instanceof Npc)
/*  56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(2209);
/*  58 */     if (qs == null) {
/*     */       
/*  60 */       if (targetId == 203555)
/*     */       {
/*  62 */         if (env.getDialogId().intValue() == 25) {
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  65 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  68 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  70 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203562:
/*  74 */           if (qs.getQuestVarById(0) == 0) {
/*     */             
/*  76 */             if (env.getDialogId().intValue() == 25)
/*  77 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  78 */             if (env.getDialogId().intValue() == 10000) {
/*     */               
/*  80 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  81 */               updateQuestStatus(player, qs);
/*  82 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  83 */               return true;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 203572:
/*  90 */           if (qs.getQuestVarById(0) == 1) {
/*     */             
/*  92 */             if (env.getDialogId().intValue() == 25)
/*  93 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  94 */             if (env.getDialogId().intValue() == 10001) {
/*     */               
/*  96 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  97 */               updateQuestStatus(player, qs);
/*  98 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  99 */               return true;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 203592:
/* 106 */           if (qs.getQuestVarById(0) == 2) {
/*     */             
/* 108 */             if (env.getDialogId().intValue() == 25)
/* 109 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 110 */             if (env.getDialogId().intValue() == 10002) {
/*     */               
/* 112 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 113 */               updateQuestStatus(player, qs);
/* 114 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 115 */               return true;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 203555:
/* 122 */           if (qs.getQuestVarById(0) == 3) {
/*     */             
/* 124 */             if (env.getDialogId().intValue() == 25)
/* 125 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 126 */             if (env.getDialogId().intValue() == 1009) {
/*     */               
/* 128 */               qs.setStatus(QuestStatus.REWARD);
/* 129 */               updateQuestStatus(player, qs);
/* 130 */               return defaultQuestEndDialog(env);
/*     */             } 
/*     */             
/* 133 */             return defaultQuestEndDialog(env);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 138 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 140 */       if (targetId == 203555)
/* 141 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 143 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2209TheScribbler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */