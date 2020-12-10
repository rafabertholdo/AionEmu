/*     */ package quest.heiron;
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
/*     */ public class _1062IndratuLegion
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1062;
/*  40 */   private static final int[] npc_ids = new int[] { 204500, 204600, 204610 };
/*     */ 
/*     */   
/*     */   public _1062IndratuLegion() {
/*  44 */     super(Integer.valueOf(1062));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.addQuestLvlUp(1062);
/*  51 */     this.qe.setNpcQuestData(212588).addOnKillEvent(1062);
/*  52 */     this.qe.setNpcQuestData(700220).addOnKillEvent(1062);
/*  53 */     for (int npc_id : npc_ids) {
/*  54 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1062);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  60 */     Player player = env.getPlayer();
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(1062);
/*  62 */     boolean lvlCheck = QuestService.checkLevelRequirement(1062, player.getCommonData().getLevel());
/*  63 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  64 */       return false;
/*     */     }
/*  66 */     QuestState qs2 = player.getQuestStateList().getQuestState(1500);
/*  67 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  68 */       return false; 
/*  69 */     qs.setStatus(QuestStatus.START);
/*  70 */     updateQuestStatus(player, qs);
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  77 */     Player player = env.getPlayer();
/*  78 */     QuestState qs = player.getQuestStateList().getQuestState(1062);
/*  79 */     if (qs == null) {
/*  80 */       return false;
/*     */     }
/*  82 */     int var = qs.getQuestVarById(0);
/*  83 */     int targetId = 0;
/*  84 */     if (env.getVisibleObject() instanceof Npc) {
/*  85 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  87 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  89 */       if (targetId == 204500) {
/*     */         
/*  91 */         if (env.getDialogId().intValue() == -1)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  93 */         if (env.getDialogId().intValue() == 1009)
/*  94 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  95 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  97 */       return false;
/*     */     } 
/*  99 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 101 */       return false;
/*     */     }
/* 103 */     if (targetId == 204500) {
/*     */       
/* 105 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 108 */           if (var == 0)
/* 109 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
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
/* 120 */     } else if (targetId == 204600) {
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
/* 133 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 54001, 0));
/* 134 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 138 */     } else if (targetId == 204610) {
/*     */       
/* 140 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 143 */           if (var == 2)
/* 144 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 1694:
/* 146 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 195));
/*     */           break;
/*     */         case 10002:
/* 149 */           if (var == 2) {
/*     */             
/* 151 */             qs.setQuestVarById(0, var + 1);
/* 152 */             updateQuestStatus(player, qs);
/* 153 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 164 */     final Player player = env.getPlayer();
/* 165 */     QuestState qs = player.getQuestStateList().getQuestState(1062);
/* 166 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 167 */       return false;
/*     */     }
/* 169 */     int targetId = 0;
/* 170 */     if (env.getVisibleObject() instanceof Npc) {
/* 171 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 173 */     if (targetId == 700220 && qs.getQuestVarById(0) > 2 && qs.getQuestVarById(0) < 12) {
/*     */       
/* 175 */       qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 176 */       updateQuestStatus(player, qs);
/* 177 */       return true;
/*     */     } 
/* 179 */     if (targetId == 700220 && qs.getQuestVarById(0) == 12) {
/*     */       
/* 181 */       qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 182 */       updateQuestStatus(player, qs);
/* 183 */       final Npc npc = (Npc)env.getVisibleObject();
/* 184 */       ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 188 */               QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 212588, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), true);
/*     */             }
/*     */           }3000L);
/* 191 */       return true;
/*     */     } 
/* 193 */     if (targetId == 212588 && qs.getQuestVarById(0) == 13) {
/*     */       
/* 195 */       qs.setStatus(QuestStatus.REWARD);
/* 196 */       updateQuestStatus(player, qs);
/* 197 */       return true;
/*     */     } 
/* 199 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1062IndratuLegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */