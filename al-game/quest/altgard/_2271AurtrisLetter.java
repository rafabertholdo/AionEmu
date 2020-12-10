/*     */ package quest.altgard;
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
/*     */ public class _2271AurtrisLetter
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2271;
/*     */   
/*     */   public _2271AurtrisLetter() {
/*  42 */     super(Integer.valueOf(2271));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(203655).addOnQuestStart(2271);
/*  49 */     this.qe.setNpcQuestData(203655).addOnTalkEvent(2271);
/*  50 */     this.qe.setNpcQuestData(203654).addOnTalkEvent(2271);
/*  51 */     this.qe.setNpcQuestData(203557).addOnTalkEvent(2271);
/*  52 */     this.deletebleItems = new int[] { 182203247 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  58 */     Player player = env.getPlayer();
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc)
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(2271);
/*  63 */     if (targetId == 203655) {
/*     */       
/*  65 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  67 */         if (env.getDialogId().intValue() == 25)
/*  68 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  69 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  71 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203247, 1)))) {
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
/*  85 */     } else if (targetId == 203654) {
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
/*     */           
/*  97 */           return true;
/*     */         } 
/*     */         
/* 100 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 103 */     } else if (targetId == 203557) {
/*     */       
/* 105 */       if (qs != null) {
/*     */         
/* 107 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 108 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 109 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 111 */           qs.setQuestVar(3);
/* 112 */           qs.setStatus(QuestStatus.REWARD);
/* 113 */           updateQuestStatus(player, qs);
/* 114 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 117 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 120 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2271AurtrisLetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */