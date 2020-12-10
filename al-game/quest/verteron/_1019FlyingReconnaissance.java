/*     */ package quest.verteron;
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
/*     */ import com.aionemu.gameserver.utils.MathUtil;
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
/*     */ public class _1019FlyingReconnaissance
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1019;
/*     */   
/*     */   public _1019FlyingReconnaissance() {
/*  54 */     super(Integer.valueOf(1019));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  60 */     this.qe.addQuestLvlUp(1019);
/*  61 */     this.qe.setNpcQuestData(203146).addOnTalkEvent(1019);
/*  62 */     this.qe.setQuestEnterZone(ZoneName.TURSIN_OUTPOST).add(1019);
/*  63 */     this.qe.setNpcQuestData(203098).addOnTalkEvent(1019);
/*  64 */     this.qe.setNpcQuestData(203147).addOnTalkEvent(1019);
/*  65 */     this.qe.setNpcQuestData(210158).addOnAttackEvent(1019);
/*  66 */     this.qe.setNpcQuestData(700037).addOnTalkEvent(1019);
/*  67 */     this.qe.setQuestItemIds(182200023).add(1019);
/*  68 */     this.qe.setNpcQuestData(210697).addOnKillEvent(1019);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  74 */     final Player player = env.getPlayer();
/*  75 */     final QuestState qs = player.getQuestStateList().getQuestState(1019);
/*  76 */     if (qs == null) {
/*  77 */       return false;
/*     */     }
/*  79 */     final int var = qs.getQuestVarById(0);
/*  80 */     int targetId = 0;
/*  81 */     if (env.getVisibleObject() instanceof Npc) {
/*  82 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  84 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  86 */       switch (targetId) {
/*     */         
/*     */         case 203146:
/*  89 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  92 */               if (var == 0)
/*  93 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */             case 10000:
/*  95 */               if (var == 0) {
/*     */                 
/*  97 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200505, 1))))
/*  98 */                   return true; 
/*  99 */                 qs.setQuestVarById(0, var + 1);
/* 100 */                 updateQuestStatus(player, qs);
/* 101 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 102 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203098:
/* 107 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 110 */               if (var == 2)
/* 111 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */             case 10001:
/* 113 */               if (var == 2) {
/*     */                 
/* 115 */                 qs.setQuestVarById(0, var + 1);
/* 116 */                 updateQuestStatus(player, qs);
/* 117 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 118 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203147:
/* 123 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 126 */               if (var == 3)
/* 127 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438); 
/* 128 */               if (var == 5)
/* 129 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */             case 10002:
/*     */             case 10003:
/* 132 */               if (var == 3 || var == 5) {
/*     */                 
/* 134 */                 if (var == 5 && 
/* 135 */                   !ItemService.addItems(player, Collections.singletonList(new QuestItems(182200023, 1))))
/* 136 */                   return true; 
/* 137 */                 qs.setQuestVarById(0, var + 1);
/* 138 */                 updateQuestStatus(player, qs);
/* 139 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 140 */                 return true;
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           break;
/*     */         case 700037:
/* 146 */           if (env.getDialogId().intValue() == -1) {
/*     */             
/* 148 */             if (var < 6 || var >= 9)
/* 149 */               return false; 
/* 150 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 151 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 153 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 155 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 159 */                     Npc npc = (Npc)player.getTarget();
/* 160 */                     if (npc == null || npc.getObjectId() != targetObjectId)
/*     */                       return; 
/* 162 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 164 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     
/* 166 */                     npc.getController().onDie(null);
/* 167 */                     qs.setQuestVarById(0, var + 1);
/* 168 */                     _1019FlyingReconnaissance.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/* 172 */           return false;
/*     */       } 
/*     */ 
/*     */     
/* 176 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 178 */       if (targetId == 203098 && env.getDialogId().intValue() == -1) {
/* 179 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */       }
/* 181 */       return defaultQuestEndDialog(env);
/*     */     } 
/*     */     
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 190 */     if (zoneName != ZoneName.TURSIN_OUTPOST)
/* 191 */       return false; 
/* 192 */     Player player = env.getPlayer();
/* 193 */     QuestState qs = player.getQuestStateList().getQuestState(1019);
/* 194 */     if (qs == null)
/* 195 */       return false; 
/* 196 */     if (zoneName == ZoneName.TURSIN_OUTPOST_ENTRANCE)
/* 197 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 18)); 
/* 198 */     if (qs.getQuestVarById(0) == 1) {
/*     */       
/* 200 */       qs.setQuestVarById(0, 2);
/* 201 */       updateQuestStatus(player, qs);
/* 202 */       return true;
/*     */     } 
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onAttackEvent(QuestEnv env) {
/* 210 */     Player player = env.getPlayer();
/* 211 */     QuestState qs = player.getQuestStateList().getQuestState(1019);
/* 212 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 4)
/* 213 */       return false; 
/* 214 */     int targetId = 0;
/* 215 */     if (env.getVisibleObject() instanceof Npc)
/* 216 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 217 */     if (targetId != 210158)
/* 218 */       return false; 
/* 219 */     if (MathUtil.getDistance(env.getVisibleObject(), 1552.74F, 1160.36F, 114.0F) < 6.0D) {
/*     */       
/* 221 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 13));
/* 222 */       ((Npc)env.getVisibleObject()).getController().onDie(null);
/* 223 */       qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 224 */       updateQuestStatus(player, qs);
/*     */     } 
/* 226 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/* 232 */     final Player player = env.getPlayer();
/* 233 */     final int id = item.getItemTemplate().getTemplateId();
/* 234 */     final int itemObjId = item.getObjectId();
/*     */     
/* 236 */     if (id != 182200023)
/* 237 */       return false; 
/* 238 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.TURSIN_TOTEM_POLE))
/* 239 */       return false; 
/* 240 */     final QuestState qs = player.getQuestStateList().getQuestState(1019);
/* 241 */     if (qs == null)
/* 242 */       return false; 
/* 243 */     int var = qs.getQuestVars().getQuestVars();
/* 244 */     if (var != 9)
/* 245 */       return false; 
/* 246 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/* 247 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 251 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 252 */             ItemService.removeItemFromInventory(player, item);
/* 253 */             qs.setQuestVarById(0, 10);
/* 254 */             _1019FlyingReconnaissance.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 257 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 263 */     Player player = env.getPlayer();
/* 264 */     QuestState qs = player.getQuestStateList().getQuestState(1019);
/* 265 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 266 */       return false;
/*     */     }
/* 268 */     int var = qs.getQuestVarById(0);
/* 269 */     int targetId = 0;
/* 270 */     if (env.getVisibleObject() instanceof Npc) {
/* 271 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 273 */     if (targetId == 210158 && 
/* 274 */       var == 1)
/* 275 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 22)); 
/* 276 */     qs.setStatus(QuestStatus.REWARD);
/* 277 */     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 278 */     updateQuestStatus(player, qs);
/*     */ 
/*     */ 
/*     */     
/* 282 */     if (targetId == 210697)
/*     */     {
/* 284 */       if (var == 10) {
/*     */         
/* 286 */         qs.setStatus(QuestStatus.REWARD);
/* 287 */         updateQuestStatus(player, qs);
/* 288 */         return true;
/*     */       } 
/*     */     }
/* 291 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 296 */     Player player = env.getPlayer();
/* 297 */     boolean lvlCheck = QuestService.checkLevelRequirement(1019, player.getCommonData().getLevel());
/* 298 */     QuestState qs = player.getQuestStateList().getQuestState(1019);
/* 299 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/* 300 */       return false; 
/* 301 */     qs.setStatus(QuestStatus.START);
/* 302 */     updateQuestStatus(player, qs);
/* 303 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1019FlyingReconnaissance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */