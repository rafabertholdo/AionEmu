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
/*     */ import com.aionemu.gameserver.services.ItemService;
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
/*     */ public class _1056LepharistPoisonResearch
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1056;
/*  39 */   private static final int[] npc_ids = new int[] { 204504, 204574, 203705, 203707 };
/*     */ 
/*     */   
/*     */   public _1056LepharistPoisonResearch() {
/*  43 */     super(Integer.valueOf(1056));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.addQuestLvlUp(1056);
/*  50 */     this.qe.setNpcQuestData(212151).addOnKillEvent(1056);
/*  51 */     for (int npc_id : npc_ids) {
/*  52 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1056);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  58 */     Player player = env.getPlayer();
/*  59 */     QuestState qs = player.getQuestStateList().getQuestState(1056);
/*  60 */     boolean lvlCheck = QuestService.checkLevelRequirement(1056, player.getCommonData().getLevel());
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
/*  76 */     QuestState qs = player.getQuestStateList().getQuestState(1056);
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
/*  87 */       if (targetId == 203707) {
/*  88 */         return defaultQuestEndDialog(env);
/*     */       }
/*  90 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  92 */       return false;
/*     */     } 
/*  94 */     if (targetId == 204504) {
/*     */       
/*  96 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/*  99 */           if (var == 0)
/* 100 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 102 */           if (var == 0) {
/*     */             
/* 104 */             qs.setQuestVarById(0, var + 1);
/* 105 */             updateQuestStatus(player, qs);
/* 106 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 107 */             return true;
/*     */           } 
/* 109 */           return false;
/*     */       } 
/*     */     
/* 112 */     } else if (targetId == 204574) {
/*     */       
/* 114 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 117 */           if (var == 1)
/* 118 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10001:
/* 120 */           if (var == 1) {
/*     */             
/* 122 */             qs.setQuestVarById(0, var + 1);
/* 123 */             updateQuestStatus(player, qs);
/* 124 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 125 */             return true;
/*     */           } 
/* 127 */           return false;
/*     */       } 
/*     */     
/* 130 */     } else if (targetId == 203705) {
/*     */       
/* 132 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 135 */           if (var == 2)
/* 136 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 137 */           if (var == 5)
/* 138 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 33:
/* 140 */           if (var == 2 && player.getInventory().getItemCountByItemId(182201614) == 1L) {
/*     */             
/* 142 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 101));
/* 143 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           } 
/*     */           
/* 146 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 10003:
/* 148 */           if (var == 2) {
/*     */             
/* 150 */             qs.setQuestVarById(0, 4);
/* 151 */             updateQuestStatus(player, qs);
/* 152 */             ItemService.decreaseItemCountByItemId(player, 182201614, 1L);
/* 153 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */             return true;
/*     */           } 
/*     */         case 10255:
/* 157 */           if (var == 5) {
/*     */             
/* 159 */             qs.setStatus(QuestStatus.REWARD);
/* 160 */             updateQuestStatus(player, qs);
/* 161 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 162 */             return true;
/*     */           } 
/* 164 */           return false;
/*     */       } 
/*     */     } 
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 173 */     Player player = env.getPlayer();
/* 174 */     QuestState qs = player.getQuestStateList().getQuestState(1056);
/* 175 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 176 */       return false;
/*     */     }
/* 178 */     int targetId = 0;
/* 179 */     if (env.getVisibleObject() instanceof Npc) {
/* 180 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 182 */     if (targetId == 212151 && qs.getQuestVarById(0) == 4) {
/*     */       
/* 184 */       qs.setQuestVarById(0, 5);
/* 185 */       updateQuestStatus(player, qs);
/*     */     } 
/* 187 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1056LepharistPoisonResearch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */