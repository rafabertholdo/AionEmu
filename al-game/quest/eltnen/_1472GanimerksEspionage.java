/*    */ package quest.eltnen;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
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
/*    */ public class _1472GanimerksEspionage
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 1472;
/*    */   
/*    */   public _1472GanimerksEspionage() {
/* 38 */     super(Integer.valueOf(1472));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 44 */     this.qe.setNpcQuestData(203903).addOnQuestStart(1472);
/* 45 */     this.qe.setNpcQuestData(203903).addOnTalkEvent(1472);
/* 46 */     this.qe.setNpcQuestData(798114).addOnTalkEvent(1472);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onDialogEvent(QuestEnv env) {
/* 52 */     Player player = env.getPlayer();
/* 53 */     int targetId = 0;
/* 54 */     if (env.getVisibleObject() instanceof Npc)
/* 55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 56 */     QuestState qs = player.getQuestStateList().getQuestState(1472);
/* 57 */     if (targetId == 203903) {
/*    */       
/* 59 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*    */         
/* 61 */         if (env.getDialogId().intValue() == 25) {
/* 62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*    */         }
/* 64 */         return defaultQuestStartDialog(env);
/*    */       } 
/*    */       
/* 67 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*    */       {
/* 69 */         return defaultQuestEndDialog(env);
/*    */       }
/*    */     }
/* 72 */     else if (targetId == 798114) {
/*    */       
/* 74 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*    */         
/* 76 */         if (env.getDialogId().intValue() == 25)
/* 77 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 78 */         if (env.getDialogId().intValue() == 10000) {
/*    */           
/* 80 */           qs.setStatus(QuestStatus.REWARD);
/* 81 */           updateQuestStatus(player, qs);
/* 82 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*    */           
/* 84 */           return true;
/*    */         } 
/*    */         
/* 87 */         return defaultQuestStartDialog(env);
/*    */       } 
/*    */     } 
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1472GanimerksEspionage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */