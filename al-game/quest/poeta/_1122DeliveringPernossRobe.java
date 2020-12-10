/*     */ package quest.poeta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class _1122DeliveringPernossRobe
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1122;
/*     */   
/*     */   public _1122DeliveringPernossRobe() {
/*  43 */     super(Integer.valueOf(1122));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(203060).addOnQuestStart(1122);
/*  50 */     this.qe.setNpcQuestData(203060).addOnTalkEvent(1122);
/*  51 */     this.qe.setNpcQuestData(790001).addOnTalkEvent(1122);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     int targetId = 0;
/*  59 */     if (env.getVisibleObject() instanceof Npc)
/*  60 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(1122);
/*  62 */     if (targetId == 203060) {
/*     */       
/*  64 */       if (qs == null)
/*     */       {
/*  66 */         if (env.getDialogId().intValue() == 25)
/*  67 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  68 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  70 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182200216, 1)))) {
/*  71 */             return defaultQuestStartDialog(env);
/*     */           }
/*  73 */           return true;
/*     */         } 
/*     */         
/*  76 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  79 */     } else if (targetId == 790001) {
/*     */       
/*  81 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         long itemCount;
/*     */         
/*  84 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  87 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */           case 10000:
/*  89 */             itemCount = player.getInventory().getItemCountByItemId(182200218);
/*  90 */             if (itemCount > 0L) {
/*     */               
/*  92 */               qs.setQuestVar(1);
/*  93 */               qs.setStatus(QuestStatus.REWARD);
/*  94 */               updateQuestStatus(player, qs);
/*  95 */               ItemService.removeItemFromInventoryByItemId(player, 182200218);
/*  96 */               ItemService.removeItemFromInventoryByItemId(player, 182200216);
/*  97 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1523);
/*     */             } 
/*     */             
/* 100 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608);
/*     */           
/*     */           case 10001:
/* 103 */             itemCount = player.getInventory().getItemCountByItemId(182200219);
/* 104 */             if (itemCount > 0L) {
/*     */               
/* 106 */               qs.setQuestVar(2);
/* 107 */               qs.setStatus(QuestStatus.REWARD);
/* 108 */               updateQuestStatus(player, qs);
/* 109 */               ItemService.removeItemFromInventoryByItemId(player, 182200219);
/* 110 */               ItemService.removeItemFromInventoryByItemId(player, 182200216);
/* 111 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
/*     */             } 
/*     */             
/* 114 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608);
/*     */           case 10002:
/* 116 */             itemCount = player.getInventory().getItemCountByItemId(182200220);
/* 117 */             if (itemCount > 0L) {
/*     */               
/* 119 */               qs.setQuestVar(3);
/* 120 */               qs.setStatus(QuestStatus.REWARD);
/* 121 */               updateQuestStatus(player, qs);
/* 122 */               ItemService.removeItemFromInventoryByItemId(player, 182200220);
/* 123 */               ItemService.removeItemFromInventoryByItemId(player, 182200216);
/* 124 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
/*     */             } 
/*     */             
/* 127 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608);
/*     */         } 
/* 129 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 132 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/* 134 */         if (env.getDialogId().intValue() == -1 || env.getDialogId().intValue() == 1009)
/*     */         {
/* 136 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4 + qs.getQuestVars().getQuestVars());
/*     */         }
/* 138 */         if (env.getDialogId().intValue() == 17) {
/*     */           
/* 140 */           QuestService.questFinish(env, qs.getQuestVars().getQuestVars() - 1);
/* 141 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 142 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 146 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1122DeliveringPernossRobe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */