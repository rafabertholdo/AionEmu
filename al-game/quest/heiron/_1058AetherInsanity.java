/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
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
/*     */ public class _1058AetherInsanity
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1058;
/*     */   
/*     */   public _1058AetherInsanity() {
/*  40 */     super(Integer.valueOf(1058));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(1058);
/*  47 */     this.qe.setNpcQuestData(204020).addOnTalkEvent(1058);
/*  48 */     this.qe.setNpcQuestData(204501).addOnTalkEvent(1058);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     QuestState qs = player.getQuestStateList().getQuestState(1058);
/*  56 */     boolean lvlCheck = QuestService.checkLevelRequirement(1058, player.getCommonData().getLevel());
/*  57 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  58 */       return false;
/*     */     }
/*  60 */     QuestState qs2 = player.getQuestStateList().getQuestState(1500);
/*  61 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  62 */       return false; 
/*  63 */     qs.setStatus(QuestStatus.START);
/*  64 */     updateQuestStatus(player, qs);
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  71 */     Player player = env.getPlayer();
/*  72 */     QuestState qs = player.getQuestStateList().getQuestState(1058);
/*  73 */     if (qs == null) {
/*  74 */       return false;
/*     */     }
/*  76 */     int var = qs.getQuestVarById(0);
/*  77 */     int targetId = 0;
/*  78 */     if (env.getVisibleObject() instanceof Npc) {
/*  79 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  81 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  83 */       if (targetId == 204501) {
/*  84 */         return defaultQuestEndDialog(env);
/*     */       }
/*  86 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  88 */       return false;
/*     */     } 
/*  90 */     if (targetId == 204020) {
/*     */       
/*  92 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/*  95 */           if (var == 0)
/*  96 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/*  98 */           if (var == 0) {
/*     */             
/* 100 */             qs.setQuestVarById(0, var + 1);
/* 101 */             updateQuestStatus(player, qs);
/* 102 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 103 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 107 */     } else if (targetId == 204501) {
/*     */       
/* 109 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 112 */           if (var == 1)
/* 113 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 114 */           if (var == 2)
/* 115 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 33:
/* 117 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 119 */             qs.setStatus(QuestStatus.REWARD);
/* 120 */             updateQuestStatus(player, qs);
/* 121 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           
/* 124 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 1353:
/* 126 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 191));
/*     */           break;
/*     */         case 10001:
/* 129 */           if (var == 1) {
/*     */             
/* 131 */             qs.setQuestVarById(0, var + 1);
/* 132 */             updateQuestStatus(player, qs);
/* 133 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 134 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 138 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1058AetherInsanity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */