/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ 
/*     */ public class _2208MauInTenMinutesADay
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2208;
/*     */   
/*     */   public _2208MauInTenMinutesADay() {
/*  46 */     super(Integer.valueOf(2208));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.setNpcQuestData(203591).addOnQuestStart(2208);
/*  53 */     this.qe.setNpcQuestData(203591).addOnTalkEvent(2208);
/*  54 */     this.qe.setNpcQuestData(203589).addOnTalkEvent(2208);
/*  55 */     this.qe.setQuestItemIds(182203205).add(2208);
/*  56 */     this.deletebleItems = new int[] { 182203205 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  62 */     Player player = env.getPlayer();
/*  63 */     int targetId = 0;
/*  64 */     if (env.getVisibleObject() instanceof Npc)
/*  65 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  66 */     QuestState qs = player.getQuestStateList().getQuestState(2208);
/*  67 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  69 */       if (targetId == 203591)
/*     */       {
/*  71 */         if (env.getDialogId().intValue() == 25)
/*  72 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  73 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  75 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203205, 1))))
/*  76 */             return defaultQuestStartDialog(env); 
/*  77 */           return true;
/*     */         } 
/*     */         
/*  80 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  83 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  85 */       if (targetId == 203589) {
/*     */         
/*  87 */         int var = qs.getQuestVarById(0);
/*  88 */         if (env.getDialogId().intValue() == 25) {
/*     */           
/*  90 */           if (var == 0)
/*  91 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  92 */           if (var == 1) {
/*  93 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */           }
/*  95 */         } else if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  97 */           qs.setStatus(QuestStatus.REWARD);
/*  98 */           updateQuestStatus(player, qs);
/*  99 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 100 */           return true;
/*     */         }
/*     */       
/*     */       } 
/* 104 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 106 */       if (targetId == 203591)
/* 107 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/* 115 */     final Player player = env.getPlayer();
/* 116 */     final int id = item.getItemTemplate().getTemplateId();
/* 117 */     final int itemObjId = item.getObjectId();
/*     */     
/* 119 */     if (id != 182203205)
/* 120 */       return false; 
/* 121 */     final QuestState qs = player.getQuestStateList().getQuestState(2208);
/* 122 */     if (qs == null)
/* 123 */       return false; 
/* 124 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/* 125 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 129 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 130 */             ItemService.removeItemFromInventory(player, item);
/* 131 */             qs.setQuestVarById(0, 1);
/* 132 */             _2208MauInTenMinutesADay.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 135 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2208MauInTenMinutesADay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */