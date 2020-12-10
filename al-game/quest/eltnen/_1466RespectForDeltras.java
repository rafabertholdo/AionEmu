/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.ZoneService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ 
/*     */ 
/*     */ public class _1466RespectForDeltras
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1466;
/*     */   
/*     */   public _1466RespectForDeltras() {
/*  46 */     super(Integer.valueOf(1466));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.setQuestItemIds(182201385).add(1466);
/*  53 */     this.qe.setNpcQuestData(212649).addOnQuestStart(1466);
/*  54 */     this.qe.setNpcQuestData(212649).addOnTalkEvent(1466);
/*  55 */     this.qe.setNpcQuestData(203903).addOnTalkEvent(1466);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/*  61 */     final Player player = env.getPlayer();
/*  62 */     final int id = item.getItemTemplate().getTemplateId();
/*  63 */     final int itemObjId = item.getObjectId();
/*     */     
/*  65 */     if (id != 182201385)
/*  66 */       return false; 
/*  67 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.Q1466))
/*  68 */       return false; 
/*  69 */     final QuestState qs = player.getQuestStateList().getQuestState(1466);
/*  70 */     if (qs == null)
/*  71 */       return false; 
/*  72 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*  73 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  77 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*  78 */             ItemService.removeItemFromInventory(player, item);
/*  79 */             qs.setStatus(QuestStatus.REWARD);
/*  80 */             _1466RespectForDeltras.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  89 */     Player player = env.getPlayer();
/*  90 */     int targetId = 0;
/*  91 */     if (env.getVisibleObject() instanceof Npc)
/*  92 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  93 */     QuestState qs = player.getQuestStateList().getQuestState(1466);
/*  94 */     if (targetId == 212649) {
/*     */       
/*  96 */       if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */       {
/*  98 */         if (env.getDialogId().intValue() == 25)
/*  99 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762); 
/* 100 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/* 102 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182201385, 1)))) {
/* 103 */             return defaultQuestStartDialog(env);
/*     */           }
/* 105 */           return true;
/*     */         } 
/*     */         
/* 108 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 112 */     else if (targetId == 203903) {
/*     */       
/* 114 */       if (qs != null) {
/*     */         
/* 116 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 117 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 118 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 120 */           qs.setQuestVar(2);
/* 121 */           qs.setStatus(QuestStatus.REWARD);
/* 122 */           updateQuestStatus(player, qs);
/* 123 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 126 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1466RespectForDeltras.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */