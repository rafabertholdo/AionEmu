/*     */ package quest.beluslan;
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
/*     */ public class _2052AnUndeadOccupation
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2052;
/*  46 */   private static final int[] npc_ids = new int[] { 204715, 204801, 204805 };
/*     */ 
/*     */   
/*     */   public _2052AnUndeadOccupation() {
/*  50 */     super(Integer.valueOf(2052));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  56 */     this.qe.addQuestLvlUp(2052);
/*  57 */     this.qe.setQuestItemIds(182204304).add(2052);
/*  58 */     this.qe.setNpcQuestData(213044).addOnKillEvent(2052);
/*  59 */     for (int npc_id : npc_ids)
/*  60 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2052); 
/*  61 */     this.deletebleItems = new int[] { 182204304 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  67 */     Player player = env.getPlayer();
/*  68 */     QuestState qs = player.getQuestStateList().getQuestState(2052);
/*  69 */     boolean lvlCheck = QuestService.checkLevelRequirement(2052, player.getCommonData().getLevel());
/*  70 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  71 */       return false;
/*     */     }
/*  73 */     QuestState qs2 = player.getQuestStateList().getQuestState(2500);
/*  74 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  75 */       return false; 
/*  76 */     qs.setStatus(QuestStatus.START);
/*  77 */     updateQuestStatus(player, qs);
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  84 */     Player player = env.getPlayer();
/*  85 */     QuestState qs = player.getQuestStateList().getQuestState(2052);
/*  86 */     if (qs == null) {
/*  87 */       return false;
/*     */     }
/*  89 */     int var = qs.getQuestVarById(0);
/*  90 */     int targetId = 0;
/*  91 */     if (env.getVisibleObject() instanceof Npc) {
/*  92 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  94 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  96 */       if (targetId == 204715) {
/*     */         
/*  98 */         if (env.getDialogId().intValue() == -1)
/*  99 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 100 */         if (env.getDialogId().intValue() == 1009)
/* 101 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/* 102 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 104 */       return false;
/*     */     } 
/* 106 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 108 */       return false;
/*     */     }
/* 110 */     if (targetId == 204715) {
/*     */       
/* 112 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 115 */           if (var == 0)
/* 116 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 118 */           if (var == 0) {
/*     */             
/* 120 */             qs.setQuestVarById(0, var + 1);
/* 121 */             updateQuestStatus(player, qs);
/* 122 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 123 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 127 */     } else if (targetId == 204801) {
/*     */       
/* 129 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 132 */           if (var == 1)
/* 133 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 134 */           if (var == 12)
/* 135 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 10001:
/* 137 */           if (var == 1) {
/*     */             
/* 139 */             qs.setQuestVarById(0, var + 1);
/* 140 */             updateQuestStatus(player, qs);
/* 141 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 142 */             return true;
/*     */           } 
/*     */         case 10002:
/* 145 */           if (var == 12) {
/*     */             
/* 147 */             qs.setQuestVarById(0, var + 1);
/* 148 */             updateQuestStatus(player, qs);
/* 149 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 150 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 154 */     } else if (targetId == 204805) {
/*     */       
/* 156 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 159 */           if (var == 13)
/* 160 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 161 */           if (var == 14)
/* 162 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 33:
/* 164 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 166 */             qs.setQuestVarById(0, var + 1);
/* 167 */             updateQuestStatus(player, qs);
/* 168 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204304, 1)));
/* 169 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           } 
/*     */           
/* 172 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 10003:
/* 174 */           if (var == 13) {
/*     */             
/* 176 */             qs.setQuestVarById(0, var + 1);
/* 177 */             updateQuestStatus(player, qs);
/* 178 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 179 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 189 */     Player player = env.getPlayer();
/* 190 */     QuestState qs = player.getQuestStateList().getQuestState(2052);
/* 191 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 192 */       return false;
/*     */     }
/* 194 */     int targetId = 0;
/* 195 */     if (env.getVisibleObject() instanceof Npc) {
/* 196 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 198 */     if (targetId == 213044 && qs.getQuestVarById(0) > 1 && qs.getQuestVarById(0) < 12) {
/*     */ 
/*     */       
/* 201 */       qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 202 */       updateQuestStatus(player, qs);
/* 203 */       return true;
/*     */     } 
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 211 */     final Player player = env.getPlayer();
/* 212 */     final int id = item.getItemTemplate().getTemplateId();
/* 213 */     final int itemObjId = item.getObjectId();
/* 214 */     final QuestState qs = player.getQuestStateList().getQuestState(2052);
/*     */     
/* 216 */     if (qs == null || qs.getQuestVarById(0) != 15 || id != 182204304)
/* 217 */       return false; 
/* 218 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.BERITRAS_WEAPON_220040000)) {
/* 219 */       return false;
/*     */     }
/* 221 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/* 222 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 226 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 227 */             ItemService.removeItemFromInventoryByItemId(player, 182204304);
/* 228 */             qs.setStatus(QuestStatus.REWARD);
/* 229 */             _2052AnUndeadOccupation.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 232 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2052AnUndeadOccupation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */