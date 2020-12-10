/*     */ package quest.verteron;
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
/*     */ public class _1021TrandilasEggs
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1021;
/*  38 */   private static final int[] mob_ids = new int[] { 210202 };
/*     */ 
/*     */   
/*     */   public _1021TrandilasEggs() {
/*  42 */     super(Integer.valueOf(1021));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(203129).addOnTalkEvent(1021);
/*  49 */     this.qe.addQuestLvlUp(1021);
/*  50 */     for (int mob_id : mob_ids) {
/*  51 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1021);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1021);
/*  59 */     boolean lvlCheck = QuestService.checkLevelRequirement(1021, player.getCommonData().getLevel());
/*  60 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  61 */       return false; 
/*  62 */     QuestState qs2 = player.getQuestStateList().getQuestState(1015);
/*  63 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  64 */       return false; 
/*  65 */     qs.setStatus(QuestStatus.START);
/*  66 */     updateQuestStatus(player, qs);
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  73 */     Player player = env.getPlayer();
/*  74 */     QuestState qs = player.getQuestStateList().getQuestState(1021);
/*  75 */     if (qs == null) {
/*  76 */       return false;
/*     */     }
/*  78 */     int var = qs.getQuestVarById(0);
/*  79 */     int targetId = 0;
/*  80 */     if (env.getVisibleObject() instanceof Npc) {
/*  81 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  83 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  85 */       if (targetId == 203129)
/*     */       {
/*  87 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  90 */             if (var == 0)
/*  91 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */           case 1012:
/*  93 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 27));
/*     */             break;
/*     */           case 10000:
/*     */           case 10001:
/*  97 */             if (var == 0) {
/*     */               
/*  99 */               qs.setQuestVarById(0, var + 1);
/* 100 */               updateQuestStatus(player, qs);
/* 101 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 103 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       }
/* 108 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 110 */       if (targetId == 203129)
/* 111 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 119 */     Player player = env.getPlayer();
/* 120 */     QuestState qs = player.getQuestStateList().getQuestState(1021);
/* 121 */     if (qs == null) {
/* 122 */       return false;
/*     */     }
/* 124 */     int var = qs.getQuestVarById(0);
/* 125 */     int targetId = 0;
/* 126 */     if (env.getVisibleObject() instanceof Npc) {
/* 127 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 129 */     if (qs.getStatus() != QuestStatus.START)
/* 130 */       return false; 
/* 131 */     if (targetId == 210202)
/*     */     {
/* 133 */       if (var == 1) {
/*     */         
/* 135 */         qs.setStatus(QuestStatus.REWARD);
/* 136 */         updateQuestStatus(player, qs);
/* 137 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 138 */         return true;
/*     */       } 
/*     */     }
/* 141 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1021TrandilasEggs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */