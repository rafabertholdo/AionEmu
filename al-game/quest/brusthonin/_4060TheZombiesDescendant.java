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
/*     */ public class _4060TheZombiesDescendant
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 4060;
/*     */   
/*     */   public _4060TheZombiesDescendant() {
/*  27 */     super(Integer.valueOf(4060));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  33 */     this.qe.setNpcQuestData(205156).addOnTalkEvent(4060);
/*  34 */     this.qe.setNpcQuestData(204143).addOnTalkEvent(4060);
/*  35 */     this.qe.setNpcQuestData(204731).addOnTalkEvent(4060);
/*  36 */     this.qe.setNpcQuestData(205204).addOnTalkEvent(4060);
/*  37 */     this.qe.setQuestItemIds(182209037).add(4060);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  43 */     Player player = env.getPlayer();
/*  44 */     QuestState qs = player.getQuestStateList().getQuestState(4060);
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
/*  58 */     } else if (targetId == 205156) {
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
/*     */           
/*  70 */           return true;
/*     */         } 
/*     */         
/*  73 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  76 */     } else if (targetId == 204143) {
/*     */       
/*  78 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  80 */         if (env.getDialogId().intValue() == 25)
/*  81 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  82 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  84 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  85 */           updateQuestStatus(player, qs);
/*  86 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  88 */           return true;
/*     */         } 
/*     */         
/*  91 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  94 */     } else if (targetId == 204731) {
/*     */       
/*  96 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/*  98 */         if (env.getDialogId().intValue() == 25)
/*  99 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 100 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 102 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 103 */           updateQuestStatus(player, qs);
/* 104 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 106 */           return true;
/*     */         } 
/*     */         
/* 109 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 112 */     } else if (targetId == 205204) {
/*     */       
/* 114 */       if (qs != null) {
/*     */         
/* 116 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*     */         {
/* 118 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/* 120 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 122 */           ItemService.removeItemFromInventoryByItemId(player, 182209037);
/* 123 */           qs.setQuestVar(1);
/* 124 */           qs.setStatus(QuestStatus.REWARD);
/* 125 */           updateQuestStatus(player, qs);
/* 126 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 129 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 138 */     final Player player = env.getPlayer();
/* 139 */     final int id = item.getItemTemplate().getTemplateId();
/* 140 */     final int itemObjId = item.getObjectId();
/*     */     
/* 142 */     if (id != 182209037)
/* 143 */       return false; 
/* 144 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 146 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 150 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 152 */             _4060TheZombiesDescendant.this.sendQuestDialog(player, 0, 4);
/*     */           }
/*     */         }3000L);
/* 155 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_4060TheZombiesDescendant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */