/*     */ package quest.beluslan;
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
/*     */ public class _2578ARingforLuck
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2578;
/*     */   
/*     */   public _2578ARingforLuck() {
/*  27 */     super(Integer.valueOf(2578));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  33 */     this.qe.setNpcQuestData(204741).addOnTalkEvent(2578);
/*  34 */     this.qe.setNpcQuestData(790017).addOnTalkEvent(2578);
/*  35 */     this.qe.setNpcQuestData(204746).addOnTalkEvent(2578);
/*  36 */     this.qe.setQuestItemIds(182204453).add(2578);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  42 */     Player player = env.getPlayer();
/*  43 */     QuestState qs = player.getQuestStateList().getQuestState(2578);
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
/*  57 */     } else if (targetId == 204741) {
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
/*  68 */           return true;
/*     */         } 
/*     */         
/*  71 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  74 */     } else if (targetId == 790017) {
/*     */       
/*  76 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  78 */         if (env.getDialogId().intValue() == 25)
/*  79 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  80 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  82 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  83 */           updateQuestStatus(player, qs);
/*  84 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  85 */           return true;
/*     */         } 
/*     */         
/*  88 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  91 */     } else if (targetId == 204746) {
/*     */       
/*  93 */       if (qs != null) {
/*     */         
/*  95 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*     */         {
/*  97 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/*  99 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 101 */           ItemService.removeItemFromInventoryByItemId(player, 182204453);
/* 102 */           qs.setQuestVar(1);
/* 103 */           qs.setStatus(QuestStatus.REWARD);
/* 104 */           updateQuestStatus(player, qs);
/* 105 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 108 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 117 */     final Player player = env.getPlayer();
/* 118 */     final int id = item.getItemTemplate().getTemplateId();
/* 119 */     final int itemObjId = item.getObjectId();
/*     */     
/* 121 */     if (id != 182204453)
/* 122 */       return false; 
/* 123 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 125 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 129 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 131 */             _2578ARingforLuck.this.sendQuestDialog(player, 0, 4);
/*     */           }
/*     */         }3000L);
/* 134 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2578ARingforLuck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */