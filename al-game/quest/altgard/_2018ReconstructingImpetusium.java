/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ public class _2018ReconstructingImpetusium
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2018;
/*     */   
/*     */   public _2018ReconstructingImpetusium() {
/*  45 */     super(Integer.valueOf(2018));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  51 */     this.qe.addQuestLvlUp(2018);
/*  52 */     this.qe.setNpcQuestData(203649).addOnTalkEvent(2018);
/*  53 */     this.qe.setNpcQuestData(210588).addOnKillEvent(2018);
/*  54 */     this.qe.setNpcQuestData(700097).addOnTalkEvent(2018);
/*  55 */     this.qe.setNpcQuestData(700098).addOnTalkEvent(2018);
/*  56 */     this.qe.setNpcQuestData(210752).addOnKillEvent(2018);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  62 */     Player player = env.getPlayer();
/*  63 */     QuestState qs = player.getQuestStateList().getQuestState(2018);
/*  64 */     boolean lvlCheck = QuestService.checkLevelRequirement(2018, player.getCommonData().getLevel());
/*  65 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/*  66 */       return false; 
/*  67 */     qs.setStatus(QuestStatus.START);
/*  68 */     updateQuestStatus(player, qs);
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  75 */     final Player player = env.getPlayer();
/*  76 */     QuestState qs = player.getQuestStateList().getQuestState(2018);
/*  77 */     if (qs == null) {
/*  78 */       return false;
/*     */     }
/*  80 */     int var = qs.getQuestVarById(0);
/*  81 */     int targetId = 0;
/*  82 */     if (env.getVisibleObject() instanceof Npc) {
/*  83 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  85 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  87 */       switch (targetId) {
/*     */         
/*     */         case 203649:
/*  90 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  93 */               if (var == 0)
/*  94 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  95 */               if (var == 4)
/*  96 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  97 */               if (var == 7)
/*  98 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */               break;
/*     */             case 1012:
/* 101 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 22));
/*     */               break;
/*     */             case 10000:
/*     */             case 10001:
/* 105 */               if (var == 0 || var == 4) {
/*     */                 
/* 107 */                 qs.setQuestVarById(0, var + 1);
/* 108 */                 updateQuestStatus(player, qs);
/* 109 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 110 */                 return true;
/*     */               } 
/*     */             case 33:
/* 113 */               if (var == 7) {
/*     */                 
/* 115 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/* 117 */                   qs.setStatus(QuestStatus.REWARD);
/* 118 */                   updateQuestStatus(player, qs);
/* 119 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */                 } 
/*     */                 
/* 122 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 700097:
/* 127 */           if (var == 5)
/* 128 */             return true; 
/*     */           break;
/*     */         case 700098:
/* 131 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case -1:
/* 134 */               env.setQuestId(Integer.valueOf(2018));
/* 135 */               if (var == 5 && QuestService.collectItemCheck(env, false)) {
/*     */                 
/* 137 */                 final int targetObjectId = env.getVisibleObject().getObjectId();
/* 138 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */                 
/* 140 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */                 
/* 142 */                 ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/* 146 */                         Npc npc = (Npc)player.getTarget();
/* 147 */                         if (!player.isTargeting(targetObjectId))
/*     */                           return; 
/* 149 */                         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                         
/* 151 */                         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */ 
/*     */                         
/* 154 */                         QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 210752, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), true);
/* 155 */                         npc.getController().onDie(null);
/*     */                       }
/*     */                     }3000L);
/*     */                 
/* 159 */                 return true;
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 165 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 167 */       if (targetId == 203649)
/*     */       {
/* 169 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 178 */     Player player = env.getPlayer();
/* 179 */     QuestState qs = player.getQuestStateList().getQuestState(2018);
/* 180 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 181 */       return false;
/*     */     }
/*     */     
/* 184 */     int targetId = 0;
/* 185 */     int var = 0;
/* 186 */     if (env.getVisibleObject() instanceof Npc)
/* 187 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 188 */     env.setQuestId(Integer.valueOf(2018));
/* 189 */     switch (targetId) {
/*     */       
/*     */       case 210588:
/* 192 */         var = qs.getQuestVarById(0);
/* 193 */         if (var < 4) {
/*     */           
/* 195 */           qs.setQuestVarById(0, var + 1);
/* 196 */           updateQuestStatus(player, qs);
/*     */         } 
/*     */         break;
/*     */       case 210752:
/* 200 */         var = qs.getQuestVarById(0);
/* 201 */         if (var == 5 && QuestService.collectItemCheck(env, false)) {
/*     */           
/* 203 */           qs.setQuestVarById(0, 7);
/* 204 */           updateQuestStatus(player, qs);
/*     */         } 
/*     */         break;
/*     */     } 
/* 208 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2018ReconstructingImpetusium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */