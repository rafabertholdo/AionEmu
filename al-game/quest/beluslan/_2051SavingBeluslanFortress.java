/*     */ package quest.beluslan;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
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
/*     */ 
/*     */ 
/*     */ public class _2051SavingBeluslanFortress
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2051;
/*  44 */   private static final int[] npc_ids = new int[] { 204702, 204733, 204206, 278040, 700285 };
/*     */ 
/*     */   
/*     */   public _2051SavingBeluslanFortress() {
/*  48 */     super(Integer.valueOf(2051));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  54 */     this.qe.addQuestLvlUp(2051);
/*  55 */     this.qe.setQuestItemIds(182204302).add(2051);
/*  56 */     for (int npc_id : npc_ids)
/*  57 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2051); 
/*  58 */     this.deletebleItems = new int[] { 182204302 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  64 */     Player player = env.getPlayer();
/*  65 */     QuestState qs = player.getQuestStateList().getQuestState(2051);
/*  66 */     boolean lvlCheck = QuestService.checkLevelRequirement(2051, player.getCommonData().getLevel());
/*  67 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  68 */       return false;
/*     */     }
/*  70 */     QuestState qs2 = player.getQuestStateList().getQuestState(2500);
/*  71 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  72 */       return false; 
/*  73 */     qs.setStatus(QuestStatus.START);
/*  74 */     updateQuestStatus(player, qs);
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  81 */     Player player = env.getPlayer();
/*  82 */     QuestState qs = player.getQuestStateList().getQuestState(2051);
/*  83 */     if (qs == null) {
/*  84 */       return false;
/*     */     }
/*  86 */     int var = qs.getQuestVarById(0);
/*  87 */     int targetId = 0;
/*  88 */     if (env.getVisibleObject() instanceof Npc) {
/*  89 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  91 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  93 */       if (targetId == 204702) {
/*     */         
/*  95 */         if (env.getDialogId().intValue() == -1)
/*  96 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  97 */         if (env.getDialogId().intValue() == 1009)
/*  98 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  99 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 101 */       return false;
/*     */     } 
/* 103 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 105 */       return false;
/*     */     }
/* 107 */     if (targetId == 204702) {
/*     */       
/* 109 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 112 */           if (var == 0)
/* 113 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 115 */           if (var == 0) {
/*     */             
/* 117 */             qs.setQuestVarById(0, var + 1);
/* 118 */             updateQuestStatus(player, qs);
/* 119 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 120 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 124 */     } else if (targetId == 204733) {
/*     */       
/* 126 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 129 */           if (var == 1)
/* 130 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 131 */           if (var == 2)
/* 132 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 133 */           if (var == 6)
/* 134 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 10001:
/* 136 */           if (var == 1) {
/*     */             
/* 138 */             qs.setQuestVarById(0, var + 1);
/* 139 */             updateQuestStatus(player, qs);
/* 140 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 141 */             return true;
/*     */           } 
/*     */         case 10002:
/* 144 */           if (var == 2) {
/*     */             
/* 146 */             qs.setQuestVarById(0, var + 1);
/* 147 */             updateQuestStatus(player, qs);
/* 148 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 149 */             return true;
/*     */           } 
/*     */         case 10006:
/* 152 */           if (var == 6) {
/*     */             
/* 154 */             qs.setQuestVarById(0, var + 1);
/* 155 */             updateQuestStatus(player, qs);
/* 156 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 157 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 161 */     } else if (targetId == 204206) {
/*     */       
/* 163 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 166 */           if (var == 3)
/* 167 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 10003:
/* 169 */           if (var == 3) {
/*     */             
/* 171 */             qs.setQuestVarById(0, var + 1);
/* 172 */             updateQuestStatus(player, qs);
/* 173 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 174 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 178 */     } else if (targetId == 278040) {
/*     */       
/* 180 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 183 */           if (var == 4)
/* 184 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 185 */           if (var == 5)
/* 186 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 33:
/* 188 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 190 */             qs.setQuestVarById(0, var + 1);
/* 191 */             updateQuestStatus(player, qs);
/* 192 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204302, 1)));
/* 193 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           } 
/*     */           
/* 196 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 10004:
/* 198 */           if (var == 4) {
/*     */             
/* 200 */             qs.setQuestVarById(0, var + 1);
/* 201 */             updateQuestStatus(player, qs);
/* 202 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 203 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 213 */     final Player player = env.getPlayer();
/* 214 */     final int id = item.getItemTemplate().getTemplateId();
/* 215 */     final int itemObjId = item.getObjectId();
/*     */     
/* 217 */     if (id != 182204302) {
/* 218 */       return false;
/*     */     }
/* 220 */     final QuestState qs = player.getQuestStateList().getQuestState(2051);
/* 221 */     if (qs == null || qs.getQuestVarById(0) != 7)
/* 222 */       return false; 
/* 223 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/* 224 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 228 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 229 */             ItemService.removeItemFromInventoryByItemId(player, 182204302);
/* 230 */             qs.setStatus(QuestStatus.REWARD);
/* 231 */             _2051SavingBeluslanFortress.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 234 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2051SavingBeluslanFortress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */