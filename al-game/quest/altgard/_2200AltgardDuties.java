/*     */ package quest.altgard;
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
/*     */ public class _2200AltgardDuties
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2200;
/*     */   
/*     */   public _2200AltgardDuties() {
/*  39 */     super(Integer.valueOf(2200));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(203557).addOnTalkEvent(2200);
/*  46 */     this.qe.setQuestEnterZone(ZoneName.ALTGARD_FORTRESS_220030000).add(2200);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     QuestState qs = player.getQuestStateList().getQuestState(2200);
/*  54 */     if (qs == null) {
/*  55 */       return false;
/*     */     }
/*  57 */     int targetId = 0;
/*  58 */     if (env.getVisibleObject() instanceof Npc)
/*  59 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  60 */     if (targetId != 203557)
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
/*  78 */         int[] ids = { 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022 };
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
/*  92 */     if (zoneName != ZoneName.ALTGARD_FORTRESS_220030000)
/*  93 */       return false; 
/*  94 */     Player player = env.getPlayer();
/*  95 */     QuestState qs = player.getQuestStateList().getQuestState(2200);
/*  96 */     if (qs != null)
/*  97 */       return false; 
/*  98 */     env.setQuestId(Integer.valueOf(2200));
/*  99 */     QuestService.startQuest(env, QuestStatus.START);
/* 100 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2200AltgardDuties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */