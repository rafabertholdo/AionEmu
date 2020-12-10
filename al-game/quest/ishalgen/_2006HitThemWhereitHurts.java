/*     */ package quest.ishalgen;
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
/*     */ 
/*     */ public class _2006HitThemWhereitHurts
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2006;
/*     */   
/*     */   public _2006HitThemWhereitHurts() {
/*  40 */     super(Integer.valueOf(2006));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(2006);
/*  47 */     this.qe.setNpcQuestData(203540).addOnTalkEvent(2006);
/*  48 */     this.qe.setNpcQuestData(700095).addOnTalkEvent(2006);
/*  49 */     this.qe.setNpcQuestData(203516).addOnTalkEvent(2006);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  55 */     Player player = env.getPlayer();
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(2006);
/*  57 */     if (qs == null) {
/*  58 */       return false;
/*     */     }
/*  60 */     int var = qs.getQuestVarById(0);
/*  61 */     int targetId = 0;
/*  62 */     if (env.getVisibleObject() instanceof Npc)
/*  63 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  64 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  66 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203540:
/*  70 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  73 */               if (var == 0)
/*  74 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  75 */               if (var == 1)
/*  76 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */               break;
/*     */             case 10000:
/*  79 */               if (var == 0) {
/*     */                 
/*  81 */                 qs.setQuestVarById(0, var + 1);
/*  82 */                 updateQuestStatus(player, qs);
/*  83 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  84 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 33:
/*  88 */               if (var == 1) {
/*     */                 
/*  90 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/*  92 */                   qs.setStatus(QuestStatus.REWARD);
/*  93 */                   updateQuestStatus(player, qs);
/*  94 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */                 } 
/*     */                 
/*  97 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           
/*     */           break;
/*     */         case 700095:
/* 104 */           if (var == 1)
/* 105 */             return true; 
/*     */           break;
/*     */       } 
/* 108 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 110 */       if (targetId == 203516) {
/*     */         
/* 112 */         if (env.getDialogId().intValue() == -1) {
/* 113 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */         }
/* 115 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 123 */     Player player = env.getPlayer();
/* 124 */     QuestState qs = player.getQuestStateList().getQuestState(2006);
/* 125 */     boolean lvlCheck = QuestService.checkLevelRequirement(2006, player.getCommonData().getLevel());
/* 126 */     if (!lvlCheck || qs == null || qs.getStatus() != QuestStatus.LOCKED) {
/* 127 */       return false;
/*     */     }
/* 129 */     qs.setStatus(QuestStatus.START);
/* 130 */     updateQuestStatus(player, qs);
/* 131 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2006HitThemWhereitHurts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */