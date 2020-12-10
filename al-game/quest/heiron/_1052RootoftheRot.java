/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
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
/*     */ public class _1052RootoftheRot
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1052;
/*  38 */   private static final int[] npc_ids = new int[] { 204549, 730026, 730024 };
/*     */ 
/*     */   
/*     */   public _1052RootoftheRot() {
/*  42 */     super(Integer.valueOf(1052));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.addQuestLvlUp(1052);
/*  49 */     this.qe.setQuestItemIds(182201603).add(1052);
/*  50 */     this.qe.setQuestItemIds(182201604).add(1052);
/*  51 */     for (int npc_id : npc_ids) {
/*  52 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1052);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  58 */     Player player = env.getPlayer();
/*  59 */     QuestState qs = player.getQuestStateList().getQuestState(1052);
/*  60 */     boolean lvlCheck = QuestService.checkLevelRequirement(1052, player.getCommonData().getLevel());
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
/*  76 */     QuestState qs = player.getQuestStateList().getQuestState(1052);
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
/*  87 */       if (targetId == 730024)
/*     */       {
/*  89 */         ItemService.decreaseItemCountByItemId(player, 182201603, 1L);
/*  90 */         ItemService.decreaseItemCountByItemId(player, 182201604, 1L);
/*  91 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/*  94 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  96 */       return false;
/*     */     } 
/*  98 */     if (targetId == 204549) {
/*     */       
/* 100 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 103 */           if (var == 0)
/* 104 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 105 */           if (var == 1)
/* 106 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 33:
/* 108 */           if (QuestService.collectItemCheck(env, false)) {
/*     */ 
/*     */ 
/*     */             
/* 112 */             qs.setQuestVarById(0, var + 1);
/* 113 */             updateQuestStatus(player, qs);
/* 114 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           } 
/*     */           
/* 117 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 10000:
/* 119 */           if (var == 0) {
/*     */             
/* 121 */             qs.setQuestVarById(0, var + 1);
/* 122 */             updateQuestStatus(player, qs);
/* 123 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 125 */             return true;
/*     */           } 
/*     */         case 10001:
/* 128 */           if (var == 1) {
/*     */             
/* 130 */             qs.setQuestVarById(0, var + 1);
/* 131 */             updateQuestStatus(player, qs);
/* 132 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 133 */             return true;
/*     */           } 
/* 135 */           return false;
/*     */       } 
/*     */     
/* 138 */     } else if (targetId == 730026) {
/*     */       
/* 140 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 143 */           if (var == 2)
/* 144 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 10255:
/* 146 */           if (var == 2) {
/*     */             
/* 148 */             qs.setStatus(QuestStatus.REWARD);
/* 149 */             updateQuestStatus(player, qs);
/* 150 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 151 */             return true;
/*     */           } 
/* 153 */           return false;
/*     */       } 
/*     */     } 
/* 156 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1052RootoftheRot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */