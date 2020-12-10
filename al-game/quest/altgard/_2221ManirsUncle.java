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
/*     */ import com.aionemu.gameserver.services.ItemService;
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
/*     */ public class _2221ManirsUncle
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2221;
/*     */   
/*     */   public _2221ManirsUncle() {
/*  40 */     super(Integer.valueOf(2221));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.setNpcQuestData(203607).addOnQuestStart(2221);
/*  47 */     this.qe.setNpcQuestData(203607).addOnTalkEvent(2221);
/*  48 */     this.qe.setNpcQuestData(203608).addOnTalkEvent(2221);
/*  49 */     this.qe.setNpcQuestData(700214).addOnTalkEvent(2221);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  55 */     Player player = env.getPlayer();
/*  56 */     int targetId = 0;
/*  57 */     if (env.getVisibleObject() instanceof Npc)
/*  58 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  59 */     QuestState qs = player.getQuestStateList().getQuestState(2221);
/*  60 */     if (qs == null) {
/*     */       
/*  62 */       if (targetId == 203607)
/*     */       {
/*  64 */         if (env.getDialogId().intValue() == 25) {
/*  65 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  67 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  70 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  72 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203608:
/*  76 */           if (qs.getQuestVarById(0) == 0) {
/*     */             
/*  78 */             if (env.getDialogId().intValue() == 25)
/*  79 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  80 */             if (env.getDialogId().intValue() == 10000) {
/*     */               
/*  82 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  83 */               updateQuestStatus(player, qs);
/*  84 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  85 */               return true;
/*     */             } 
/*     */           } 
/*  88 */           if (qs.getQuestVarById(0) == 2) {
/*     */             
/*  90 */             if (env.getDialogId().intValue() == 25)
/*  91 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  92 */             if (env.getDialogId().intValue() == 1009) {
/*     */               
/*  94 */               ItemService.removeItemFromInventoryByItemId(player, 182203215);
/*  95 */               qs.setStatus(QuestStatus.REWARD);
/*  96 */               updateQuestStatus(player, qs);
/*  97 */               return defaultQuestEndDialog(env);
/*     */             } 
/*     */             
/* 100 */             return defaultQuestEndDialog(env);
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 700214:
/* 105 */           if ((qs.getQuestVarById(0) == 1 || qs.getQuestVarById(0) == 2) && env.getDialogId().intValue() == -1) {
/*     */             
/* 107 */             qs.setQuestVarById(0, 2);
/* 108 */             updateQuestStatus(player, qs);
/*     */           } 
/* 110 */           return true;
/*     */       } 
/*     */     
/* 113 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 115 */       if (targetId == 203608)
/* 116 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2221ManirsUncle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */