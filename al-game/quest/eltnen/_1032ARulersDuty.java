/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class _1032ARulersDuty
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1032;
/*     */   
/*     */   public _1032ARulersDuty() {
/*  53 */     super(Integer.valueOf(1032));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  59 */     this.qe.setQuestItemIds(182201001).add(1032);
/*  60 */     this.qe.setNpcQuestData(203932).addOnTalkEvent(1032);
/*  61 */     this.qe.setNpcQuestData(730020).addOnTalkEvent(1032);
/*  62 */     this.qe.setNpcQuestData(730019).addOnTalkEvent(1032);
/*  63 */     this.qe.setNpcQuestData(700157).addOnTalkEvent(1032);
/*  64 */     this.qe.addQuestLvlUp(1032);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/*  70 */     final Player player = env.getPlayer();
/*  71 */     final int id = item.getItemTemplate().getTemplateId();
/*  72 */     final int itemObjId = item.getObjectId();
/*     */     
/*  74 */     if (id != 182201001)
/*  75 */       return false; 
/*  76 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.PUTRID_MIRE))
/*  77 */       return false; 
/*  78 */     final QuestState qs = player.getQuestStateList().getQuestState(1032);
/*  79 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*  80 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  84 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*  85 */             qs.setQuestVar(4);
/*  86 */             _1032ARulersDuty.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/*     */     
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  97 */     Player player = env.getPlayer();
/*  98 */     QuestState qs = player.getQuestStateList().getQuestState(1032);
/*  99 */     boolean lvlCheck = QuestService.checkLevelRequirement(1032, player.getCommonData().getLevel());
/* 100 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/* 101 */       return false; 
/* 102 */     qs.setStatus(QuestStatus.START);
/* 103 */     updateQuestStatus(player, qs);
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/* 110 */     final Player player = env.getPlayer();
/* 111 */     int targetId = 0;
/* 112 */     if (env.getVisibleObject() instanceof Npc)
/* 113 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 114 */     QuestState qs = player.getQuestStateList().getQuestState(1032);
/* 115 */     if (qs == null)
/* 116 */       return false; 
/* 117 */     if (targetId == 203932) {
/*     */       
/* 119 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/* 121 */         if (env.getDialogId().intValue() == 25)
/* 122 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 123 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 125 */           qs.setQuestVar(1);
/* 126 */           updateQuestStatus(player, qs);
/* 127 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 129 */           return true;
/*     */         } 
/*     */         
/* 132 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 135 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 137 */         if (env.getDialogId().intValue() == -1)
/* 138 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 139 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/* 142 */     } else if (targetId == 730020) {
/*     */       
/* 144 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
/*     */         
/* 146 */         if (env.getDialogId().intValue() == 25)
/* 147 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 148 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 150 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 151 */           updateQuestStatus(player, qs);
/* 152 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 154 */           return true;
/*     */         } 
/*     */         
/* 157 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 160 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5)
/*     */       {
/* 162 */         if (env.getDialogId().intValue() == 25)
/* 163 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 164 */         if (env.getDialogId().intValue() == 10004) {
/*     */           
/* 166 */           qs.setStatus(QuestStatus.REWARD);
/* 167 */           updateQuestStatus(player, qs);
/* 168 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 170 */           return true;
/*     */         } 
/*     */         
/* 173 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 177 */     else if (targetId == 730019) {
/*     */       
/* 179 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
/*     */         
/* 181 */         if (env.getDialogId().intValue() == 25)
/* 182 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 183 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 185 */           ItemService.decreaseItemCountByItemId(player, 182201001, 1L);
/* 186 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 187 */           updateQuestStatus(player, qs);
/* 188 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 49));
/* 189 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 191 */           return true;
/*     */         } 
/*     */         
/* 194 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 197 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4)
/*     */       {
/* 199 */         if (env.getDialogId().intValue() == 25)
/* 200 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 201 */         if (env.getDialogId().intValue() == 10003) {
/*     */           
/* 203 */           qs.setQuestVar(5);
/* 204 */           updateQuestStatus(player, qs);
/* 205 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 207 */           return true;
/*     */         } 
/*     */         
/* 210 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 214 */     else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
/*     */       
/* 216 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 700157:
/* 220 */           if (qs.getQuestVarById(0) == 3 && env.getDialogId().intValue() == -1) {
/*     */             
/* 222 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201001, 1)));
/* 223 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 224 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 226 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 228 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 232 */                     if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
/*     */                       return; 
/* 234 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 236 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 246 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1032ARulersDuty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */