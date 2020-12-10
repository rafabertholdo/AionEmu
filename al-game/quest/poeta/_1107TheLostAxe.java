/*     */ package quest.poeta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class _1107TheLostAxe
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1107;
/*     */   
/*     */   public _1107TheLostAxe() {
/*  42 */     super(Integer.valueOf(1107));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(203075).addOnTalkEvent(1107);
/*  49 */     this.qe.setQuestItemIds(182200501).add(1107);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  55 */     Player player = env.getPlayer();
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1107);
/*     */     
/*  58 */     int targetId = 0;
/*  59 */     if (env.getVisibleObject() instanceof Npc)
/*  60 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  61 */     if (targetId == 0) {
/*     */       
/*  63 */       if (env.getDialogId().intValue() == 1002) {
/*     */         
/*  65 */         QuestService.startQuest(env, QuestStatus.START);
/*  66 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  67 */         return true;
/*     */       } 
/*     */       
/*  70 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*     */     }
/*  72 */     else if (targetId == 203075) {
/*     */       
/*  74 */       if (qs != null) {
/*     */         
/*  76 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*     */         {
/*  78 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/*  80 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  82 */           ItemService.removeItemFromInventoryByItemId(player, 182200501);
/*  83 */           qs.setQuestVar(1);
/*  84 */           qs.setStatus(QuestStatus.REWARD);
/*  85 */           updateQuestStatus(player, qs);
/*  86 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  89 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/*  98 */     Player player = env.getPlayer();
/*  99 */     int id = item.getItemTemplate().getTemplateId();
/* 100 */     int itemObjId = item.getObjectId();
/* 101 */     QuestState qs = player.getQuestStateList().getQuestState(1107);
/*     */     
/* 103 */     if (id != 182200501)
/* 104 */       return false; 
/* 105 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
/* 106 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/* 107 */       sendQuestDialog(player, 0, 4); 
/* 108 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1107TheLostAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */