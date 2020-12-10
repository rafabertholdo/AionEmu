/*     */ package quest.eltnen;
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
/*     */ public class _1300OrdersfromTelemachus
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1300;
/*     */   
/*     */   public _1300OrdersfromTelemachus() {
/*  38 */     super(Integer.valueOf(1300));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203901).addOnTalkEvent(1300);
/*  45 */     this.qe.setQuestEnterZone(ZoneName.ELTNEN_FORTRESS).add(1300);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  51 */     Player player = env.getPlayer();
/*  52 */     QuestState qs = player.getQuestStateList().getQuestState(1300);
/*  53 */     boolean lvlCheck = QuestService.checkLevelRequirement(1300, player.getCommonData().getLevel());
/*  54 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  55 */       return false; 
/*  56 */     qs.setStatus(QuestStatus.START);
/*  57 */     updateQuestStatus(player, qs);
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  64 */     Player player = env.getPlayer();
/*  65 */     QuestState qs = player.getQuestStateList().getQuestState(1300);
/*  66 */     if (qs == null) {
/*  67 */       return false;
/*     */     }
/*  69 */     int targetId = 0;
/*  70 */     if (env.getVisibleObject() instanceof Npc)
/*  71 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  72 */     if (targetId != 203901)
/*  73 */       return false; 
/*  74 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  76 */       if (env.getDialogId().intValue() == 25) {
/*     */         
/*  78 */         qs.setQuestVar(1);
/*  79 */         qs.setStatus(QuestStatus.REWARD);
/*  80 */         updateQuestStatus(player, qs);
/*  81 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */       } 
/*     */       
/*  84 */       return defaultQuestStartDialog(env);
/*     */     } 
/*  86 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  88 */       if (env.getDialogId().intValue() == 17) {
/*     */         
/*  90 */         int[] ids = { 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039, 1040, 1041, 1042, 1043 };
/*  91 */         for (int id : ids)
/*     */         {
/*  93 */           QuestService.startQuest(new QuestEnv(env.getVisibleObject(), env.getPlayer(), Integer.valueOf(id), env.getDialogId()), QuestStatus.LOCKED);
/*     */         }
/*     */       } 
/*  96 */       return defaultQuestEndDialog(env);
/*     */     } 
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 104 */     if (zoneName != ZoneName.ELTNEN_FORTRESS)
/* 105 */       return false; 
/* 106 */     Player player = env.getPlayer();
/* 107 */     QuestState qs = player.getQuestStateList().getQuestState(1300);
/* 108 */     if (qs != null)
/* 109 */       return false; 
/* 110 */     env.setQuestId(Integer.valueOf(1300));
/* 111 */     QuestService.startQuest(env, QuestStatus.START);
/* 112 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1300OrdersfromTelemachus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */