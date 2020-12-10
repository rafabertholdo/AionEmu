/*    */ package quest.ishalgen;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ import com.aionemu.gameserver.services.QuestService;
/*    */ import com.aionemu.gameserver.world.zone.ZoneName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class _2100OrderoftheCaptain
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 2100;
/*    */   
/*    */   public _2100OrderoftheCaptain() {
/* 38 */     super(Integer.valueOf(2100));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 44 */     this.qe.setNpcQuestData(203516).addOnTalkEvent(2100);
/* 45 */     this.qe.setQuestEnterZone(ZoneName.ALDELLE_VILLAGE).add(2100);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onDialogEvent(QuestEnv env) {
/* 51 */     Player player = env.getPlayer();
/* 52 */     QuestState qs = player.getQuestStateList().getQuestState(2100);
/* 53 */     if (qs == null) {
/* 54 */       return false;
/*    */     }
/* 56 */     int targetId = 0;
/* 57 */     if (env.getVisibleObject() instanceof Npc)
/* 58 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 59 */     if (targetId != 203516)
/* 60 */       return false; 
/* 61 */     if (qs.getStatus() == QuestStatus.START) {
/*    */       
/* 63 */       if (env.getDialogId().intValue() == 25) {
/*    */         
/* 65 */         qs.setQuestVar(1);
/* 66 */         qs.setStatus(QuestStatus.REWARD);
/* 67 */         updateQuestStatus(player, qs);
/* 68 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*    */       } 
/*    */       
/* 71 */       return defaultQuestStartDialog(env);
/*    */     } 
/* 73 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*    */       
/* 75 */       if (env.getDialogId().intValue() == 17) {
/*    */         
/* 77 */         int[] ids = { 2001, 2002, 2003, 2004, 2005, 2006, 2007 };
/* 78 */         for (int id : ids)
/* 79 */           QuestService.startQuest(new QuestEnv(env.getVisibleObject(), env.getPlayer(), Integer.valueOf(id), env.getDialogId()), QuestStatus.LOCKED); 
/*    */       } 
/* 81 */       return defaultQuestEndDialog(env);
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 89 */     if (zoneName != ZoneName.ALDELLE_VILLAGE)
/* 90 */       return false; 
/* 91 */     Player player = env.getPlayer();
/* 92 */     QuestState qs = player.getQuestStateList().getQuestState(2100);
/* 93 */     if (qs != null)
/* 94 */       return false; 
/* 95 */     env.setQuestId(Integer.valueOf(2100));
/* 96 */     QuestService.startQuest(env, QuestStatus.START);
/* 97 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2100OrderoftheCaptain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */