/*     */ package quest.beluslan;
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
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class _2053AMissingFather
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2053;
/*  48 */   private static final int[] npc_ids = new int[] { 204707, 204749, 204800, 700359, 730108 };
/*     */ 
/*     */   
/*     */   public _2053AMissingFather() {
/*  52 */     super(Integer.valueOf(2053));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  58 */     this.qe.setQuestEnterZone(ZoneName.MALEK_MINE_220040000).add(2053);
/*  59 */     this.qe.addQuestLvlUp(2053);
/*  60 */     this.qe.setQuestItemIds(182204305).add(2053);
/*  61 */     for (int npc_id : npc_ids)
/*  62 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2053); 
/*  63 */     this.deletebleItems = new int[] { 182204307, 182204305 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  69 */     Player player = env.getPlayer();
/*  70 */     QuestState qs = player.getQuestStateList().getQuestState(2053);
/*  71 */     boolean lvlCheck = QuestService.checkLevelRequirement(2053, player.getCommonData().getLevel());
/*  72 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  73 */       return false;
/*     */     }
/*  75 */     QuestState qs2 = player.getQuestStateList().getQuestState(2500);
/*  76 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  77 */       return false; 
/*  78 */     qs.setStatus(QuestStatus.START);
/*  79 */     updateQuestStatus(player, qs);
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  86 */     final Player player = env.getPlayer();
/*  87 */     final QuestState qs = player.getQuestStateList().getQuestState(2053);
/*  88 */     if (qs == null) {
/*  89 */       return false;
/*     */     }
/*  91 */     int var = qs.getQuestVarById(0);
/*  92 */     int targetId = 0;
/*  93 */     if (env.getVisibleObject() instanceof Npc) {
/*  94 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  96 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  98 */       if (targetId == 204707) {
/*     */         
/* 100 */         if (env.getDialogId().intValue() == -1)
/* 101 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 102 */         if (env.getDialogId().intValue() == 1009)
/* 103 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/* 104 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 106 */       return false;
/*     */     } 
/* 108 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 110 */       return false;
/*     */     }
/* 112 */     if (targetId == 204707) {
/*     */       
/* 114 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 117 */           if (var == 0)
/* 118 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 119 */           if (var == 5)
/* 120 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 10000:
/* 122 */           if (var == 0) {
/*     */             
/* 124 */             qs.setQuestVarById(0, var + 1);
/* 125 */             updateQuestStatus(player, qs);
/* 126 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 127 */             return true;
/*     */           } 
/*     */         case 10005:
/* 130 */           if (var == 5) {
/*     */             
/* 132 */             qs.setQuestVarById(0, var + 1);
/* 133 */             updateQuestStatus(player, qs);
/* 134 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 135 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 139 */     } else if (targetId == 204749) {
/*     */       
/* 141 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 144 */           if (var == 1)
/* 145 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10001:
/* 147 */           if (var == 1) {
/*     */             
/* 149 */             qs.setQuestVarById(0, var + 1);
/* 150 */             updateQuestStatus(player, qs);
/* 151 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204305, 1)));
/* 152 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 153 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 157 */     } else if (targetId == 730108) {
/*     */       
/* 159 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 162 */           if (var == 4)
/* 163 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 10004:
/* 165 */           if (var == 4) {
/*     */             
/* 167 */             qs.setQuestVarById(0, 5);
/* 168 */             updateQuestStatus(player, qs);
/* 169 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 170 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 174 */     } else if (targetId == 204800) {
/*     */       
/* 176 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 179 */           if (var == 6)
/* 180 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 10006:
/* 182 */           if (var == 6) {
/*     */             
/* 184 */             qs.setQuestVarById(0, var + 1);
/* 185 */             updateQuestStatus(player, qs);
/* 186 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 187 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 191 */     } else if (targetId == 700359 && var == 7) {
/*     */       
/* 193 */       if (env.getDialogId().intValue() == -1 && player.getInventory().getItemCountByItemId(182204307) == 0L) {
/*     */         
/* 195 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 196 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 197 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 198 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 202 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 203 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 204 */                 ItemService.removeItemFromInventoryByItemId(player, 182204307);
/* 205 */                 qs.setStatus(QuestStatus.REWARD);
/* 206 */                 _2053AMissingFather.this.updateQuestStatus(player, qs);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 217 */     if (zoneName != ZoneName.MALEK_MINE_220040000)
/* 218 */       return false; 
/* 219 */     Player player = env.getPlayer();
/* 220 */     QuestState qs = player.getQuestStateList().getQuestState(2053);
/* 221 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 3)
/* 222 */       return false; 
/* 223 */     qs.setQuestVarById(0, 4);
/* 224 */     updateQuestStatus(player, qs);
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 231 */     final Player player = env.getPlayer();
/* 232 */     final int id = item.getItemTemplate().getTemplateId();
/* 233 */     final int itemObjId = item.getObjectId();
/*     */     
/* 235 */     if (id != 182204305) {
/* 236 */       return false;
/*     */     }
/* 238 */     final QuestState qs = player.getQuestStateList().getQuestState(2053);
/* 239 */     if (qs == null || qs.getQuestVarById(0) != 2)
/* 240 */       return false; 
/* 241 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 1000, 0, 0), true);
/* 242 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 246 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 247 */             ItemService.removeItemFromInventoryByItemId(player, 182204305);
/* 248 */             qs.setQuestVarById(0, 3);
/* 249 */             _2053AMissingFather.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }1000L);
/* 252 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2053AMissingFather.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */