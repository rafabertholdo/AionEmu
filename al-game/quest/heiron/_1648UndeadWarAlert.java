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
/*     */ public class _1648UndeadWarAlert
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1648;
/*     */   
/*     */   public _1648UndeadWarAlert() {
/*  22 */     super(Integer.valueOf(1648));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  28 */     this.qe.setNpcQuestData(204545).addOnQuestStart(1648);
/*  29 */     this.qe.setNpcQuestData(204545).addOnTalkEvent(1648);
/*  30 */     this.qe.setNpcQuestData(204612).addOnTalkEvent(1648);
/*  31 */     this.qe.setNpcQuestData(204500).addOnTalkEvent(1648);
/*  32 */     this.qe.setNpcQuestData(204590).addOnTalkEvent(1648);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  38 */     Player player = env.getPlayer();
/*  39 */     QuestState qs = player.getQuestStateList().getQuestState(1648);
/*     */     
/*  41 */     int targetId = 0;
/*  42 */     if (env.getVisibleObject() instanceof Npc) {
/*  43 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  45 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */     {
/*  47 */       if (targetId == 204545) {
/*     */         
/*  49 */         if (env.getDialogId().intValue() == 25)
/*     */         {
/*  51 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*     */         
/*  54 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*     */     
/*  58 */     if (qs == null) {
/*  59 */       return false;
/*     */     }
/*  61 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  63 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 204612:
/*  67 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  71 */               if (qs.getQuestVarById(0) == 0)
/*     */               {
/*  73 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */               }
/*     */ 
/*     */             
/*     */             case 10000:
/*  78 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  79 */               updateQuestStatus(player, qs);
/*  80 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/*     */               
/*  82 */               return true;
/*     */           } 
/*     */         
/*     */         
/*     */         
/*     */         case 204500:
/*  88 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  92 */               if (qs.getQuestVarById(0) == 1)
/*     */               {
/*  94 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */               }
/*     */ 
/*     */             
/*     */             case 10001:
/*  99 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 100 */               qs.setStatus(QuestStatus.REWARD);
/* 101 */               updateQuestStatus(player, qs);
/* 102 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/*     */               
/* 104 */               return true;
/*     */           } 
/*     */           
/*     */           break;
/*     */       } 
/*     */     
/* 110 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 112 */       if (targetId == 204590) {
/*     */         
/* 114 */         if (env.getDialogId().intValue() == 1009) {
/* 115 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 117 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 120 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1648UndeadWarAlert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */