/*     */ package quest.reshanta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
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
/*     */ public class _2075PuttingontheSpeed
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2075;
/*  39 */   private static final int[] npc_ids = new int[] { 278034, 279004, 279024, 279006 };
/*     */ 
/*     */   
/*     */   public _2075PuttingontheSpeed() {
/*  43 */     super(Integer.valueOf(2075));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.addQuestLvlUp(2075);
/*  50 */     for (int npc_id : npc_ids) {
/*  51 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2075);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(2075);
/*  59 */     boolean lvlCheck = QuestService.checkLevelRequirement(2075, player.getCommonData().getLevel());
/*  60 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  61 */       return false;
/*     */     }
/*  63 */     QuestState qs2 = player.getQuestStateList().getQuestState(2701);
/*  64 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  65 */       return false; 
/*  66 */     qs.setStatus(QuestStatus.START);
/*  67 */     updateQuestStatus(player, qs);
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  74 */     Player player = env.getPlayer();
/*  75 */     QuestState qs = player.getQuestStateList().getQuestState(2075);
/*  76 */     if (qs == null) {
/*  77 */       return false;
/*     */     }
/*  79 */     int var = qs.getQuestVarById(0);
/*  80 */     int targetId = 0;
/*  81 */     if (env.getVisibleObject() instanceof Npc) {
/*  82 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  84 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  86 */       if (targetId == 278034) {
/*     */         
/*  88 */         if (env.getDialogId().intValue() == -1)
/*  89 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  90 */         if (env.getDialogId().intValue() == 1009)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  92 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  94 */       return false;
/*     */     } 
/*  96 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/*  98 */       return false;
/*     */     }
/* 100 */     if (targetId == 278034) {
/*     */       
/* 102 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 105 */           if (var == 0)
/* 106 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 108 */           if (var == 0) {
/*     */             
/* 110 */             qs.setQuestVarById(0, var + 1);
/* 111 */             updateQuestStatus(player, qs);
/* 112 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 113 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 117 */     } else if (targetId == 279004) {
/*     */       
/* 119 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 122 */           if (var == 1)
/* 123 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 1353:
/* 125 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 292));
/*     */           break;
/*     */         case 10001:
/* 128 */           if (var == 1) {
/*     */             
/* 130 */             qs.setQuestVarById(0, var + 1);
/* 131 */             updateQuestStatus(player, qs);
/* 132 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 133 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 137 */     } else if (targetId == 279024) {
/*     */       
/* 139 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 142 */           if (var == 2)
/* 143 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 144 */           if (var == 4)
/* 145 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 10002:
/* 147 */           if (var == 2) {
/*     */             
/* 149 */             qs.setQuestVarById(0, var + 1);
/* 150 */             updateQuestStatus(player, qs);
/* 151 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 152 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 55001, 0));
/* 153 */             return true;
/*     */           } 
/*     */         case 10004:
/* 156 */           if (var == 4) {
/*     */             
/* 158 */             qs.setStatus(QuestStatus.REWARD);
/* 159 */             updateQuestStatus(player, qs);
/* 160 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 161 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 165 */     } else if (targetId == 279006) {
/*     */       
/* 167 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 170 */           if (var == 3)
/* 171 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 10003:
/* 173 */           if (var == 3) {
/*     */             
/* 175 */             qs.setQuestVarById(0, var + 1);
/* 176 */             updateQuestStatus(player, qs);
/* 177 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 178 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_2075PuttingontheSpeed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */