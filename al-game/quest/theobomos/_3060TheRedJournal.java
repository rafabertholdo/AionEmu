/*     */ package quest.theobomos;
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
/*     */ public class _3060TheRedJournal
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3060;
/*     */   
/*     */   public _3060TheRedJournal() {
/*  27 */     super(Integer.valueOf(3060));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  33 */     this.qe.setNpcQuestData(798190).addOnTalkEvent(3060);
/*  34 */     this.qe.setNpcQuestData(798191).addOnTalkEvent(3060);
/*  35 */     this.qe.setNpcQuestData(798192).addOnTalkEvent(3060);
/*  36 */     this.qe.setNpcQuestData(798193).addOnTalkEvent(3060);
/*  37 */     this.qe.setQuestItemIds(182208043).add(3060);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  43 */     Player player = env.getPlayer();
/*  44 */     QuestState qs = player.getQuestStateList().getQuestState(3060);
/*     */     
/*  46 */     int targetId = 0;
/*  47 */     if (env.getVisibleObject() instanceof Npc)
/*  48 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  49 */     if (targetId == 0) {
/*     */       
/*  51 */       if (env.getDialogId().intValue() == 1002)
/*     */       {
/*  53 */         QuestService.startQuest(env, QuestStatus.START);
/*  54 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  55 */         return true;
/*     */       }
/*     */     
/*  58 */     } else if (targetId == 798190) {
/*     */       
/*  60 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  62 */         if (env.getDialogId().intValue() == 25)
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  64 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  66 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  67 */           updateQuestStatus(player, qs);
/*  68 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  69 */           return true;
/*     */         } 
/*     */         
/*  72 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  75 */     } else if (targetId == 798191) {
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
/*  86 */           return true;
/*     */         } 
/*     */         
/*  89 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  92 */     } else if (targetId == 798192) {
/*     */       
/*  94 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/*  96 */         if (env.getDialogId().intValue() == 25)
/*  97 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*  98 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 100 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 101 */           updateQuestStatus(player, qs);
/* 102 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 103 */           return true;
/*     */         } 
/*     */         
/* 106 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 109 */     } else if (targetId == 798193) {
/*     */       
/* 111 */       if (qs != null) {
/*     */         
/* 113 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*     */         {
/* 115 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/* 117 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 119 */           ItemService.removeItemFromInventoryByItemId(player, 182208043);
/* 120 */           qs.setQuestVar(1);
/* 121 */           qs.setStatus(QuestStatus.REWARD);
/* 122 */           updateQuestStatus(player, qs);
/* 123 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 126 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 135 */     final Player player = env.getPlayer();
/* 136 */     final int id = item.getItemTemplate().getTemplateId();
/* 137 */     final int itemObjId = item.getObjectId();
/*     */     
/* 139 */     if (id != 182208043)
/* 140 */       return false; 
/* 141 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 143 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 147 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 149 */             _3060TheRedJournal.this.sendQuestDialog(player, 0, 4);
/*     */           }
/*     */         }3000L);
/* 152 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3060TheRedJournal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */