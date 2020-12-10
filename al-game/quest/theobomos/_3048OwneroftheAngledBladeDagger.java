/*     */ package quest.theobomos;
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
/*     */ public class _3048OwneroftheAngledBladeDagger
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3048;
/*     */   
/*     */   public _3048OwneroftheAngledBladeDagger() {
/*  27 */     super(Integer.valueOf(3048));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  33 */     this.qe.setNpcQuestData(798208).addOnTalkEvent(3048);
/*  34 */     this.qe.setNpcQuestData(798206).addOnTalkEvent(3048);
/*  35 */     this.qe.setQuestItemIds(182208033).add(3048);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  41 */     Player player = env.getPlayer();
/*  42 */     QuestState qs = player.getQuestStateList().getQuestState(3048);
/*     */     
/*  44 */     int targetId = 0;
/*  45 */     if (env.getVisibleObject() instanceof Npc)
/*  46 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  47 */     if (targetId == 0) {
/*     */       
/*  49 */       if (env.getDialogId().intValue() == 1002)
/*     */       {
/*  51 */         QuestService.startQuest(env, QuestStatus.START);
/*  52 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  53 */         return true;
/*     */       }
/*     */     
/*  56 */     } else if (targetId == 798208) {
/*     */       
/*  58 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  60 */         if (env.getDialogId().intValue() == 25)
/*  61 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  62 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  64 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  65 */           updateQuestStatus(player, qs);
/*  66 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  67 */           return true;
/*     */         } 
/*     */         
/*  70 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  73 */     } else if (targetId == 798206) {
/*     */       
/*  75 */       if (qs != null) {
/*     */         
/*  77 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*     */         {
/*  79 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/*  81 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  83 */           ItemService.removeItemFromInventoryByItemId(player, 182208033);
/*  84 */           qs.setQuestVar(1);
/*  85 */           qs.setStatus(QuestStatus.REWARD);
/*  86 */           updateQuestStatus(player, qs);
/*  87 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  90 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/*  99 */     final Player player = env.getPlayer();
/* 100 */     final int id = item.getItemTemplate().getTemplateId();
/* 101 */     final int itemObjId = item.getObjectId();
/*     */     
/* 103 */     if (id != 182208033)
/* 104 */       return false; 
/* 105 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 107 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 111 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 113 */             _3048OwneroftheAngledBladeDagger.this.sendQuestDialog(player, 0, 4);
/*     */           }
/*     */         }3000L);
/* 116 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3048OwneroftheAngledBladeDagger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */