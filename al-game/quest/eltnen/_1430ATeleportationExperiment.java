/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.TeleportService;
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
/*     */ public class _1430ATeleportationExperiment
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1430;
/*     */   
/*     */   public _1430ATeleportationExperiment() {
/*  38 */     super(Integer.valueOf(1430));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203919).addOnQuestStart(1430);
/*  45 */     this.qe.setNpcQuestData(203919).addOnTalkEvent(1430);
/*  46 */     this.qe.setNpcQuestData(203337).addOnTalkEvent(1430);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     int targetId = 0;
/*  54 */     if (env.getVisibleObject() instanceof Npc)
/*  55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1430);
/*  57 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  59 */       if (targetId == 203919)
/*     */       {
/*  61 */         if (env.getDialogId().intValue() == 25) {
/*  62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*     */         }
/*  64 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/*  68 */     else if (targetId == 203337) {
/*     */ 
/*     */       
/*  71 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  73 */         if (env.getDialogId().intValue() == 25)
/*  74 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  75 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  77 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  78 */           updateQuestStatus(player, qs);
/*  79 */           qs.setStatus(QuestStatus.REWARD);
/*  80 */           TeleportService.teleportTo(player, 220020000, 1, 638.0F, 2337.0F, 425.0F, (byte)20, 0);
/*     */         } else {
/*     */           
/*  83 */           return defaultQuestStartDialog(env);
/*     */         }
/*     */       
/*  86 */       } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/*  88 */         if (env.getDialogId().intValue() == 25)
/*  89 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
/*  90 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  92 */           qs.setQuestVar(2);
/*  93 */           updateQuestStatus(player, qs);
/*  94 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  97 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1430ATeleportationExperiment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */