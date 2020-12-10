/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*     */ public class _1323LostJewelBox
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1323;
/*     */   
/*     */   public _1323LostJewelBox() {
/*  43 */     super(Integer.valueOf(1323));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(730019).addOnTalkEvent(1323);
/*  50 */     this.qe.setNpcQuestData(203939).addOnTalkEvent(1323);
/*  51 */     this.qe.setQuestItemIds(182201309).add(1323);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1323);
/*     */     
/*  60 */     int targetId = 0;
/*  61 */     if (env.getVisibleObject() instanceof Npc)
/*  62 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  63 */     if (targetId == 0) {
/*     */       
/*  65 */       if (env.getDialogId().intValue() == 1002)
/*     */       {
/*  67 */         QuestService.startQuest(env, QuestStatus.START);
/*  68 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  69 */         return true;
/*     */       }
/*     */     
/*  72 */     } else if (targetId == 730019) {
/*     */       
/*  74 */       if (qs != null)
/*     */       {
/*  76 */         if (env.getDialogId().intValue() == 25)
/*  77 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  78 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  80 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  81 */           updateQuestStatus(player, qs);
/*  82 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  84 */           return true;
/*     */         } 
/*     */         
/*  87 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  90 */     } else if (targetId == 203939) {
/*     */       
/*  92 */       if (qs != null) {
/*     */         
/*  94 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*     */         {
/*  96 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/*  98 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 100 */           ItemService.removeItemFromInventoryByItemId(player, 182201309);
/* 101 */           qs.setQuestVar(1);
/* 102 */           qs.setStatus(QuestStatus.REWARD);
/* 103 */           updateQuestStatus(player, qs);
/* 104 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 107 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 116 */     final Player player = env.getPlayer();
/* 117 */     final int id = item.getItemTemplate().getTemplateId();
/* 118 */     final int itemObjId = item.getObjectId();
/*     */     
/* 120 */     if (id != 182201309)
/* 121 */       return false; 
/* 122 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 124 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 128 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 130 */             _1323LostJewelBox.this.sendQuestDialog(player, 0, 4);
/*     */           }
/*     */         }3000L);
/* 133 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1323LostJewelBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */