/*     */ package quest.eltnen;
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
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ public class _1355TheFireTempleKey
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1355;
/*     */   
/*     */   public _1355TheFireTempleKey() {
/*  43 */     super(Integer.valueOf(1355));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(203933).addOnTalkEvent(1355);
/*  50 */     this.qe.setQuestItemIds(182201400).add(1355);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1355);
/*     */     
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc)
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  62 */     if (targetId == 0) {
/*     */       
/*  64 */       if (env.getDialogId().intValue() == 1002)
/*     */       {
/*  66 */         QuestService.startQuest(env, QuestStatus.START);
/*  67 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  68 */         return true;
/*     */       }
/*     */     
/*  71 */     } else if (targetId == 203933) {
/*     */       
/*  73 */       if (qs != null) {
/*     */         
/*  75 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*     */         {
/*  77 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/*  79 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  81 */           ItemService.removeItemFromInventoryByItemId(player, 182201400);
/*  82 */           qs.setQuestVar(1);
/*  83 */           qs.setStatus(QuestStatus.REWARD);
/*  84 */           updateQuestStatus(player, qs);
/*  85 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  88 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/*  97 */     final Player player = env.getPlayer();
/*  98 */     final int id = item.getItemTemplate().getTemplateId();
/*  99 */     final int itemObjId = item.getObjectId();
/*     */     
/* 101 */     if (id != 182201400)
/* 102 */       return false; 
/* 103 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 105 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 109 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 111 */             _1355TheFireTempleKey.this.sendQuestDialog(player, 0, 4);
/*     */           }
/*     */         }3000L);
/* 114 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1355TheFireTempleKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */