/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.ZoneService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ public class _1361FindingDrinkingWater
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1361;
/*     */   
/*     */   public _1361FindingDrinkingWater() {
/*  50 */     super(Integer.valueOf(1361));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  56 */     this.qe.setQuestItemIds(182201326).add(1361);
/*  57 */     this.qe.setNpcQuestData(203943).addOnQuestStart(1361);
/*  58 */     this.qe.setNpcQuestData(203943).addOnTalkEvent(1361);
/*  59 */     this.qe.setNpcQuestData(700173).addOnTalkEvent(1361);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/*  65 */     final Player player = env.getPlayer();
/*  66 */     final int id = item.getItemTemplate().getTemplateId();
/*  67 */     final int itemObjId = item.getObjectId();
/*     */     
/*  69 */     if (id != 182201326)
/*  70 */       return false; 
/*  71 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.MYSTIC_SPRING_OF_ANATHE))
/*  72 */       return false; 
/*  73 */     final QuestState qs = player.getQuestStateList().getQuestState(1361);
/*  74 */     if (qs == null)
/*  75 */       return false; 
/*  76 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*  77 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  81 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*  82 */             ItemService.removeItemFromInventory(player, item);
/*  83 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201327, 1)));
/*  84 */             qs.setQuestVar(1);
/*  85 */             _1361FindingDrinkingWater.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  94 */     final Player player = env.getPlayer();
/*  95 */     int targetId = 0;
/*  96 */     if (env.getVisibleObject() instanceof Npc)
/*  97 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  98 */     QuestState qs = player.getQuestStateList().getQuestState(1361);
/*  99 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/* 101 */       if (targetId == 203943) {
/*     */         
/* 103 */         if (env.getDialogId().intValue() == 25)
/* 104 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 105 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/* 107 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182201326, 1)))) {
/* 108 */             return defaultQuestStartDialog(env);
/*     */           }
/* 110 */           return true;
/*     */         } 
/*     */         
/* 113 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } else {
/* 116 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/* 118 */         if (env.getDialogId().intValue() == 25)
/* 119 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 120 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 122 */           qs.setQuestVar(2);
/* 123 */           qs.setStatus(QuestStatus.REWARD);
/* 124 */           updateQuestStatus(player, qs);
/* 125 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 128 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */       
/* 131 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 133 */         switch (targetId) {
/*     */ 
/*     */           
/*     */           case 700173:
/* 137 */             if (qs.getQuestVarById(0) == 1 && env.getDialogId().intValue() == -1) {
/*     */ 
/*     */               
/* 140 */               final int targetObjectId = env.getVisibleObject().getObjectId();
/* 141 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */               
/* 143 */               PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */               
/* 145 */               ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                   {
/*     */                     public void run()
/*     */                     {
/* 149 */                       if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
/*     */                         return; 
/* 151 */                       QuestState qs = player.getQuestStateList().getQuestState(1361);
/* 152 */                       qs.setStatus(QuestStatus.REWARD);
/* 153 */                       _1361FindingDrinkingWater.this.updateQuestStatus(player, qs);
/* 154 */                       ItemService.removeItemFromInventoryByItemId(player, 182201327);
/* 155 */                       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                       
/* 157 */                       PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     }
/*     */                   }3000L);
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       }
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1361FindingDrinkingWater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */