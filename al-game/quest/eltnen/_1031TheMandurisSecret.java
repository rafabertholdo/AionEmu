/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
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
/*     */ public class _1031TheMandurisSecret
/*     */   extends QuestHandler
/*     */ {
/*  40 */   private static final int[] mob_ids = new int[] { 210770, 210771, 210759, 210758 };
/*     */   
/*     */   private static final int questId = 1031;
/*     */   
/*     */   public _1031TheMandurisSecret() {
/*  45 */     super(Integer.valueOf(1031));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  51 */     this.qe.setNpcQuestData(203902).addOnTalkEvent(1031);
/*  52 */     this.qe.setNpcQuestData(203936).addOnTalkEvent(1031);
/*  53 */     this.qe.setNpcQuestData(700179).addOnTalkEvent(1031);
/*  54 */     this.qe.setNpcQuestData(204043).addOnTalkEvent(1031);
/*  55 */     this.qe.setNpcQuestData(204030).addOnTalkEvent(1031);
/*  56 */     this.qe.addQuestLvlUp(1031);
/*  57 */     for (int mob_id : mob_ids) {
/*  58 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1031);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  64 */     Player player = env.getPlayer();
/*  65 */     QuestState qs = player.getQuestStateList().getQuestState(1031);
/*  66 */     boolean lvlCheck = QuestService.checkLevelRequirement(1031, player.getCommonData().getLevel());
/*  67 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  68 */       return false; 
/*  69 */     qs.setStatus(QuestStatus.START);
/*  70 */     updateQuestStatus(player, qs);
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  77 */     Player player = env.getPlayer();
/*  78 */     QuestState qs = player.getQuestStateList().getQuestState(1031);
/*  79 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/*  80 */       return false;
/*     */     }
/*  82 */     int var = qs.getQuestVarById(0);
/*  83 */     int targetId = 0;
/*  84 */     if (env.getVisibleObject() instanceof Npc)
/*  85 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  86 */     switch (targetId) {
/*     */       
/*     */       case 210758:
/*     */       case 210759:
/*     */       case 210770:
/*     */       case 210771:
/*  92 */         if (var >= 1 && var <= 6) {
/*     */           
/*  94 */           qs.setQuestVarById(0, var + 1);
/*  95 */           updateQuestStatus(player, qs);
/*  96 */           return true;
/*     */         }  break;
/*     */     } 
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/* 104 */     final Player player = env.getPlayer();
/* 105 */     int targetId = 0;
/* 106 */     if (env.getVisibleObject() instanceof Npc)
/* 107 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 108 */     QuestState qs = player.getQuestStateList().getQuestState(1031);
/* 109 */     if (qs == null)
/* 110 */       return false; 
/* 111 */     if (targetId == 203902) {
/*     */       
/* 113 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/* 115 */         if (env.getDialogId().intValue() == 25)
/* 116 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 117 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 119 */           qs.setQuestVar(1);
/* 120 */           updateQuestStatus(player, qs);
/* 121 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 123 */           return true;
/*     */         } 
/*     */         
/* 126 */         return defaultQuestStartDialog(env);
/*     */       } 
/* 128 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 7) {
/*     */         
/* 130 */         if (env.getDialogId().intValue() == 25)
/* 131 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 132 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 134 */           qs.setQuestVar(8);
/* 135 */           updateQuestStatus(player, qs);
/* 136 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 138 */           return true;
/*     */         } 
/*     */         
/* 141 */         return defaultQuestStartDialog(env);
/*     */       } 
/* 143 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 145 */         if (env.getDialogId().intValue() == -1)
/* 146 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 147 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/* 150 */     } else if (targetId == 203936) {
/*     */       
/* 152 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 8)
/*     */       {
/* 154 */         if (env.getDialogId().intValue() == 25)
/* 155 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 156 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 158 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 159 */           updateQuestStatus(player, qs);
/* 160 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 162 */           return true;
/*     */         } 
/*     */         
/* 165 */         return defaultQuestStartDialog(env);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 170 */     else if (targetId == 204043) {
/*     */       
/* 172 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 10)
/*     */       {
/* 174 */         if (env.getDialogId().intValue() == 25)
/* 175 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 176 */         if (env.getDialogId().intValue() == 10004) {
/*     */           
/* 178 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 2);
/* 179 */           updateQuestStatus(player, qs);
/* 180 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 182 */           return true;
/*     */         } 
/*     */         
/* 185 */         return defaultQuestStartDialog(env);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 190 */     else if (targetId == 204030) {
/*     */       
/* 192 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 12)
/*     */       {
/*     */         
/* 195 */         if (env.getDialogId().intValue() == 25)
/* 196 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 197 */         if (env.getDialogId().intValue() == 10006) {
/*     */           
/* 199 */           qs.setStatus(QuestStatus.REWARD);
/* 200 */           updateQuestStatus(player, qs);
/* 201 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 203 */           return true;
/*     */         } 
/*     */         
/* 206 */         return defaultQuestStartDialog(env);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 211 */     else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 9) {
/*     */       
/* 213 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 700179:
/* 217 */           if (qs.getQuestVarById(0) == 9 && env.getDialogId().intValue() == -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 222 */             qs.setQuestVar(10);
/* 223 */             updateQuestStatus(player, qs);
/* 224 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 225 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 227 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 229 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 233 */                     if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
/*     */                       return; 
/* 235 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 237 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 247 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1031TheMandurisSecret.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */