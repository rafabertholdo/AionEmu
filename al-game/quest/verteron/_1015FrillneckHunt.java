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
/*     */ public class _1015FrillneckHunt
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1015;
/*  38 */   private static final int[] mob_ids = new int[] { 210126, 210200, 210201 };
/*     */ 
/*     */   
/*     */   public _1015FrillneckHunt() {
/*  42 */     super(Integer.valueOf(1015));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(203129).addOnTalkEvent(1015);
/*  49 */     this.qe.addQuestLvlUp(1015);
/*  50 */     for (int mob_id : mob_ids) {
/*  51 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1015);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1015);
/*  59 */     boolean lvlCheck = QuestService.checkLevelRequirement(1015, player.getCommonData().getLevel());
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
/*  71 */     QuestState qs = player.getQuestStateList().getQuestState(1015);
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
/*  82 */       if (targetId == 203129)
/*     */       {
/*  84 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  87 */             if (var == 0)
/*  88 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  89 */             if (var == 8)
/*  90 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 1012:
/*  92 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 27));
/*  93 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1012);
/*     */           case 10000:
/*     */           case 10001:
/*  96 */             if (var == 0 || var == 8) {
/*     */               
/*  98 */               qs.setQuestVarById(0, var + 1);
/*  99 */               updateQuestStatus(player, qs);
/* 100 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 102 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       }
/* 107 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 109 */       if (targetId == 203129) {
/*     */         
/* 111 */         if (env.getDialogId().intValue() == -1)
/* 112 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 113 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 122 */     Player player = env.getPlayer();
/* 123 */     QuestState qs = player.getQuestStateList().getQuestState(1015);
/* 124 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 125 */       return false;
/*     */     }
/* 127 */     int var = qs.getQuestVarById(0);
/* 128 */     int targetId = 0;
/* 129 */     if (env.getVisibleObject() instanceof Npc)
/* 130 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 131 */     switch (targetId) {
/*     */       
/*     */       case 210126:
/* 134 */         if (var >= 1 && var <= 7) {
/*     */           
/* 136 */           qs.setQuestVarById(0, var + 1);
/* 137 */           updateQuestStatus(player, qs);
/* 138 */           return true;
/*     */         } 
/*     */         break;
/*     */       case 210200:
/*     */       case 210201:
/* 143 */         if (var >= 9 && var <= 20) {
/*     */           
/* 145 */           if (var == 20) {
/* 146 */             qs.setStatus(QuestStatus.REWARD);
/*     */           } else {
/* 148 */             qs.setQuestVarById(0, var + 1);
/* 149 */           }  updateQuestStatus(player, qs);
/* 150 */           return true;
/*     */         } 
/*     */         break;
/*     */     } 
/* 154 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1015FrillneckHunt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */