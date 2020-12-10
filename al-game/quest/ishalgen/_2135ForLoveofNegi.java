/*     */ package quest.ishalgen;
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
/*     */ public class _2135ForLoveofNegi
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2135;
/*     */   
/*     */   public _2135ForLoveofNegi() {
/*  42 */     super(Integer.valueOf(2135));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(203532).addOnQuestStart(2135);
/*  49 */     this.qe.setNpcQuestData(203532).addOnTalkEvent(2135);
/*  50 */     this.qe.setNpcQuestData(203531).addOnTalkEvent(2135);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     int targetId = 0;
/*  58 */     if (env.getVisibleObject() instanceof Npc)
/*  59 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(2135);
/*  61 */     if (targetId == 203532) {
/*     */       
/*  63 */       if (qs == null) {
/*     */         
/*  65 */         if (env.getDialogId().intValue() == 25)
/*  66 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  67 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  69 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203131, 1)))) {
/*  70 */             return defaultQuestStartDialog(env);
/*     */           }
/*  72 */           return true;
/*     */         } 
/*     */         
/*  75 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  77 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/*  79 */         if (env.getDialogId().intValue() == 25)
/*  80 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  81 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  83 */           qs.setQuestVar(2);
/*  84 */           qs.setStatus(QuestStatus.REWARD);
/*  85 */           updateQuestStatus(player, qs);
/*  86 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  89 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  91 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  93 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  96 */     else if (targetId == 203531) {
/*     */       
/*  98 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/* 100 */         if (env.getDialogId().intValue() == 25)
/* 101 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 102 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 104 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 105 */           ItemService.removeItemFromInventoryByItemId(player, 182203131);
/* 106 */           updateQuestStatus(player, qs);
/* 107 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 109 */           return true;
/*     */         } 
/*     */         
/* 112 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2135ForLoveofNegi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */