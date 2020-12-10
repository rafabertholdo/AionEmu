/*    */ package quest.sanctum;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ import com.aionemu.gameserver.services.ItemService;
/*    */ import com.aionemu.gameserver.services.QuestService;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class _1968CombatRations
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 1968;
/*    */   
/*    */   public _1968CombatRations() {
/* 27 */     super(Integer.valueOf(1968));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 33 */     this.qe.setNpcQuestData(278556).addOnTalkEvent(1968);
/* 34 */     this.qe.setQuestItemIds(182206037).add(1968);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onDialogEvent(QuestEnv env) {
/* 40 */     Player player = env.getPlayer();
/* 41 */     QuestState qs = player.getQuestStateList().getQuestState(1968);
/*    */     
/* 43 */     int targetId = 0;
/* 44 */     if (env.getVisibleObject() instanceof Npc)
/* 45 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 46 */     if (targetId == 0) {
/*    */       
/* 48 */       if (env.getDialogId().intValue() == 1002)
/*    */       {
/* 50 */         QuestService.startQuest(env, QuestStatus.START);
/* 51 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/* 52 */         return true;
/*    */       }
/*    */     
/* 55 */     } else if (targetId == 278556) {
/*    */       
/* 57 */       if (qs != null) {
/*    */         
/* 59 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*    */         {
/* 61 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*    */         }
/* 63 */         if (env.getDialogId().intValue() == 1009) {
/*    */           
/* 65 */           ItemService.removeItemFromInventoryByItemId(player, 182206037);
/* 66 */           qs.setQuestVar(1);
/* 67 */           qs.setStatus(QuestStatus.REWARD);
/* 68 */           updateQuestStatus(player, qs);
/* 69 */           return defaultQuestEndDialog(env);
/*    */         } 
/*    */         
/* 72 */         return defaultQuestEndDialog(env);
/*    */       } 
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 81 */     final Player player = env.getPlayer();
/* 82 */     final int id = item.getItemTemplate().getTemplateId();
/* 83 */     final int itemObjId = item.getObjectId();
/*    */     
/* 85 */     if (id != 182206037)
/* 86 */       return false; 
/* 87 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*    */     
/* 89 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 93 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*    */             
/* 95 */             _1968CombatRations.this.sendQuestDialog(player, 0, 4);
/*    */           }
/*    */         }3000L);
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_1968CombatRations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */