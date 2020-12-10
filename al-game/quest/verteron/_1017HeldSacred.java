/*     */ package quest.verteron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class _1017HeldSacred
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1017;
/*     */   
/*     */   public _1017HeldSacred() {
/*  39 */     super(Integer.valueOf(1017));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.addQuestLvlUp(1017);
/*  46 */     this.qe.setNpcQuestData(203178).addOnTalkEvent(1017);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     QuestState qs = player.getQuestStateList().getQuestState(1017);
/*  54 */     boolean lvlCheck = QuestService.checkLevelRequirement(1017, player.getCommonData().getLevel());
/*  55 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  56 */       return false; 
/*  57 */     qs.setStatus(QuestStatus.START);
/*  58 */     updateQuestStatus(player, qs);
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  65 */     Player player = env.getPlayer();
/*  66 */     QuestState qs = player.getQuestStateList().getQuestState(1017);
/*  67 */     if (qs == null) {
/*  68 */       return false;
/*     */     }
/*  70 */     int var = qs.getQuestVarById(0);
/*  71 */     int targetId = 0;
/*  72 */     if (env.getVisibleObject() instanceof Npc) {
/*  73 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  75 */     if (targetId == 203178 && qs.getStatus() == QuestStatus.START) {
/*     */       
/*  77 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/*  80 */           if (var == 0)
/*  81 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  82 */           if (var == 1)
/*  83 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  84 */           return false;
/*     */         
/*     */         case 10000:
/*  87 */           if (var == 0) {
/*     */             
/*  89 */             qs.setQuestVarById(0, var + 1);
/*  90 */             updateQuestStatus(player, qs);
/*  91 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  92 */             return true;
/*     */           } 
/*     */         
/*     */         case 33:
/*  96 */           if (var == 1) {
/*     */             
/*  98 */             if (QuestService.collectItemCheck(env, true)) {
/*     */               
/* 100 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 101 */               qs.setStatus(QuestStatus.REWARD);
/* 102 */               updateQuestStatus(player, qs);
/* 103 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */             } 
/*     */             
/* 106 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
/*     */           } 
/*     */           break;
/*     */       } 
/* 110 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 112 */       if (targetId == 203178)
/* 113 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1017HeldSacred.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */