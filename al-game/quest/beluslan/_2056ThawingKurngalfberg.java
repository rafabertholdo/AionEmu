/*     */ package quest.beluslan;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
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
/*     */ public class _2056ThawingKurngalfberg
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2056;
/*  47 */   private static final int[] npc_ids = new int[] { 204753, 790016, 730036, 279000 };
/*     */ 
/*     */   
/*     */   public _2056ThawingKurngalfberg() {
/*  51 */     super(Integer.valueOf(2056));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  57 */     this.qe.setQuestItemIds(182204313).add(2056);
/*  58 */     this.qe.setQuestItemIds(182204314).add(2056);
/*  59 */     this.qe.setQuestItemIds(182204315).add(2056);
/*  60 */     this.qe.addQuestLvlUp(2056);
/*  61 */     for (int npc_id : npc_ids) {
/*  62 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2056);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  68 */     Player player = env.getPlayer();
/*  69 */     QuestState qs = player.getQuestStateList().getQuestState(2056);
/*  70 */     boolean lvlCheck = QuestService.checkLevelRequirement(2056, player.getCommonData().getLevel());
/*  71 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  72 */       return false;
/*     */     }
/*  74 */     QuestState qs2 = player.getQuestStateList().getQuestState(2500);
/*  75 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  76 */       return false; 
/*  77 */     qs.setStatus(QuestStatus.START);
/*  78 */     updateQuestStatus(player, qs);
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  85 */     Player player = env.getPlayer();
/*  86 */     QuestState qs = player.getQuestStateList().getQuestState(2056);
/*  87 */     if (qs == null) {
/*  88 */       return false;
/*     */     }
/*  90 */     int var = qs.getQuestVarById(0);
/*  91 */     int targetId = 0;
/*  92 */     if (env.getVisibleObject() instanceof Npc) {
/*  93 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  95 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  97 */       if (targetId == 204753) {
/*     */         
/*  99 */         if (env.getDialogId().intValue() == -1)
/* 100 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 101 */         if (env.getDialogId().intValue() == 1009)
/* 102 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/* 103 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 105 */       return false;
/*     */     } 
/* 107 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 109 */       return false;
/*     */     }
/* 111 */     if (targetId == 204753) {
/*     */       
/* 113 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 116 */           if (var == 0)
/* 117 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 118 */           if (var == 1)
/* 119 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 1012:
/* 121 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 242));
/*     */           break;
/*     */         case 2376:
/* 124 */           if (QuestService.collectItemCheck(env, false)) {
/* 125 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2376);
/*     */           }
/* 127 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);
/*     */         case 10000:
/* 129 */           if (var == 0) {
/*     */             
/* 131 */             qs.setQuestVarById(0, var + 1);
/* 132 */             updateQuestStatus(player, qs);
/* 133 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 134 */             return true;
/*     */           } 
/*     */         case 10004:
/* 137 */           if (var == 1) {
/*     */             
/* 139 */             qs.setQuestVarById(0, var + 1);
/* 140 */             updateQuestStatus(player, qs);
/* 141 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 142 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 146 */     } else if (targetId == 790016) {
/*     */       
/* 148 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 151 */           if (var == 1)
/* 152 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 2035:
/* 154 */           if (var == 1 && player.getInventory().getItemCountByItemId(182204315) != 1L) {
/*     */             
/* 156 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204315, 1)));
/* 157 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
/*     */           } 
/*     */           
/* 160 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */       } 
/*     */     
/* 163 */     } else if (targetId == 730036) {
/*     */       
/* 165 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 168 */           if (var == 1)
/* 169 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 1353:
/* 171 */           if (var == 1 && player.getInventory().getItemCountByItemId(182204313) != 1L) {
/*     */             
/* 173 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204313, 1)));
/* 174 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
/*     */           } 
/*     */           
/* 177 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
/*     */       } 
/*     */     
/* 180 */     } else if (targetId == 279000) {
/*     */       
/* 182 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 185 */           if (var == 1)
/* 186 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 1694:
/* 188 */           if (var == 1 && player.getInventory().getItemCountByItemId(182204314) != 1L) {
/*     */             
/* 190 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204314, 1)));
/* 191 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
/*     */           } 
/*     */           
/* 194 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
/*     */       } 
/*     */     } 
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 203 */     final Player player = env.getPlayer();
/* 204 */     final int id = item.getItemTemplate().getTemplateId();
/* 205 */     final int itemObjId = item.getObjectId();
/*     */     
/* 207 */     final QuestState qs = player.getQuestStateList().getQuestState(2056);
/* 208 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.THE_SACRED_ORCHARD_220040000)) {
/* 209 */       return false;
/*     */     }
/* 211 */     if ((id != 182204313 && qs.getQuestVarById(0) == 2) || (id != 182204314 && qs.getQuestVarById(0) == 3) || (id != 182204315 && qs.getQuestVarById(0) == 4))
/*     */     {
/* 213 */       return false;
/*     */     }
/* 215 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 2000, 0, 0), true);
/* 216 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 220 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 221 */             if (qs.getQuestVarById(0) == 2) {
/*     */               
/* 223 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 243));
/* 224 */               ItemService.removeItemFromInventoryByItemId(player, id);
/* 225 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 226 */               _2056ThawingKurngalfberg.this.updateQuestStatus(player, qs);
/*     */             }
/* 228 */             else if (qs.getQuestVarById(0) == 3) {
/*     */               
/* 230 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 244));
/* 231 */               ItemService.removeItemFromInventoryByItemId(player, id);
/* 232 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 233 */               _2056ThawingKurngalfberg.this.updateQuestStatus(player, qs);
/*     */             }
/* 235 */             else if (qs.getQuestVarById(0) == 4) {
/*     */               
/* 237 */               ItemService.removeItemFromInventoryByItemId(player, id);
/* 238 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 245));
/* 239 */               qs.setStatus(QuestStatus.REWARD);
/* 240 */               _2056ThawingKurngalfberg.this.updateQuestStatus(player, qs);
/*     */             } 
/*     */           }
/*     */         }2000L);
/* 244 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2056ThawingKurngalfberg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */