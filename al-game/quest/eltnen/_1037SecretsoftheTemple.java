/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.Collections;
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
/*     */ public class _1037SecretsoftheTemple
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1037;
/*  45 */   private static final int[] npc_ids = new int[] { 203965, 203967, 700151, 700154, 700150, 700153, 700152 };
/*     */ 
/*     */   
/*     */   public _1037SecretsoftheTemple() {
/*  49 */     super(Integer.valueOf(1037));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  55 */     this.qe.addQuestLvlUp(1037);
/*  56 */     for (int npc_id : npc_ids) {
/*  57 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1037);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  63 */     Player player = env.getPlayer();
/*  64 */     QuestState qs = player.getQuestStateList().getQuestState(1037);
/*  65 */     boolean lvlCheck = QuestService.checkLevelRequirement(1037, player.getCommonData().getLevel());
/*  66 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  67 */       return false;
/*     */     }
/*  69 */     qs.setStatus(QuestStatus.START);
/*  70 */     updateQuestStatus(player, qs);
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  77 */     final Player player = env.getPlayer();
/*  78 */     final QuestState qs = player.getQuestStateList().getQuestState(1037);
/*  79 */     if (qs == null) {
/*  80 */       return false;
/*     */     }
/*  82 */     int var = qs.getQuestVarById(0);
/*  83 */     int targetId = 0;
/*  84 */     if (env.getVisibleObject() instanceof Npc) {
/*  85 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  87 */     if (qs.getStatus() == QuestStatus.REWARD)
/*     */     {
/*  89 */       if (targetId == 203965) {
/*  90 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  93 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/*  95 */       return false;
/*     */     }
/*  97 */     if (targetId == 203965) {
/*     */       
/*  99 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 102 */           if (var == 0) {
/* 103 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */           }
/*     */         case 10000:
/* 106 */           if (var == 0) {
/*     */             
/* 108 */             qs.setQuestVarById(0, var + 1);
/* 109 */             updateQuestStatus(player, qs);
/* 110 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 111 */             return true;
/*     */           } 
/* 113 */           return false;
/*     */       } 
/*     */ 
/*     */     
/* 117 */     } else if (targetId == 203967) {
/*     */       
/* 119 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 122 */           if (var == 1)
/* 123 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 124 */           if (var == 2) {
/* 125 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */           }
/*     */         case 1694:
/* 128 */           if (var == 2 && QuestService.collectItemCheck(env, true)) {
/* 129 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
/*     */           }
/* 131 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
/*     */         case 10001:
/* 133 */           if (var == 1) {
/*     */             
/* 135 */             qs.setQuestVarById(0, var + 1);
/* 136 */             updateQuestStatus(player, qs);
/* 137 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 138 */             return true;
/*     */           } 
/*     */         case 10002:
/* 141 */           if (var == 2) {
/*     */             
/* 143 */             qs.setQuestVarById(0, var + 1);
/* 144 */             updateQuestStatus(player, qs);
/* 145 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 146 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201027, 1)));
/* 147 */             return true;
/*     */           } 
/* 149 */           return false;
/*     */       } 
/*     */     
/* 152 */     } else if ((targetId == 700151 && var == 3) || (targetId == 700154 && var == 4) || (targetId == 700150 && var == 5) || (targetId == 700153 && var == 6) || (targetId == 700152 && var == 7)) {
/*     */ 
/*     */       
/* 155 */       if (env.getDialogId().intValue() == -1) {
/*     */         
/* 157 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 158 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 33));
/* 159 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 160 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 161 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 165 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 166 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 167 */                 if (qs.getQuestVarById(0) == 7) {
/*     */                   
/* 169 */                   ItemService.decreaseItemCountByItemId(player, 182201027, 1L);
/* 170 */                   qs.setStatus(QuestStatus.REWARD);
/* 171 */                   _1037SecretsoftheTemple.this.updateQuestStatus(player, qs);
/*     */                   
/*     */                   return;
/*     */                 } 
/* 175 */                 qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 176 */                 _1037SecretsoftheTemple.this.updateQuestStatus(player, qs);
/*     */               }
/*     */             }3000L);
/*     */       } 
/* 180 */       return false;
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1037SecretsoftheTemple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */