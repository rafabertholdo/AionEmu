/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
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
/*     */ 
/*     */ 
/*     */ public class _1367MabangtahsFeast
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1367;
/*     */   
/*     */   public _1367MabangtahsFeast() {
/*  38 */     super(Integer.valueOf(1367));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(204023).addOnQuestStart(1367);
/*  45 */     this.qe.setNpcQuestData(204023).addOnTalkEvent(1367);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  51 */     Player player = env.getPlayer();
/*  52 */     int targetId = 0;
/*  53 */     if (env.getVisibleObject() instanceof Npc)
/*  54 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  55 */     QuestState qs = player.getQuestStateList().getQuestState(1367);
/*  56 */     if (targetId == 204023) {
/*     */       
/*  58 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  60 */         if (env.getDialogId().intValue() == 25) {
/*  61 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  63 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  65 */       if (qs.getStatus() == QuestStatus.START) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  70 */         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 0) {
/*     */           
/*  72 */           long itemCount = player.getInventory().getItemCountByItemId(182201333);
/*  73 */           long itemCount1 = player.getInventory().getItemCountByItemId(182201332);
/*  74 */           long itemCount2 = player.getInventory().getItemCountByItemId(182201331);
/*  75 */           if (itemCount > 1L || itemCount1 > 5L || itemCount2 > 0L)
/*     */           {
/*  77 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */           }
/*     */ 
/*     */           
/*  81 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */         } 
/*     */         
/*  84 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  86 */           long itemCount2 = player.getInventory().getItemCountByItemId(182201331);
/*  87 */           if (itemCount2 > 0L) {
/*     */             
/*  89 */             qs.setStatus(QuestStatus.REWARD);
/*  90 */             updateQuestStatus(player, qs);
/*  91 */             qs.setQuestVarById(0, 1);
/*  92 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           
/*  95 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */         } 
/*  97 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  99 */           long itemCount1 = player.getInventory().getItemCountByItemId(182201332);
/* 100 */           if (itemCount1 > 4L) {
/*     */             
/* 102 */             qs.setStatus(QuestStatus.REWARD);
/* 103 */             updateQuestStatus(player, qs);
/* 104 */             qs.setQuestVarById(0, 2);
/* 105 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           } 
/*     */           
/* 108 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */         } 
/* 110 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 112 */           long itemCount = player.getInventory().getItemCountByItemId(182201333);
/* 113 */           if (itemCount > 1L) {
/*     */             
/* 115 */             qs.setStatus(QuestStatus.REWARD);
/* 116 */             updateQuestStatus(player, qs);
/* 117 */             qs.setQuestVarById(0, 3);
/* 118 */             updateQuestStatus(player, qs);
/* 119 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */           } 
/*     */           
/* 122 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */         } 
/*     */         
/* 125 */         return defaultQuestStartDialog(env);
/*     */       } 
/* 127 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 129 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1367MabangtahsFeast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */