/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ public class _1034DisappearingAether
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1034;
/*     */   
/*     */   public _1034DisappearingAether() {
/*  44 */     super(Integer.valueOf(1034));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.addQuestLvlUp(1034);
/*  51 */     this.qe.setNpcQuestData(203903).addOnTalkEvent(1034);
/*  52 */     this.qe.setNpcQuestData(204501).addOnTalkEvent(1034);
/*  53 */     this.qe.setNpcQuestData(700149).addOnTalkEvent(1034);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(1034);
/*  61 */     boolean lvlCheck = QuestService.checkLevelRequirement(1034, player.getCommonData().getLevel());
/*  62 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  63 */       return false;
/*     */     }
/*  65 */     QuestState qs2 = player.getQuestStateList().getQuestState(1300);
/*  66 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  67 */       return false; 
/*  68 */     qs.setStatus(QuestStatus.START);
/*  69 */     updateQuestStatus(player, qs);
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  76 */     final Player player = env.getPlayer();
/*  77 */     final QuestState qs = player.getQuestStateList().getQuestState(1034);
/*  78 */     if (qs == null) {
/*  79 */       return false;
/*     */     }
/*  81 */     int var = qs.getQuestVarById(0);
/*  82 */     int targetId = 0;
/*  83 */     if (env.getVisibleObject() instanceof Npc) {
/*  84 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  86 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  88 */       if (targetId == 203903) {
/*     */         
/*  90 */         if (env.getDialogId().intValue() == -1)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  92 */         if (env.getDialogId().intValue() == 1009)
/*  93 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  94 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  96 */       return false;
/*     */     } 
/*  98 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 100 */       return false;
/*     */     }
/* 102 */     if (targetId == 203903) {
/*     */       
/* 104 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 107 */           if (var == 0)
/* 108 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 110 */           if (var == 0) {
/*     */             
/* 112 */             qs.setQuestVarById(0, var + 1);
/* 113 */             updateQuestStatus(player, qs);
/* 114 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 115 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 119 */     } else if (targetId == 204032) {
/*     */       
/* 121 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 124 */           if (var == 1)
/* 125 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 126 */           if (var == 3)
/* 127 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 128 */           if (var == 4)
/* 129 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 33:
/* 131 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 133 */             qs.setStatus(QuestStatus.REWARD);
/* 134 */             updateQuestStatus(player, qs);
/* 135 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
/*     */           } 
/*     */           
/* 138 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */         case 1353:
/* 140 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 179));
/*     */           break;
/*     */         case 10001:
/* 143 */           if (var == 1) {
/*     */             
/* 145 */             qs.setQuestVarById(0, var + 1);
/* 146 */             updateQuestStatus(player, qs);
/* 147 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 148 */             return true;
/*     */           } 
/*     */         case 10002:
/* 151 */           if (var == 3) {
/*     */             
/* 153 */             qs.setQuestVarById(0, var + 1);
/* 154 */             updateQuestStatus(player, qs);
/* 155 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 156 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 160 */     } else if (targetId == 700149 && var == 2) {
/*     */       
/* 162 */       if (env.getDialogId().intValue() == -1) {
/*     */         
/* 164 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 165 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 166 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 167 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 171 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 172 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 173 */                 qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 174 */                 _1034DisappearingAether.this.updateQuestStatus(player, qs);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/* 179 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1034DisappearingAether.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */