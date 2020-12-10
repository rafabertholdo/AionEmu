/*     */ package quest.brusthonin;
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
/*     */ public class _4053GlossyHoe
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 4053;
/*     */   
/*     */   public _4053GlossyHoe() {
/*  27 */     super(Integer.valueOf(4053));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  33 */     this.qe.setNpcQuestData(205165).addOnTalkEvent(4053);
/*  34 */     this.qe.setNpcQuestData(205167).addOnTalkEvent(4053);
/*  35 */     this.qe.setNpcQuestData(205178).addOnTalkEvent(4053);
/*  36 */     this.qe.setQuestItemIds(182209031).add(4053);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  42 */     Player player = env.getPlayer();
/*  43 */     QuestState qs = player.getQuestStateList().getQuestState(4053);
/*     */     
/*  45 */     int targetId = 0;
/*  46 */     if (env.getVisibleObject() instanceof Npc)
/*  47 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  48 */     if (targetId == 0) {
/*     */       
/*  50 */       if (env.getDialogId().intValue() == 1002)
/*     */       {
/*  52 */         QuestService.startQuest(env, QuestStatus.START);
/*  53 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  54 */         return true;
/*     */       }
/*     */     
/*  57 */     } else if (targetId == 205165) {
/*     */       
/*  59 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  61 */         if (env.getDialogId().intValue() == 25)
/*  62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  63 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  65 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  66 */           updateQuestStatus(player, qs);
/*  67 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  69 */           return true;
/*     */         } 
/*     */         
/*  72 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  75 */     } else if (targetId == 205167) {
/*     */       
/*  77 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  79 */         if (env.getDialogId().intValue() == 25)
/*  80 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  81 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  83 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  84 */           updateQuestStatus(player, qs);
/*  85 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  87 */           return true;
/*     */         } 
/*     */         
/*  90 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  93 */     } else if (targetId == 205178) {
/*     */       
/*  95 */       if (qs != null) {
/*     */         
/*  97 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*     */         {
/*  99 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/* 101 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 103 */           ItemService.removeItemFromInventoryByItemId(player, 182209031);
/* 104 */           qs.setQuestVar(1);
/* 105 */           qs.setStatus(QuestStatus.REWARD);
/* 106 */           updateQuestStatus(player, qs);
/* 107 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 110 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 119 */     final Player player = env.getPlayer();
/* 120 */     final int id = item.getItemTemplate().getTemplateId();
/* 121 */     final int itemObjId = item.getObjectId();
/*     */     
/* 123 */     if (id != 182209031)
/* 124 */       return false; 
/* 125 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 127 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 131 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 133 */             _4053GlossyHoe.this.sendQuestDialog(player, 0, 4);
/*     */           }
/*     */         }3000L);
/* 136 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_4053GlossyHoe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */