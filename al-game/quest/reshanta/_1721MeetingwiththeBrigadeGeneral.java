/*     */ package quest.reshanta;
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
/*     */ public class _1721MeetingwiththeBrigadeGeneral
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1721;
/*     */   
/*     */   public _1721MeetingwiththeBrigadeGeneral() {
/*  42 */     super(Integer.valueOf(1721));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(278501).addOnQuestStart(1721);
/*  49 */     this.qe.setNpcQuestData(278501).addOnTalkEvent(1721);
/*  50 */     this.qe.setNpcQuestData(278503).addOnTalkEvent(1721);
/*  51 */     this.qe.setNpcQuestData(278502).addOnTalkEvent(1721);
/*  52 */     this.qe.setNpcQuestData(278518).addOnTalkEvent(1721);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  58 */     Player player = env.getPlayer();
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc)
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(1721);
/*  63 */     if (targetId == 278501) {
/*     */       
/*  65 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  67 */         if (env.getDialogId().intValue() == 25)
/*  68 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  69 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  71 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182202151, 1)))) {
/*  72 */             return defaultQuestStartDialog(env);
/*     */           }
/*  74 */           return true;
/*     */         } 
/*     */         
/*  77 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  79 */       if (qs.getStatus() == QuestStatus.START)
/*     */       {
/*  81 */         if (env.getDialogId().intValue() == 25) {
/*  82 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/*     */       }
/*  85 */     } else if (targetId == 278503) {
/*     */       
/*  87 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  89 */         if (env.getDialogId().intValue() == 25)
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  91 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  93 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  94 */           updateQuestStatus(player, qs);
/*  95 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  96 */           return true;
/*     */         } 
/*     */         
/*  99 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 102 */     } else if (targetId == 278502) {
/*     */       
/* 104 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 106 */         if (env.getDialogId().intValue() == 25)
/* 107 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 108 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 110 */           qs.setQuestVar(2);
/* 111 */           qs.setStatus(QuestStatus.REWARD);
/* 112 */           updateQuestStatus(player, qs);
/* 113 */           ItemService.removeItemFromInventoryByItemId(player, 182202151);
/* 114 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 115 */           return true;
/*     */         } 
/*     */         
/* 118 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 121 */     } else if (targetId == 278518) {
/*     */       
/* 123 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 125 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 128 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_1721MeetingwiththeBrigadeGeneral.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */