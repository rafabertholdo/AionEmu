/*     */ package quest.ascension;
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
/*     */ import com.aionemu.gameserver.services.TeleportService;
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
/*     */ public class _2901DispatchtoAltgard
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2901;
/*     */   
/*     */   public _2901DispatchtoAltgard() {
/*  40 */     super(Integer.valueOf(2901));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(2901);
/*  47 */     this.qe.setNpcQuestData(204191).addOnTalkEvent(2901);
/*  48 */     this.qe.setNpcQuestData(203559).addOnTalkEvent(2901);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     QuestState qs = player.getQuestStateList().getQuestState(2901);
/*  56 */     if (qs == null) {
/*  57 */       return false;
/*     */     }
/*  59 */     int var = qs.getQuestVarById(0);
/*  60 */     int targetId = 0;
/*  61 */     if (env.getVisibleObject() instanceof Npc)
/*  62 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  63 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  65 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 204191:
/*  69 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  72 */               if (var == 0)
/*  73 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */               break;
/*     */             case 10000:
/*  76 */               if (var == 0) {
/*     */                 
/*  78 */                 qs.setQuestVarById(0, var + 1);
/*  79 */                 updateQuestStatus(player, qs);
/*  80 */                 TeleportService.teleportTo(player, 220030000, player.getInstanceId(), 1748.0F, 1807.0F, 255.0F, 1000);
/*  81 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/*  82 */                 return true;
/*     */               } 
/*     */               break;
/*     */           } 
/*     */         case 203559:
/*  87 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  90 */               if (var == 1) {
/*     */                 
/*  92 */                 qs.setStatus(QuestStatus.REWARD);
/*  93 */                 updateQuestStatus(player, qs);
/*  94 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */       } 
/*  99 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 101 */       if (targetId == 203559)
/*     */       {
/* 103 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 112 */     Player player = env.getPlayer();
/* 113 */     QuestState qs = player.getQuestStateList().getQuestState(2901);
/* 114 */     boolean lvlCheck = QuestService.checkLevelRequirement(2901, player.getCommonData().getLevel());
/* 115 */     if (qs != null || !lvlCheck) {
/* 116 */       return false;
/*     */     }
/* 118 */     env.setQuestId(Integer.valueOf(2901));
/* 119 */     QuestService.startQuest(env, QuestStatus.START);
/* 120 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ascension\_2901DispatchtoAltgard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */