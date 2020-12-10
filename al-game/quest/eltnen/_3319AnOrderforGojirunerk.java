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
/*     */ public class _3319AnOrderforGojirunerk
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3319;
/*     */   
/*     */   public _3319AnOrderforGojirunerk() {
/*  22 */     super(Integer.valueOf(3319));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  28 */     this.qe.setNpcQuestData(798050).addOnQuestStart(3319);
/*  29 */     this.qe.setNpcQuestData(798050).addOnTalkEvent(3319);
/*  30 */     this.qe.setNpcQuestData(798138).addOnTalkEvent(3319);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  36 */     Player player = env.getPlayer();
/*  37 */     QuestState qs = player.getQuestStateList().getQuestState(3319);
/*     */     
/*  39 */     int targetId = 0;
/*  40 */     if (env.getVisibleObject() instanceof Npc) {
/*  41 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  43 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */     {
/*  45 */       if (targetId == 798050) {
/*     */         
/*  47 */         if (env.getDialogId().intValue() == 25)
/*     */         {
/*  49 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*     */         
/*  52 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*     */     
/*  56 */     if (qs == null) {
/*  57 */       return false;
/*     */     }
/*  59 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  61 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 798138:
/*  65 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  69 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */ 
/*     */             
/*     */             case 10000:
/*  73 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  74 */               updateQuestStatus(player, qs);
/*  75 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/*  77 */               return true;
/*     */           } 
/*     */         
/*     */         
/*     */         
/*     */         case 798050:
/*  83 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  87 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */ 
/*     */             
/*     */             case 1009:
/*  91 */               qs.setQuestVar(2);
/*  92 */               qs.setStatus(QuestStatus.REWARD);
/*  93 */               updateQuestStatus(player, qs);
/*  94 */               return defaultQuestEndDialog(env);
/*     */           } 
/*     */           
/*  97 */           return defaultQuestEndDialog(env);
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/* 102 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 104 */       if (targetId == 798050) {
/*     */         
/* 106 */         switch (env.getDialogId().intValue()) {
/*     */ 
/*     */           
/*     */           case 1009:
/* 110 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         } 
/*     */         
/* 113 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_3319AnOrderforGojirunerk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */