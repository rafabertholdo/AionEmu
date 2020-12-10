/*     */ package quest.eltnen;
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
/*     */ public class _1039SomethingInTheWater
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1039;
/*  46 */   private static final int[] mob_ids = new int[] { 210946, 210947 };
/*     */ 
/*     */   
/*     */   public _1039SomethingInTheWater() {
/*  50 */     super(Integer.valueOf(1039));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  56 */     this.qe.setQuestItemIds(182201009).add(1039);
/*  57 */     this.qe.setNpcQuestData(203946).addOnTalkEvent(1039);
/*  58 */     this.qe.setNpcQuestData(203705).addOnTalkEvent(1039);
/*  59 */     this.qe.addQuestLvlUp(1039);
/*  60 */     for (int mob_id : mob_ids) {
/*  61 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1039);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  67 */     Player player = env.getPlayer();
/*  68 */     QuestState qs = player.getQuestStateList().getQuestState(1039);
/*  69 */     boolean lvlCheck = QuestService.checkLevelRequirement(1039, player.getCommonData().getLevel());
/*  70 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  71 */       return false; 
/*  72 */     qs.setStatus(QuestStatus.START);
/*  73 */     updateQuestStatus(player, qs);
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/*  80 */     final Player player = env.getPlayer();
/*  81 */     final int id = item.getItemTemplate().getTemplateId();
/*  82 */     final int itemObjId = item.getObjectId();
/*     */     
/*  84 */     if (id != 182201009)
/*  85 */       return false; 
/*  86 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.MYSTIC_SPRING_OF_AGAIRON))
/*  87 */       return false; 
/*  88 */     final QuestState qs = player.getQuestStateList().getQuestState(1039);
/*  89 */     if (qs == null)
/*  90 */       return false; 
/*  91 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*  92 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  96 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*  97 */             ItemService.removeItemFromInventory(player, item);
/*  98 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201010, 1)));
/*  99 */             qs.setQuestVar(2);
/* 100 */             _1039SomethingInTheWater.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 109 */     Player player = env.getPlayer();
/* 110 */     QuestState qs = player.getQuestStateList().getQuestState(1039);
/* 111 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 112 */       return false;
/*     */     }
/* 114 */     int var = qs.getQuestVarById(0);
/* 115 */     int targetId = 0;
/* 116 */     if (env.getVisibleObject() instanceof Npc)
/* 117 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 118 */     switch (targetId) {
/*     */       
/*     */       case 210946:
/* 121 */         if (qs.getQuestVarById(1) == 2 && qs.getQuestVarById(2) == 3 && var == 4) {
/*     */           
/* 123 */           qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
/* 124 */           qs.setStatus(QuestStatus.REWARD);
/* 125 */           updateQuestStatus(player, qs);
/* 126 */           return true;
/*     */         } 
/* 128 */         if (qs.getQuestVarById(1) <= 2 && var == 4) {
/*     */           
/* 130 */           qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
/* 131 */           updateQuestStatus(player, qs);
/* 132 */           return true;
/*     */         } 
/*     */         break;
/*     */     } 
/* 136 */     switch (targetId) {
/*     */       
/*     */       case 210947:
/* 139 */         if (qs.getQuestVarById(1) == 3 && qs.getQuestVarById(2) == 2 && var == 4) {
/*     */           
/* 141 */           qs.setQuestVarById(2, qs.getQuestVarById(2) + 1);
/* 142 */           qs.setStatus(QuestStatus.REWARD);
/* 143 */           updateQuestStatus(player, qs);
/* 144 */           return true;
/*     */         } 
/* 146 */         if (qs.getQuestVarById(2) <= 2 && var == 4) {
/*     */           
/* 148 */           qs.setQuestVarById(2, qs.getQuestVarById(2) + 1);
/* 149 */           updateQuestStatus(player, qs);
/* 150 */           return true;
/*     */         }  break;
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/* 159 */     Player player = env.getPlayer();
/* 160 */     int targetId = 0;
/* 161 */     if (env.getVisibleObject() instanceof Npc)
/* 162 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 163 */     QuestState qs = player.getQuestStateList().getQuestState(1039);
/* 164 */     if (qs == null) {
/* 165 */       return false;
/*     */     }
/* 167 */     if (targetId == 203946) {
/*     */       
/* 169 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/* 171 */         if (env.getDialogId().intValue() == 25)
/* 172 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 173 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 175 */           qs.setQuestVar(1);
/* 176 */           ItemService.addItems(player, Collections.singletonList(new QuestItems(182201009, 1)));
/* 177 */           updateQuestStatus(player, qs);
/* 178 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 180 */           return true;
/*     */         } 
/*     */         
/* 183 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 186 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
/*     */         
/* 188 */         if (env.getDialogId().intValue() == 25)
/* 189 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 190 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 192 */           qs.setQuestVar(4);
/* 193 */           updateQuestStatus(player, qs);
/* 194 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 196 */           return true;
/*     */         } 
/*     */         
/* 199 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 202 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 204 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/* 207 */     else if (targetId == 203705) {
/*     */       
/* 209 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
/*     */         
/* 211 */         if (env.getDialogId().intValue() == 25)
/* 212 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 213 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 215 */           qs.setQuestVar(3);
/* 216 */           updateQuestStatus(player, qs);
/* 217 */           ItemService.decreaseItemCountByItemId(player, 182201010, 1L);
/* 218 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 219 */           return true;
/*     */         } 
/*     */         
/* 222 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1039SomethingInTheWater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */