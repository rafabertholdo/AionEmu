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
/*     */ public class _2136TheLostAxe
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2136;
/*  44 */   private static final int[] npc_ids = new int[] { 700146, 790009 };
/*     */ 
/*     */   
/*     */   public _2136TheLostAxe() {
/*  48 */     super(Integer.valueOf(2136));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  54 */     this.qe.setQuestItemIds(182203130).add(2136);
/*  55 */     for (int npc_id : npc_ids) {
/*  56 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2136);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  62 */     final Player player = env.getPlayer();
/*  63 */     int targetId = 0;
/*  64 */     final QuestState qs = player.getQuestStateList().getQuestState(2136);
/*  65 */     if (env.getVisibleObject() instanceof Npc) {
/*  66 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  68 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  70 */       if (env.getDialogId().intValue() == 1002) {
/*     */         
/*  72 */         QuestService.startQuest(env, QuestStatus.START);
/*  73 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  74 */         return true;
/*     */       } 
/*     */       
/*  77 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*     */     } 
/*     */     
/*  80 */     if (qs == null) {
/*  81 */       return false;
/*     */     }
/*  83 */     int var = qs.getQuestVarById(0);
/*     */     
/*  85 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  87 */       if (targetId == 790009)
/*     */       {
/*  89 */         final Npc npc = (Npc)env.getVisibleObject();
/*  90 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/*  94 */                 npc.getController().onDelete();
/*     */               }
/*     */             },  10000L);
/*  97 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/* 100 */     } else if (qs.getStatus() != QuestStatus.START) {
/* 101 */       return false;
/*     */     } 
/* 103 */     if (targetId == 790009) {
/*     */       
/* 105 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 108 */           if (var == 1)
/* 109 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 111 */           if (var == 1) {
/*     */             
/* 113 */             qs.setStatus(QuestStatus.REWARD);
/* 114 */             updateQuestStatus(player, qs);
/* 115 */             ItemService.removeItemFromInventoryByItemId(player, 182203130);
/* 116 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 117 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           } 
/*     */         case 10001:
/* 120 */           if (var == 1) {
/*     */             
/* 122 */             qs.setStatus(QuestStatus.REWARD);
/* 123 */             updateQuestStatus(player, qs);
/* 124 */             ItemService.removeItemFromInventoryByItemId(player, 182203130);
/* 125 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 126 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           break;
/*     */       } 
/* 130 */     } else if (targetId == 700146) {
/*     */       
/* 132 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 135 */           if (var == 0) {
/*     */             
/* 137 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 138 */             final int instanceId = player.getInstanceId();
/* 139 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 140 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 141 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 145 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 146 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 147 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 59));
/* 148 */                     qs.setQuestVarById(0, 1);
/* 149 */                     _2136TheLostAxe.this.updateQuestStatus(player, qs);
/* 150 */                     QuestService.addNewSpawn(220010000, instanceId, 790009, 1088.5F, 2371.8F, 258.375F, (byte)87, true);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/* 154 */           return true;
/*     */       } 
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 163 */     Player player = env.getPlayer();
/* 164 */     int id = item.getItemTemplate().getTemplateId();
/* 165 */     int itemObjId = item.getObjectId();
/* 166 */     QuestState qs = player.getQuestStateList().getQuestState(2136);
/*     */     
/* 168 */     if (id != 182203130)
/* 169 */       return false; 
/* 170 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
/* 171 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/* 172 */       sendQuestDialog(player, 0, 4); 
/* 173 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2136TheLostAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */