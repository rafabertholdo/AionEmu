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
/*     */ public class _2001ThinkingAhead
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2001;
/*     */   
/*     */   public _2001ThinkingAhead() {
/*  38 */     super(Integer.valueOf(2001));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.addQuestLvlUp(2001);
/*  45 */     this.qe.setNpcQuestData(203518).addOnTalkEvent(2001);
/*  46 */     this.qe.setNpcQuestData(700093).addOnTalkEvent(2001);
/*  47 */     this.qe.setNpcQuestData(210369).addOnKillEvent(2001);
/*  48 */     this.qe.setNpcQuestData(210368).addOnKillEvent(2001);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     QuestState qs = player.getQuestStateList().getQuestState(2001);
/*  56 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
/*  57 */       return false;
/*     */     }
/*  59 */     qs.setStatus(QuestStatus.START);
/*  60 */     updateQuestStatus(player, qs);
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  67 */     Player player = env.getPlayer();
/*  68 */     QuestState qs = player.getQuestStateList().getQuestState(2001);
/*  69 */     if (qs == null) {
/*  70 */       return false;
/*     */     }
/*  72 */     int var = qs.getQuestVarById(0);
/*  73 */     int targetId = 0;
/*  74 */     if (env.getVisibleObject() instanceof Npc)
/*  75 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  76 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  78 */       if (targetId == 203518) {
/*     */         
/*  80 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  83 */             if (var == 0)
/*  84 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  85 */             if (var == 1)
/*  86 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  87 */             if (var == 2)
/*  88 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694); 
/*  89 */             return false;
/*     */           case 1012:
/*  91 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 51));
/*     */             break;
/*     */           case 10000:
/*     */           case 10002:
/*  95 */             if (var == 0 || var == 2) {
/*     */               
/*  97 */               qs.setQuestVarById(0, var + 1);
/*  98 */               updateQuestStatus(player, qs);
/*  99 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 100 */               return true;
/*     */             } 
/*     */           case 33:
/* 103 */             if (var == 1) {
/*     */               
/* 105 */               if (QuestService.collectItemCheck(env, true)) {
/*     */                 
/* 107 */                 qs.setQuestVarById(0, var + 1);
/* 108 */                 updateQuestStatus(player, qs);
/* 109 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
/*     */               } 
/*     */               
/* 112 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */             } 
/*     */             break;
/*     */         } 
/* 116 */       } else if (targetId == 700093) {
/*     */         
/* 118 */         if (var == 1 && env.getDialogId().intValue() == -1) {
/* 119 */           return true;
/*     */         }
/*     */       } 
/* 122 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 124 */       if (targetId == 203518) {
/*     */         
/* 126 */         if (env.getDialogId().intValue() == -1) {
/* 127 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */         }
/* 129 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 138 */     Player player = env.getPlayer();
/* 139 */     QuestState qs = player.getQuestStateList().getQuestState(2001);
/* 140 */     if (qs == null) {
/* 141 */       return false;
/*     */     }
/* 143 */     int var = qs.getQuestVarById(0);
/* 144 */     int targetId = 0;
/* 145 */     if (env.getVisibleObject() instanceof Npc) {
/* 146 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 148 */     if (qs.getStatus() != QuestStatus.START)
/* 149 */       return false; 
/* 150 */     switch (targetId) {
/*     */       
/*     */       case 210368:
/*     */       case 210369:
/* 154 */         if (var >= 3 && var < 8) {
/*     */           
/* 156 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 157 */           updateQuestStatus(player, qs);
/* 158 */           return true;
/*     */         } 
/* 160 */         if (var == 8) {
/*     */           
/* 162 */           qs.setStatus(QuestStatus.REWARD);
/* 163 */           updateQuestStatus(player, qs);
/* 164 */           return true;
/*     */         }  break;
/*     */     } 
/* 167 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2001ThinkingAhead.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */