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
/*     */ public class _1013HuntingLepharistRevolutionaries
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1013;
/*  38 */   private static final int[] mob_ids = new int[] { 210688, 210316 };
/*     */ 
/*     */   
/*     */   public _1013HuntingLepharistRevolutionaries() {
/*  42 */     super(Integer.valueOf(1013));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(203126).addOnTalkEvent(1013);
/*  49 */     this.qe.addQuestLvlUp(1013);
/*  50 */     for (int mob_id : mob_ids) {
/*  51 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1013);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1013);
/*  59 */     boolean lvlCheck = QuestService.checkLevelRequirement(1013, player.getCommonData().getLevel());
/*  60 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  61 */       return false; 
/*  62 */     qs.setStatus(QuestStatus.START);
/*  63 */     updateQuestStatus(player, qs);
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  70 */     Player player = env.getPlayer();
/*  71 */     QuestState qs = player.getQuestStateList().getQuestState(1013);
/*  72 */     if (qs == null) {
/*  73 */       return false;
/*     */     }
/*  75 */     int var = qs.getQuestVarById(0);
/*  76 */     int targetId = 0;
/*  77 */     if (env.getVisibleObject() instanceof Npc) {
/*  78 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  80 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  82 */       if (targetId == 203126)
/*     */       {
/*  84 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  87 */             if (var == 0)
/*  88 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  89 */             if (var == 11)
/*  90 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  91 */             if (var >= 12) {
/*     */               
/*  93 */               qs.setStatus(QuestStatus.REWARD);
/*  94 */               updateQuestStatus(player, qs);
/*  95 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */             } 
/*     */           case 1012:
/*  98 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 25));
/*  99 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1012);
/*     */           case 10000:
/*     */           case 10001:
/* 102 */             if (var == 0 || var == 11) {
/*     */               
/* 104 */               qs.setQuestVarById(0, var + 1);
/* 105 */               updateQuestStatus(player, qs);
/* 106 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 108 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       }
/* 113 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 115 */       if (targetId == 203126)
/* 116 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 124 */     Player player = env.getPlayer();
/* 125 */     QuestState qs = player.getQuestStateList().getQuestState(1013);
/* 126 */     if (qs == null) {
/* 127 */       return false;
/*     */     }
/* 129 */     int var = qs.getQuestVarById(0);
/* 130 */     int targetId = 0;
/* 131 */     if (env.getVisibleObject() instanceof Npc) {
/* 132 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 134 */     if (qs.getStatus() != QuestStatus.START)
/* 135 */       return false; 
/* 136 */     switch (targetId) {
/*     */       
/*     */       case 210688:
/* 139 */         if (var >= 1 && var <= 11) {
/*     */           
/* 141 */           qs.setQuestVarById(0, var + 1);
/* 142 */           updateQuestStatus(player, qs);
/* 143 */           return true;
/*     */         } 
/*     */         break;
/*     */       case 210316:
/* 147 */         if (var == 12) {
/*     */           
/* 149 */           qs.setStatus(QuestStatus.REWARD);
/* 150 */           updateQuestStatus(player, qs);
/* 151 */           return true;
/*     */         } 
/*     */         break;
/*     */     } 
/* 155 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1013HuntingLepharistRevolutionaries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */