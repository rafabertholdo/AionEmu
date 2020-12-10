/*     */ package quest.brusthonin;
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
/*     */ public class _2094TheSecretofAdmaStronghold
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2094;
/*  37 */   private static final int[] npc_ids = new int[] { 205150, 205192, 205155, 730164, 205191, 204057 };
/*     */ 
/*     */   
/*     */   public _2094TheSecretofAdmaStronghold() {
/*  41 */     super(Integer.valueOf(2094));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  47 */     this.qe.addQuestLvlUp(2094);
/*  48 */     this.qe.setNpcQuestData(214700).addOnKillEvent(2094);
/*  49 */     for (int npc_id : npc_ids)
/*  50 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2094); 
/*  51 */     this.deletebleItems = new int[] { 182209013 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(2094);
/*  59 */     boolean lvlCheck = QuestService.checkLevelRequirement(2094, player.getCommonData().getLevel());
/*     */     
/*  61 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/*     */     {
/*  63 */       return false;
/*     */     }
/*     */     
/*  66 */     int[] quests = { 2092, 2093, 2054 };
/*  67 */     for (int id : quests) {
/*     */       
/*  69 */       QuestState qs2 = player.getQuestStateList().getQuestState(id);
/*  70 */       if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
/*  71 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  75 */     qs.setStatus(QuestStatus.START);
/*  76 */     updateQuestStatus(player, qs);
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  83 */     Player player = env.getPlayer();
/*  84 */     QuestState qs = player.getQuestStateList().getQuestState(2094);
/*  85 */     Npc npc = (Npc)env.getVisibleObject();
/*     */     
/*  87 */     if (qs == null) {
/*  88 */       return false;
/*     */     }
/*  90 */     int var = qs.getQuestVarById(0);
/*  91 */     int targetId = 0;
/*  92 */     if (env.getVisibleObject() instanceof Npc) {
/*  93 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  95 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  97 */       if (targetId == 204057)
/*  98 */         return defaultQuestEndDialog(env); 
/*  99 */       return false;
/*     */     } 
/* 101 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 103 */       return false;
/*     */     }
/* 105 */     if (targetId == 205150) {
/*     */       
/* 107 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 110 */           if (var == 0)
/* 111 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 112 */           return true;
/*     */         case 10000:
/* 114 */           if (var == 0) {
/*     */             
/* 116 */             qs.setQuestVarById(0, var + 1);
/* 117 */             updateQuestStatus(player, qs);
/* 118 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 119 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 123 */     } else if (targetId == 205192) {
/*     */       
/* 125 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 128 */           if (var == 1)
/* 129 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 130 */           if (var == 2)
/* 131 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 132 */           if (var == 3)
/* 133 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 134 */           return true;
/*     */         case 10001:
/* 136 */           if (var == 1) {
/*     */             
/* 138 */             qs.setQuestVarById(0, var + 1);
/* 139 */             updateQuestStatus(player, qs);
/* 140 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 141 */             return true;
/*     */           } 
/*     */         case 33:
/* 144 */           if (var == 2) {
/*     */             
/* 146 */             if (QuestService.collectItemCheck(env, true)) {
/*     */               
/* 148 */               qs.setQuestVarById(0, var + 1);
/* 149 */               updateQuestStatus(player, qs);
/* 150 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */             } 
/* 152 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10008);
/*     */           } 
/*     */         case 10003:
/* 155 */           if (var == 3) {
/*     */             
/* 157 */             qs.setQuestVarById(0, var + 1);
/* 158 */             updateQuestStatus(player, qs);
/*     */           } 
/*     */           break;
/*     */       } 
/* 162 */     } else if (targetId == 205155) {
/*     */       
/* 164 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 167 */           if (var == 5)
/* 168 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 10005:
/* 170 */           if (var == 5) {
/*     */             
/* 172 */             qs.setQuestVarById(0, var + 1);
/* 173 */             updateQuestStatus(player, qs);
/* 174 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 175 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 179 */     } else if (targetId == 730164) {
/*     */       
/* 181 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 184 */           if (var == 6) {
/*     */             
/* 186 */             QuestService.addNewSpawn(220050000, 1, 205191, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 188 */             npc.getController().onDespawn(true);
/* 189 */             npc.getController().scheduleRespawn();
/* 190 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 194 */     } else if (targetId == 205191) {
/*     */       
/* 196 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 199 */           if (var == 6) {
/*     */             
/* 201 */             qs.setStatus(QuestStatus.REWARD);
/* 202 */             updateQuestStatus(player, qs);
/* 203 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 214 */     Player player = env.getPlayer();
/* 215 */     QuestState qs = player.getQuestStateList().getQuestState(2094);
/* 216 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 217 */       return false;
/*     */     }
/* 219 */     int var = qs.getQuestVarById(0);
/* 220 */     int targetId = 0;
/* 221 */     if (env.getVisibleObject() instanceof Npc) {
/* 222 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 224 */     if (targetId == 214700)
/*     */     {
/* 226 */       if (var == 4) {
/*     */         
/* 228 */         qs.setQuestVarById(0, var + 1);
/* 229 */         updateQuestStatus(player, qs);
/* 230 */         return true;
/*     */       } 
/*     */     }
/* 233 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_2094TheSecretofAdmaStronghold.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */