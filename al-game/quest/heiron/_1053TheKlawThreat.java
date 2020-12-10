/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
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
/*     */ public class _1053TheKlawThreat
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1053;
/*  38 */   private static final int[] npc_ids = new int[] { 204583, 204502 };
/*     */ 
/*     */   
/*     */   public _1053TheKlawThreat() {
/*  42 */     super(Integer.valueOf(1053));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.addQuestLvlUp(1053);
/*  49 */     this.qe.setNpcQuestData(700169).addOnKillEvent(1053);
/*  50 */     this.qe.setNpcQuestData(212120).addOnKillEvent(1053);
/*  51 */     for (int npc_id : npc_ids) {
/*  52 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1053);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  58 */     Player player = env.getPlayer();
/*  59 */     QuestState qs = player.getQuestStateList().getQuestState(1053);
/*  60 */     boolean lvlCheck = QuestService.checkLevelRequirement(1053, player.getCommonData().getLevel());
/*  61 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  62 */       return false;
/*     */     }
/*  64 */     QuestState qs2 = player.getQuestStateList().getQuestState(1500);
/*  65 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  66 */       return false; 
/*  67 */     qs.setStatus(QuestStatus.START);
/*  68 */     updateQuestStatus(player, qs);
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  75 */     Player player = env.getPlayer();
/*  76 */     QuestState qs = player.getQuestStateList().getQuestState(1053);
/*  77 */     if (qs == null) {
/*  78 */       return false;
/*     */     }
/*  80 */     int var = qs.getQuestVarById(0);
/*  81 */     int targetId = 0;
/*  82 */     if (env.getVisibleObject() instanceof Npc) {
/*  83 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  85 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  87 */       if (targetId == 204502) {
/*  88 */         return defaultQuestEndDialog(env);
/*     */       }
/*  90 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  92 */       return false;
/*     */     } 
/*  94 */     if (targetId == 204583)
/*     */     {
/*  96 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/*  99 */           if (var == 0)
/* 100 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 101 */           if (var == 1)
/* 102 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 103 */           if (var == 2)
/* 104 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 33:
/* 106 */           if (var == 1 && QuestService.collectItemCheck(env, true)) {
/* 107 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           }
/* 109 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 1693:
/* 111 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 186));
/* 112 */           return false;
/*     */         case 10000:
/* 114 */           if (var == 0) {
/*     */             
/* 116 */             qs.setQuestVarById(0, var + 1);
/* 117 */             updateQuestStatus(player, qs);
/* 118 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 119 */             return true;
/*     */           } 
/*     */         case 10002:
/* 122 */           if (var == 1) {
/*     */             
/* 124 */             qs.setQuestVarById(0, var + 2);
/* 125 */             updateQuestStatus(player, qs);
/* 126 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 127 */             return true;
/*     */           } 
/* 129 */           return false;
/*     */       } 
/*     */     }
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 139 */     Player player = env.getPlayer();
/* 140 */     QuestState qs = player.getQuestStateList().getQuestState(1053);
/* 141 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 142 */       return false;
/*     */     }
/* 144 */     Rnd queen = new Rnd();
/* 145 */     int targetId = 0;
/* 146 */     if (env.getVisibleObject() instanceof Npc)
/* 147 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 148 */     Npc npc = (Npc)env.getVisibleObject();
/*     */     
/* 150 */     if (targetId == 700169) {
/*     */       
/* 152 */       int spawn = Rnd.nextInt(5);
/* 153 */       if (spawn == 1)
/*     */       {
/* 155 */         QuestService.addNewSpawn(210040000, 1, 212120, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */         
/* 157 */         return true;
/*     */       }
/*     */     
/* 160 */     } else if (targetId == 212120 && qs.getQuestVarById(0) == 3) {
/*     */       
/* 162 */       qs.setStatus(QuestStatus.REWARD);
/* 163 */       updateQuestStatus(player, qs);
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1053TheKlawThreat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */