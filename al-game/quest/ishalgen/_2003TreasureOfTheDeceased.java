/*     */ package quest.ishalgen;
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
/*     */ public class _2003TreasureOfTheDeceased
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2003;
/*     */   
/*     */   public _2003TreasureOfTheDeceased() {
/*  40 */     super(Integer.valueOf(2003));
/*     */   }
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.addQuestLvlUp(2003);
/*  46 */     this.qe.setNpcQuestData(203539).addOnTalkEvent(2003);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     QuestState qs = player.getQuestStateList().getQuestState(2003);
/*  54 */     if (qs == null) {
/*  55 */       return false;
/*     */     }
/*  57 */     int var = qs.getQuestVarById(0);
/*  58 */     int targetId = 0;
/*  59 */     if (env.getVisibleObject() instanceof Npc)
/*  60 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  61 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  63 */       if (targetId == 203539)
/*     */       {
/*  65 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  68 */             if (var == 0)
/*  69 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  70 */             if (var == 1)
/*  71 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 1012:
/*  73 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 53));
/*     */             break;
/*     */           case 10000:
/*  76 */             if (var == 0) {
/*     */               
/*  78 */               qs.setQuestVarById(0, var + 1);
/*  79 */               updateQuestStatus(player, qs);
/*  80 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  81 */               return true;
/*     */             } 
/*     */           case 33:
/*  84 */             if (var == 1) {
/*     */               
/*  86 */               if (QuestService.collectItemCheck(env, true)) {
/*     */                 
/*  88 */                 qs.setStatus(QuestStatus.REWARD);
/*  89 */                 updateQuestStatus(player, qs);
/*  90 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */               } 
/*     */               
/*  93 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       }
/*  98 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 100 */       if (targetId == 203539)
/* 101 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 108 */     Player player = env.getPlayer();
/* 109 */     boolean lvlCheck = QuestService.checkLevelRequirement(2003, player.getCommonData().getLevel());
/* 110 */     if (!lvlCheck)
/* 111 */       return false; 
/* 112 */     QuestState qs = player.getQuestStateList().getQuestState(2003);
/* 113 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 114 */       return false; 
/* 115 */     QuestState qs2 = player.getQuestStateList().getQuestState(2100);
/* 116 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/* 117 */       return false; 
/* 118 */     qs.setStatus(QuestStatus.START);
/* 119 */     updateQuestStatus(player, qs);
/* 120 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2003TreasureOfTheDeceased.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */