/*    */ package quest.poeta;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ public class _1123WheresTutty
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 1123;
/*    */   
/*    */   public _1123WheresTutty() {
/* 39 */     super(Integer.valueOf(1123));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 45 */     this.qe.setNpcQuestData(790001).addOnTalkEvent(1123);
/* 46 */     this.qe.setNpcQuestData(790001).addOnQuestStart(1123);
/* 47 */     this.qe.setQuestEnterZone(ZoneName.Q1123).add(1123);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onDialogEvent(QuestEnv env) {
/* 53 */     Player player = env.getPlayer();
/* 54 */     int targetId = 0;
/* 55 */     if (env.getVisibleObject() instanceof Npc)
/* 56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 57 */     QuestState qs = player.getQuestStateList().getQuestState(1123);
/* 58 */     if (targetId == 790001) {
/*    */       
/* 60 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*    */         
/* 62 */         if (env.getDialogId().intValue() == 25) {
/* 63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*    */         }
/* 65 */         return defaultQuestStartDialog(env);
/*    */       } 
/* 67 */       if (qs.getStatus() == QuestStatus.REWARD) {
/*    */         
/* 69 */         if (env.getDialogId().intValue() == -1)
/* 70 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 71 */         return defaultQuestEndDialog(env);
/*    */       } 
/*    */     } 
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 80 */     if (zoneName != ZoneName.Q1123)
/* 81 */       return false; 
/* 82 */     Player player = env.getPlayer();
/* 83 */     QuestState qs = player.getQuestStateList().getQuestState(1123);
/* 84 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/* 85 */       return false; 
/* 86 */     env.setQuestId(Integer.valueOf(1123));
/* 87 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 11));
/* 88 */     qs.setStatus(QuestStatus.REWARD);
/* 89 */     updateQuestStatus(player, qs);
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1123WheresTutty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */