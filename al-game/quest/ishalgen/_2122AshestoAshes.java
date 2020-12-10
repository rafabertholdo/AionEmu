/*     */ package quest.ishalgen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*     */ public class _2122AshestoAshes
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2122;
/*     */   
/*     */   public _2122AshestoAshes() {
/*  46 */     super(Integer.valueOf(2122));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.setNpcQuestData(203551).addOnTalkEvent(2122);
/*  53 */     this.qe.setNpcQuestData(700148).addOnTalkEvent(2122);
/*  54 */     this.qe.setNpcQuestData(730029).addOnTalkEvent(2122);
/*  55 */     this.qe.setQuestItemIds(182203120).add(2122);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     final Player player = env.getPlayer();
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(2122);
/*     */     
/*  64 */     int targetId = 0;
/*  65 */     if (env.getVisibleObject() instanceof Npc)
/*  66 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  67 */     if (targetId == 0) {
/*     */       
/*  69 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 1002:
/*  72 */           QuestService.startQuest(env, QuestStatus.START);
/*  73 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  74 */           return true;
/*     */         case 1003:
/*  76 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  77 */           return true;
/*     */       } 
/*     */     
/*  80 */     } else if (targetId == 203551) {
/*     */       
/*  82 */       if (qs == null)
/*  83 */         return false; 
/*  84 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/*  86 */         int var = qs.getQuestVarById(0);
/*  87 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  90 */             if (var == 0)
/*  91 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */             break;
/*     */           case 1012:
/*  94 */             if (var == 0)
/*  95 */               ItemService.removeItemFromInventoryByItemId(player, 182203120); 
/*     */             break;
/*     */           case 10000:
/*  98 */             qs.setQuestVarById(0, var + 1);
/*  99 */             updateQuestStatus(player, qs);
/* 100 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 101 */             return true;
/*     */         } 
/*     */       
/* 104 */       } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/* 106 */         if (env.getDialogId().intValue() == -1) {
/* 107 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/* 109 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/* 112 */     } else if (targetId == 700148) {
/*     */       
/* 114 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/* 115 */         return true;
/*     */       }
/* 117 */     } else if (targetId == 730029) {
/*     */       
/* 119 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/* 121 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 122 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case -1:
/* 125 */             if (player.getInventory().getItemCountByItemId(182203133) < 3L) {
/*     */               
/* 127 */               sendQuestDialog(player, targetObjectId, 1693);
/* 128 */               return false;
/*     */             } 
/* 130 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 132 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 134 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 138 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/* 140 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 142 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     
/* 144 */                     _2122AshestoAshes.this.sendQuestDialog(player, targetObjectId, 1352);
/*     */                   }
/*     */                 }3000L);
/* 147 */             return false;
/*     */           case 10001:
/* 149 */             ItemService.removeItemFromInventoryByItemId(player, 182203133);
/* 150 */             qs.setStatus(QuestStatus.REWARD);
/* 151 */             updateQuestStatus(player, qs);
/* 152 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/* 153 */             return true;
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 164 */     Player player = env.getPlayer();
/* 165 */     int id = item.getItemTemplate().getTemplateId();
/* 166 */     int itemObjId = item.getObjectId();
/* 167 */     QuestState qs = player.getQuestStateList().getQuestState(2122);
/*     */     
/* 169 */     if (id != 182203120)
/* 170 */       return false; 
/* 171 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
/* 172 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/* 173 */       sendQuestDialog(player, 0, 4); 
/* 174 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2122AshestoAshes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */