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
/*     */ public class _2005TeachingaLesson
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2005;
/*     */   
/*     */   public _2005TeachingaLesson() {
/*  40 */     super(Integer.valueOf(2005));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(2005);
/*  47 */     this.qe.setNpcQuestData(203540).addOnTalkEvent(2005);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     QuestState qs = player.getQuestStateList().getQuestState(2005);
/*  55 */     if (qs == null) {
/*  56 */       return false;
/*     */     }
/*  58 */     int var = qs.getQuestVarById(0);
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc)
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  62 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  64 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203540:
/*  68 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  71 */               if (var == 0)
/*  72 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  73 */               if (var == 1)
/*  74 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */               break;
/*     */             case 1012:
/*  77 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 54));
/*     */               break;
/*     */             case 10000:
/*  80 */               if (var == 0) {
/*     */                 
/*  82 */                 qs.setQuestVarById(0, var + 1);
/*  83 */                 updateQuestStatus(player, qs);
/*  84 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  85 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 33:
/*  89 */               if (var == 1) {
/*     */                 
/*  91 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/*  93 */                   qs.setStatus(QuestStatus.REWARD);
/*  94 */                   updateQuestStatus(player, qs);
/*  95 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */                 } 
/*     */                 
/*  98 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           
/*     */           break;
/*     */       } 
/* 105 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 107 */       if (targetId == 203540)
/* 108 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 116 */     Player player = env.getPlayer();
/* 117 */     QuestState qs = player.getQuestStateList().getQuestState(2005);
/* 118 */     boolean lvlCheck = QuestService.checkLevelRequirement(2005, player.getCommonData().getLevel());
/* 119 */     if (!lvlCheck || qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 120 */       return false; 
/* 121 */     qs.setStatus(QuestStatus.START);
/* 122 */     updateQuestStatus(player, qs);
/* 123 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2005TeachingaLesson.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */