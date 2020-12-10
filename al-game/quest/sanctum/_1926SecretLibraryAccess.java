/*     */ package quest.sanctum;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.WorldMapType;
/*     */ import java.util.Collections;
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
/*     */ public class _1926SecretLibraryAccess
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1926;
/*  41 */   private static final int[] npc_ids = new int[] { 203894, 203098 };
/*     */ 
/*     */   
/*     */   public _1926SecretLibraryAccess() {
/*  45 */     super(Integer.valueOf(1926));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  51 */     this.qe.setNpcQuestData(203894).addOnQuestStart(1926);
/*  52 */     for (int npc_id : npc_ids) {
/*  53 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1926);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean AreVerteronQuestsFinished(Player player) {
/*  59 */     QuestState qs = player.getQuestStateList().getQuestState(1020);
/*  60 */     return !(qs == null || qs.getStatus() != QuestStatus.COMPLETE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  66 */     final Player player = env.getPlayer();
/*  67 */     int targetId = 0;
/*  68 */     if (env.getVisibleObject() instanceof Npc)
/*  69 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  70 */     QuestState qs = player.getQuestStateList().getQuestState(1926);
/*     */     
/*  72 */     if (targetId == 203894) {
/*     */       
/*  74 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*  75 */         if (env.getDialogId().intValue() == 25)
/*  76 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762); 
/*  77 */         return defaultQuestStartDialog(env);
/*  78 */       }  if ((qs.getStatus() == QuestStatus.REWARD && qs.getQuestVarById(0) == 0) || qs.getStatus() == QuestStatus.COMPLETE) {
/*     */         
/*  80 */         if (env.getDialogId().intValue() == -1 && qs.getStatus() == QuestStatus.REWARD)
/*  81 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  82 */         if (env.getDialogId().intValue() == 17) {
/*  83 */           ItemService.removeItemFromInventoryByItemId(player, 182206022);
/*  84 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  85 */           updateQuestStatus(player, qs);
/*  86 */           return defaultQuestEndDialog(env);
/*  87 */         }  if (env.getDialogId().intValue() == 1009) {
/*  88 */           return defaultQuestEndDialog(env);
/*     */         }
/*  90 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               
/*     */               public void run()
/*     */               {
/*  95 */                 TeleportService.teleportTo(player, WorldMapType.SANCTUM.getId(), 2032.9F, 1473.1F, 592.2F, 195);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*  99 */     } else if (targetId == 203098) {
/*     */       
/* 101 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/* 103 */         if (env.getDialogId().intValue() == 25) {
/* 104 */           if (AreVerteronQuestsFinished(player))
/* 105 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 106 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1097);
/*     */         } 
/* 108 */         if (env.getDialogId().intValue() == 10255) {
/* 109 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206022, 1)))) {
/* 110 */             qs.setStatus(QuestStatus.REWARD);
/* 111 */             updateQuestStatus(player, qs);
/*     */           } 
/* 113 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 114 */           return true;
/*     */         } 
/* 116 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_1926SecretLibraryAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */