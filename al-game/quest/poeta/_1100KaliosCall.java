/*     */ package quest.poeta;
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
/*     */ public class _1100KaliosCall
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1100;
/*     */   
/*     */   public _1100KaliosCall() {
/*  39 */     super(Integer.valueOf(1100));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(203067).addOnTalkEvent(1100);
/*  46 */     this.qe.setQuestEnterZone(ZoneName.AKARIOS_VILLAGE).add(1100);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     QuestState qs = player.getQuestStateList().getQuestState(1100);
/*  54 */     if (qs == null) {
/*  55 */       return false;
/*     */     }
/*  57 */     int targetId = 0;
/*  58 */     if (env.getVisibleObject() instanceof Npc)
/*  59 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  60 */     if (targetId != 203067)
/*  61 */       return false; 
/*  62 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  64 */       if (env.getDialogId().intValue() == 25) {
/*     */         
/*  66 */         qs.setQuestVar(1);
/*  67 */         qs.setStatus(QuestStatus.REWARD);
/*  68 */         updateQuestStatus(player, qs);
/*  69 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */       } 
/*     */       
/*  72 */       return defaultQuestStartDialog(env);
/*     */     } 
/*  74 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  76 */       if (env.getDialogId().intValue() == 17) {
/*     */         
/*  78 */         int[] ids = { 1001, 1002, 1003, 1004, 1005 };
/*  79 */         for (int id : ids)
/*     */         {
/*  81 */           QuestService.startQuest(new QuestEnv(env.getVisibleObject(), env.getPlayer(), Integer.valueOf(id), env.getDialogId()), QuestStatus.LOCKED);
/*     */         }
/*     */       } 
/*  84 */       return defaultQuestEndDialog(env);
/*     */     } 
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/*  92 */     if (zoneName != ZoneName.AKARIOS_VILLAGE)
/*  93 */       return false; 
/*  94 */     Player player = env.getPlayer();
/*  95 */     QuestState qs = player.getQuestStateList().getQuestState(1100);
/*  96 */     if (qs != null)
/*  97 */       return false; 
/*  98 */     env.setQuestId(Integer.valueOf(1100));
/*  99 */     QuestService.startQuest(env, QuestStatus.START);
/* 100 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1100KaliosCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */