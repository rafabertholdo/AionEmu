/*     */ package quest.poeta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
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
/*     */ public class _1114TheNymphsGown
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1114;
/*  47 */   private static final int[] npc_ids = new int[] { 203075, 203058, 700008 };
/*     */ 
/*     */   
/*     */   public _1114TheNymphsGown() {
/*  51 */     super(Integer.valueOf(1114));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  57 */     this.qe.setQuestItemIds(182200214).add(1114);
/*  58 */     for (int npc_id : npc_ids) {
/*  59 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1114);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  65 */     final Player player = env.getPlayer();
/*  66 */     int targetId = 0;
/*  67 */     final QuestState qs = player.getQuestStateList().getQuestState(1114);
/*  68 */     if (env.getVisibleObject() instanceof Npc) {
/*  69 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  71 */     if (targetId == 0)
/*     */     {
/*  73 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  75 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  77 */           if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200226, 1))));
/*     */           
/*  79 */           QuestService.startQuest(env, QuestStatus.START);
/*  80 */           ItemService.removeItemFromInventoryByItemId(player, 182200214);
/*  81 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*     */           
/*  83 */           return true;
/*     */         } 
/*     */         
/*  86 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*     */       } 
/*     */     }
/*     */     
/*  90 */     if (qs == null) {
/*  91 */       return false;
/*     */     }
/*  93 */     int var = qs.getQuestVarById(0);
/*     */     
/*  95 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  97 */       if (targetId == 203075 && var == 4) {
/*     */         
/*  99 */         if (env.getDialogId().intValue() == -1)
/* 100 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 101 */         if (env.getDialogId().intValue() == 1009)
/* 102 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6); 
/* 103 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 105 */       if (targetId == 203058 && var == 3) {
/* 106 */         return defaultQuestEndDialog(env);
/*     */       }
/* 108 */     } else if (qs.getStatus() != QuestStatus.START) {
/* 109 */       return false;
/*     */     } 
/* 111 */     if (targetId == 203075) {
/*     */       
/* 113 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 116 */           if (var == 0)
/* 117 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 118 */           if (var == 2)
/* 119 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 120 */           if (var == 3) {
/* 121 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */           }
/*     */         case 1009:
/* 124 */           if (var == 2) {
/*     */             
/* 126 */             qs.setQuestVarById(0, var + 2);
/* 127 */             qs.setStatus(QuestStatus.REWARD);
/* 128 */             updateQuestStatus(player, qs);
/* 129 */             ItemService.removeItemFromInventoryByItemId(player, 182200217);
/* 130 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           } 
/* 132 */           if (var == 3) {
/*     */             
/* 134 */             qs.setQuestVarById(0, var + 1);
/* 135 */             qs.setStatus(QuestStatus.REWARD);
/* 136 */             updateQuestStatus(player, qs);
/* 137 */             ItemService.removeItemFromInventoryByItemId(player, 182200217);
/* 138 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           } 
/*     */         case 10000:
/* 141 */           if (var == 0) {
/*     */             
/* 143 */             qs.setQuestVarById(0, var + 1);
/* 144 */             updateQuestStatus(player, qs);
/* 145 */             ItemService.removeItemFromInventoryByItemId(player, 182200226);
/* 146 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 147 */             return true;
/*     */           } 
/*     */         case 10001:
/* 150 */           if (var == 2) {
/*     */             
/* 152 */             qs.setQuestVarById(0, var + 1);
/* 153 */             updateQuestStatus(player, qs);
/* 154 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 155 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 159 */     } else if (targetId == 700008) {
/*     */       
/* 161 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 164 */           if (var == 1) {
/*     */             
/* 166 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 167 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 168 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 169 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 173 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 174 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 175 */                     for (VisibleObject obj : player.getKnownList().getKnownObjects().values()) {
/*     */                       
/* 177 */                       if (!(obj instanceof Npc))
/*     */                         continue; 
/* 179 */                       if (((Npc)obj).getNpcId() != 203175)
/*     */                         continue; 
/* 181 */                       ((Npc)obj).getAggroList().addDamage((Creature)player, 50);
/*     */                     } 
/*     */                     
/* 184 */                     if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200217, 1))));
/* 185 */                     qs.setQuestVarById(0, 2);
/* 186 */                     _1114TheNymphsGown.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/* 190 */           return true;
/*     */       } 
/*     */     } 
/* 193 */     if (targetId == 203058)
/*     */     {
/* 195 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 198 */           if (var == 3)
/* 199 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 10002:
/* 201 */           if (var == 3) {
/*     */             
/* 203 */             qs.setStatus(QuestStatus.REWARD);
/* 204 */             updateQuestStatus(player, qs);
/* 205 */             ItemService.removeItemFromInventoryByItemId(player, 182200217);
/* 206 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */         case 10001:
/* 209 */           if (var == 3) {
/*     */             
/* 211 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 212 */             return true;
/*     */           } 
/*     */           break;
/*     */       }  } 
/* 216 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 222 */     Player player = env.getPlayer();
/* 223 */     int id = item.getItemTemplate().getTemplateId();
/* 224 */     int itemObjId = item.getObjectId();
/* 225 */     QuestState qs = player.getQuestStateList().getQuestState(1114);
/*     */     
/* 227 */     if (id != 182200214)
/* 228 */       return false; 
/* 229 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
/* 230 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/* 231 */       sendQuestDialog(player, 0, 4); 
/* 232 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1114TheNymphsGown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */