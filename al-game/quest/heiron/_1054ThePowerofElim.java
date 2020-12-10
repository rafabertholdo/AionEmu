/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
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
/*     */ 
/*     */ 
/*     */ public class _1054ThePowerofElim
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1054;
/*  42 */   private static final int[] npc_ids = new int[] { 730024, 204647, 730008, 730019 };
/*     */ 
/*     */   
/*     */   public _1054ThePowerofElim() {
/*  46 */     super(Integer.valueOf(1054));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.addQuestLvlUp(1054);
/*  53 */     this.qe.setQuestItemIds(182201608).add(1054);
/*  54 */     for (int npc_id : npc_ids) {
/*  55 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1054);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  61 */     Player player = env.getPlayer();
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(1054);
/*  63 */     boolean lvlCheck = QuestService.checkLevelRequirement(1054, player.getCommonData().getLevel());
/*  64 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  65 */       return false;
/*     */     }
/*  67 */     QuestState qs2 = player.getQuestStateList().getQuestState(1500);
/*  68 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  69 */       return false; 
/*  70 */     qs.setStatus(QuestStatus.START);
/*  71 */     updateQuestStatus(player, qs);
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  78 */     Player player = env.getPlayer();
/*  79 */     QuestState qs = player.getQuestStateList().getQuestState(1054);
/*  80 */     if (qs == null) {
/*  81 */       return false;
/*     */     }
/*  83 */     int var = qs.getQuestVarById(0);
/*  84 */     int targetId = 0;
/*  85 */     if (env.getVisibleObject() instanceof Npc) {
/*  86 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  88 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  90 */       if (targetId == 204647) {
/*  91 */         return defaultQuestEndDialog(env);
/*     */       }
/*  93 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  95 */       return false;
/*     */     } 
/*  97 */     if (targetId == 730024) {
/*     */       
/*  99 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 102 */           if (var == 0)
/* 103 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 105 */           if (var == 0) {
/*     */             
/* 107 */             qs.setQuestVarById(0, var + 1);
/* 108 */             updateQuestStatus(player, qs);
/* 109 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 111 */             return true;
/*     */           } 
/* 113 */           return false;
/*     */       } 
/*     */     
/* 116 */     } else if (targetId == 204647) {
/*     */       
/* 118 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 121 */           if (var == 1)
/* 122 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 123 */           if (var == 4)
/* 124 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 125 */           if (var == 5)
/* 126 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 33:
/* 128 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 130 */             qs.setStatus(QuestStatus.REWARD);
/* 131 */             updateQuestStatus(player, qs);
/* 132 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           
/* 135 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 2377:
/* 137 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 187));
/* 138 */           return false;
/*     */         case 10001:
/* 140 */           if (var == 1) {
/*     */             
/* 142 */             qs.setQuestVarById(0, var + 1);
/* 143 */             updateQuestStatus(player, qs);
/* 144 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 145 */             return true;
/*     */           } 
/*     */         case 10004:
/* 148 */           if (var == 4) {
/*     */             
/* 150 */             ItemService.decreaseItemCountByItemId(player, 182201606, 1L);
/* 151 */             ItemService.decreaseItemCountByItemId(player, 182201607, 1L);
/* 152 */             qs.setQuestVarById(0, 5);
/* 153 */             updateQuestStatus(player, qs);
/* 154 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 155 */             return true;
/*     */           } 
/* 157 */           return false;
/*     */       } 
/*     */     
/* 160 */     } else if (targetId == 730008) {
/*     */       
/* 162 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 165 */           if (var == 2)
/* 166 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 10002:
/* 168 */           if (var == 2) {
/*     */             
/* 170 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201606, 1)));
/* 171 */             qs.setQuestVarById(0, var + 1);
/* 172 */             updateQuestStatus(player, qs);
/* 173 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 174 */             return true;
/*     */           } 
/* 176 */           return false;
/*     */       } 
/*     */     
/* 179 */     } else if (targetId == 730019) {
/*     */       
/* 181 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 184 */           if (var == 3)
/* 185 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 10003:
/* 187 */           if (var == 3) {
/*     */             
/* 189 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201607, 1)));
/* 190 */             qs.setQuestVarById(0, var + 1);
/* 191 */             updateQuestStatus(player, qs);
/* 192 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 193 */             return true;
/*     */           } 
/* 195 */           return false;
/*     */       } 
/*     */     } 
/* 198 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1054ThePowerofElim.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */