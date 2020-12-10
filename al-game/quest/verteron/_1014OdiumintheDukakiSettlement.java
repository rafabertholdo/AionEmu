/*     */ package quest.verteron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*     */ public class _1014OdiumintheDukakiSettlement
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1014;
/*  47 */   private static final int[] npc_ids = new int[] { 203129, 730020, 203098, 700090 };
/*  48 */   private static final int[] mob_ids = new int[] { 210145, 210174, 210739 };
/*     */ 
/*     */   
/*     */   public _1014OdiumintheDukakiSettlement() {
/*  52 */     super(Integer.valueOf(1014));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  58 */     this.qe.addQuestLvlUp(1014);
/*  59 */     this.qe.setQuestItemIds(182200012).add(1014);
/*  60 */     for (int mob_id : mob_ids)
/*  61 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1014); 
/*  62 */     for (int npc_id : npc_ids) {
/*  63 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1014);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  69 */     Player player = env.getPlayer();
/*  70 */     QuestState qs = player.getQuestStateList().getQuestState(1014);
/*  71 */     boolean lvlCheck = QuestService.checkLevelRequirement(1014, player.getCommonData().getLevel());
/*  72 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  73 */       return false; 
/*  74 */     qs.setStatus(QuestStatus.START);
/*  75 */     updateQuestStatus(player, qs);
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  82 */     final Player player = env.getPlayer();
/*  83 */     QuestState qs = player.getQuestStateList().getQuestState(1014);
/*  84 */     if (qs == null) {
/*  85 */       return false;
/*     */     }
/*  87 */     int var = qs.getQuestVarById(0);
/*  88 */     int targetId = 0;
/*  89 */     if (env.getVisibleObject() instanceof Npc) {
/*  90 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  92 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  94 */       if (targetId == 203098) {
/*  95 */         return defaultQuestEndDialog(env);
/*     */       }
/*  97 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  99 */       return false;
/*     */     } 
/* 101 */     if (targetId == 203129) {
/*     */       
/* 103 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 106 */           if (var == 0)
/* 107 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 108 */           if (var == 10)
/* 109 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 110 */           if (var == 14)
/* 111 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 112 */           return false;
/*     */         
/*     */         case 1013:
/* 115 */           if (var == 0) {
/*     */             
/* 117 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 26));
/* 118 */             return false;
/*     */           } 
/*     */         
/*     */         case 10000:
/* 122 */           if (var == 0) {
/*     */             
/* 124 */             qs.setQuestVarById(0, var + 1);
/* 125 */             updateQuestStatus(player, qs);
/* 126 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 127 */             return true;
/*     */           } 
/*     */         
/*     */         case 10001:
/* 131 */           if (var == 10) {
/*     */             
/* 133 */             qs.setQuestVarById(0, var + 1);
/* 134 */             updateQuestStatus(player, qs);
/* 135 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 136 */             return true;
/*     */           } 
/*     */         
/*     */         case 10002:
/* 140 */           if (var == 14) {
/*     */             
/* 142 */             qs.setStatus(QuestStatus.REWARD);
/* 143 */             updateQuestStatus(player, qs);
/* 144 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 145 */             return true;
/*     */           } 
/*     */           
/* 148 */           return false;
/*     */       } 
/*     */     
/* 151 */     } else if (targetId == 730020) {
/*     */       
/* 153 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 156 */           if (var == 1)
/* 157 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 158 */           return false;
/*     */         
/*     */         case 10001:
/* 161 */           if (var == 1) {
/*     */             
/* 163 */             qs.setQuestVarById(0, var + 1);
/* 164 */             updateQuestStatus(player, qs);
/* 165 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 166 */             return true;
/*     */           } 
/* 168 */           return false;
/*     */       } 
/*     */     
/* 171 */     } else if (targetId == 700090) {
/*     */       
/* 173 */       if (var == 11 && env.getDialogId().intValue() == -1) {
/*     */         
/* 175 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/*     */         
/* 177 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 178 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 179 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 183 */                 if (!player.isTargeting(targetObjectId))
/*     */                   return; 
/* 185 */                 if (player.getInventory().getItemCountByItemId(182200011) == 0L)
/*     */                   return; 
/* 187 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 188 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 189 */                 QuestService.addNewSpawn(210030000, 1, 210739, 757.7F, 2477.2F, 217.4F, (byte)0, true);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 201 */     Player player = env.getPlayer();
/* 202 */     QuestState qs = player.getQuestStateList().getQuestState(1014);
/* 203 */     if (qs == null) {
/* 204 */       return false;
/*     */     }
/* 206 */     int targetId = 0;
/* 207 */     if (env.getVisibleObject() instanceof Npc) {
/* 208 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 210 */     if (qs.getStatus() != QuestStatus.START)
/* 211 */       return false; 
/* 212 */     if (targetId == 210145 && qs.getQuestVarById(0) < 10) {
/*     */       
/* 214 */       qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 215 */       updateQuestStatus(player, qs);
/*     */     } 
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 223 */     final Player player = env.getPlayer();
/* 224 */     final int id = item.getItemTemplate().getTemplateId();
/* 225 */     final int itemObjId = item.getObjectId();
/* 226 */     final QuestState qs = player.getQuestStateList().getQuestState(1014);
/*     */     
/* 228 */     if (id != 182200012 || qs.getQuestVarById(0) != 11)
/* 229 */       return false; 
/* 230 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.ODIUM_REFINING_CAULDRON)) {
/* 231 */       return false;
/*     */     }
/* 233 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/* 234 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 238 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 172));
/* 239 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 240 */             ItemService.decreaseItemCountByItemId(player, 182200012, 1L);
/* 241 */             ItemService.decreaseItemCountByItemId(player, 182200011, 1L);
/* 242 */             qs.setQuestVarById(0, 14);
/* 243 */             _1014OdiumintheDukakiSettlement.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 246 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1014OdiumintheDukakiSettlement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */