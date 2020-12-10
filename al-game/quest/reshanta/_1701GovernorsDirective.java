/*     */ package quest.reshanta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ public class _1701GovernorsDirective
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1701;
/*     */   
/*     */   public _1701GovernorsDirective() {
/*  39 */     super(Integer.valueOf(1701));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(278501).addOnTalkEvent(1701);
/*  46 */     this.qe.setQuestEnterZone(ZoneName.LATIS_PLAZA_400010000).add(1701);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     QuestState qs = player.getQuestStateList().getQuestState(1701);
/*  54 */     boolean lvlCheck = QuestService.checkLevelRequirement(1701, player.getCommonData().getLevel());
/*  55 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  56 */       return false; 
/*  57 */     qs.setStatus(QuestStatus.START);
/*  58 */     updateQuestStatus(player, qs);
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  65 */     Player player = env.getPlayer();
/*  66 */     QuestState qs = player.getQuestStateList().getQuestState(1701);
/*  67 */     if (qs == null) {
/*  68 */       return false;
/*     */     }
/*  70 */     int targetId = 0;
/*  71 */     if (env.getVisibleObject() instanceof Npc)
/*  72 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  73 */     if (targetId != 278501)
/*  74 */       return false; 
/*  75 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  77 */       if (env.getDialogId().intValue() == 25)
/*  78 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  79 */       if (env.getDialogId().intValue() == 1009) {
/*     */         
/*  81 */         qs.setStatus(QuestStatus.REWARD);
/*  82 */         qs.setQuestVarById(0, 1);
/*  83 */         updateQuestStatus(player, qs);
/*  84 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */       } 
/*  86 */       return false;
/*     */     } 
/*  88 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  90 */       if (env.getDialogId().intValue() == 17) {
/*     */         
/*  92 */         int[] ids = { 1071, 1072, 1073, 1074, 1075, 1076, 1077 };
/*  93 */         for (int id : ids)
/*     */         {
/*  95 */           QuestService.startQuest(new QuestEnv(env.getVisibleObject(), env.getPlayer(), Integer.valueOf(id), env.getDialogId()), QuestStatus.LOCKED);
/*     */         }
/*     */       } 
/*  98 */       return defaultQuestEndDialog(env);
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 106 */     if (zoneName != ZoneName.LATIS_PLAZA_400010000)
/* 107 */       return false; 
/* 108 */     Player player = env.getPlayer();
/* 109 */     QuestState qs = player.getQuestStateList().getQuestState(1701);
/* 110 */     if (qs != null)
/* 111 */       return false; 
/* 112 */     env.setQuestId(Integer.valueOf(1701));
/* 113 */     QuestService.startQuest(env, QuestStatus.START);
/* 114 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_1701GovernorsDirective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */