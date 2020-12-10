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
/*     */ public class _1075NewWings
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1075;
/*  39 */   private static final int[] npc_ids = new int[] { 278506, 279023, 278643 };
/*     */ 
/*     */   
/*     */   public _1075NewWings() {
/*  43 */     super(Integer.valueOf(1075));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.addQuestLvlUp(1075);
/*  50 */     for (int npc_id : npc_ids) {
/*  51 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1075);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1075);
/*  59 */     boolean lvlCheck = QuestService.checkLevelRequirement(1075, player.getCommonData().getLevel());
/*  60 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  61 */       return false;
/*     */     }
/*  63 */     QuestState qs2 = player.getQuestStateList().getQuestState(1072);
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
/*  75 */     QuestState qs = player.getQuestStateList().getQuestState(1075);
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
/*  86 */       if (targetId == 279023) {
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
/* 100 */     if (targetId == 278506) {
/*     */       
/* 102 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 105 */           if (var == 0)
/* 106 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 1013:
/* 108 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 272));
/*     */           break;
/*     */         case 10000:
/* 111 */           if (var == 0) {
/*     */             
/* 113 */             qs.setQuestVarById(0, var + 1);
/* 114 */             updateQuestStatus(player, qs);
/* 115 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 116 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 120 */     } else if (targetId == 279023) {
/*     */       
/* 122 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 125 */           if (var == 1)
/* 126 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10001:
/* 128 */           if (var == 1) {
/*     */             
/* 130 */             qs.setQuestVarById(0, var + 1);
/* 131 */             updateQuestStatus(player, qs);
/* 132 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 133 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 57001, 0));
/* 134 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 138 */     } else if (targetId == 278643) {
/*     */       
/* 140 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 143 */           if (var == 2)
/* 144 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 145 */           if (var == 3)
/* 146 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 10002:
/* 148 */           if (var == 2) {
/*     */             
/* 150 */             qs.setQuestVarById(0, var + 1);
/* 151 */             updateQuestStatus(player, qs);
/* 152 */             QuestService.addNewSpawn(400010000, 1, 214102, 2344.32F, 1789.96F, 2258.88F, (byte)86, true);
/* 153 */             QuestService.addNewSpawn(400010000, 1, 214102, 2344.51F, 1786.01F, 2258.88F, (byte)52, true);
/* 154 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 155 */             return true;
/*     */           } 
/*     */         case 10003:
/* 158 */           if (var == 3) {
/*     */             
/* 160 */             qs.setQuestVarById(0, 12);
/* 161 */             qs.setStatus(QuestStatus.REWARD);
/* 162 */             updateQuestStatus(player, qs);
/* 163 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 164 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 168 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_1075NewWings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */