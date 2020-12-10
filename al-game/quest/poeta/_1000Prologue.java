/*    */ package quest.poeta;
/*    */ 
/*    */ import com.aionemu.gameserver.model.Race;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ import com.aionemu.gameserver.services.QuestService;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ public class _1000Prologue
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 1000;
/*    */   
/*    */   public _1000Prologue() {
/* 39 */     super(Integer.valueOf(1000));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 45 */     this.qe.addOnEnterWorld(1000);
/* 46 */     this.qe.setQuestMovieEndIds(1).add(1000);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onEnterWorldEvent(QuestEnv env) {
/* 52 */     Player player = env.getPlayer();
/* 53 */     if (player.getCommonData().getRace() != Race.ELYOS)
/* 54 */       return false; 
/* 55 */     QuestState qs = player.getQuestStateList().getQuestState(1000);
/* 56 */     if (qs == null) {
/*    */       
/* 58 */       env.setQuestId(Integer.valueOf(1000));
/* 59 */       QuestService.startQuest(env, QuestStatus.START);
/*    */     } 
/* 61 */     qs = player.getQuestStateList().getQuestState(1000);
/* 62 */     if (qs.getStatus() == QuestStatus.START) {
/*    */       
/* 64 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(1, 1));
/* 65 */       return true;
/*    */     } 
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onMovieEndEvent(QuestEnv env, int movieId) {
/* 73 */     if (movieId != 1)
/* 74 */       return false; 
/* 75 */     Player player = env.getPlayer();
/* 76 */     if (player.getCommonData().getRace() != Race.ELYOS)
/* 77 */       return false; 
/* 78 */     QuestState qs = player.getQuestStateList().getQuestState(1000);
/* 79 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/* 80 */       return false; 
/* 81 */     qs.setStatus(QuestStatus.REWARD);
/* 82 */     QuestService.questFinish(env);
/* 83 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1000Prologue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */