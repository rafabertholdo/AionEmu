/*     */ package quest.beluslan;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
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
/*     */ 
/*     */ public class _2054LightuptheLighthouse
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2054;
/*  48 */   private static final int[] npc_ids = new int[] { 204768, 204739, 730109, 730140, 700287 };
/*     */ 
/*     */   
/*     */   public _2054LightuptheLighthouse() {
/*  52 */     super(Integer.valueOf(2054));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  58 */     this.qe.setQuestItemIds(182204308).add(2054);
/*  59 */     this.qe.addQuestLvlUp(2054);
/*  60 */     for (int npc_id : npc_ids)
/*  61 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2054); 
/*  62 */     this.deletebleItems = new int[] { 182204309, 182204308 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  68 */     Player player = env.getPlayer();
/*  69 */     QuestState qs = player.getQuestStateList().getQuestState(2054);
/*  70 */     boolean lvlCheck = QuestService.checkLevelRequirement(2054, player.getCommonData().getLevel());
/*  71 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  72 */       return false;
/*     */     }
/*  74 */     QuestState qs2 = player.getQuestStateList().getQuestState(2500);
/*  75 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  76 */       return false; 
/*  77 */     qs.setStatus(QuestStatus.START);
/*  78 */     updateQuestStatus(player, qs);
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  85 */     final Player player = env.getPlayer();
/*  86 */     final QuestState qs = player.getQuestStateList().getQuestState(2054);
/*  87 */     Npc npc = (Npc)env.getVisibleObject();
/*     */     
/*  89 */     if (qs == null) {
/*  90 */       return false;
/*     */     }
/*  92 */     int var = qs.getQuestVarById(0);
/*  93 */     int targetId = 0;
/*  94 */     if (env.getVisibleObject() instanceof Npc) {
/*  95 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  97 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  99 */       if (targetId == 204768) {
/*     */         
/* 101 */         if (env.getDialogId().intValue() == -1)
/* 102 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 103 */         if (env.getDialogId().intValue() == 1009)
/* 104 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/* 105 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 107 */       return false;
/*     */     } 
/* 109 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 111 */       return false;
/*     */     }
/* 113 */     if (targetId == 204768) {
/*     */       
/* 115 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 118 */           if (var == 0)
/* 119 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 121 */           if (var == 0) {
/*     */             
/* 123 */             qs.setQuestVarById(0, var + 1);
/* 124 */             updateQuestStatus(player, qs);
/* 125 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 126 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 130 */     } else if (targetId == 204739) {
/*     */       
/* 132 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 135 */           if (var == 1)
/* 136 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 1353:
/* 138 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 237));
/*     */           break;
/*     */         case 10001:
/* 141 */           if (var == 1) {
/*     */             
/* 143 */             qs.setQuestVarById(0, var + 1);
/* 144 */             updateQuestStatus(player, qs);
/* 145 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 146 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 150 */     } else if (targetId == 730109) {
/*     */       
/* 152 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 155 */           if (var == 3)
/* 156 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 10003:
/* 158 */           if (var == 3) {
/*     */             
/* 160 */             QuestService.addNewSpawn(220040000, 1, 213912, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 162 */             npc.getController().onDespawn(true);
/* 163 */             npc.getController().scheduleRespawn();
/* 164 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 165 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 169 */     } else if (targetId == 730140) {
/*     */       
/* 171 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 174 */           if (var == 3)
/* 175 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120); 
/*     */         case 10004:
/* 177 */           if (var == 3) {
/*     */             
/* 179 */             qs.setQuestVarById(0, var + 1);
/* 180 */             updateQuestStatus(player, qs);
/* 181 */             npc.getController().onDespawn(true);
/* 182 */             npc.getController().scheduleRespawn();
/* 183 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204309, 1)));
/* 184 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 185 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 189 */     } else if (targetId == 700287 && var == 4) {
/*     */       
/* 191 */       if (env.getDialogId().intValue() == -1) {
/*     */         
/* 193 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 194 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 195 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 196 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 200 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 201 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 202 */                 ItemService.removeItemFromInventoryByItemId(player, 182204309);
/* 203 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 238));
/* 204 */                 qs.setStatus(QuestStatus.REWARD);
/* 205 */                 _2054LightuptheLighthouse.this.updateQuestStatus(player, qs);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 216 */     final Player player = env.getPlayer();
/* 217 */     final int id = item.getItemTemplate().getTemplateId();
/* 218 */     final int itemObjId = item.getObjectId();
/*     */     
/* 220 */     if (id != 182204308) {
/* 221 */       return false;
/*     */     }
/* 223 */     final QuestState qs = player.getQuestStateList().getQuestState(2054);
/* 224 */     if (qs == null || qs.getQuestVarById(0) != 2)
/* 225 */       return false; 
/* 226 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 1000, 0, 0), true);
/* 227 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 231 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 232 */             ItemService.removeItemFromInventoryByItemId(player, 182204308);
/* 233 */             qs.setQuestVarById(0, 3);
/* 234 */             _2054LightuptheLighthouse.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }1000L);
/* 237 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2054LightuptheLighthouse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */