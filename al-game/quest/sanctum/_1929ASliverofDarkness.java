/*     */ package quest.sanctum;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Equipment;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.SystemMessageId;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.InstanceService;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.WorldMapInstance;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class _1929ASliverofDarkness
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1929;
/*     */   
/*     */   public _1929ASliverofDarkness() {
/*  58 */     super(Integer.valueOf(1929));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  64 */     this.qe.addQuestLvlUp(1929);
/*  65 */     this.qe.setNpcQuestData(203752).addOnTalkEvent(1929);
/*  66 */     this.qe.setNpcQuestData(203852).addOnTalkEvent(1929);
/*  67 */     this.qe.setNpcQuestData(203164).addOnTalkEvent(1929);
/*  68 */     this.qe.setNpcQuestData(205110).addOnTalkEvent(1929);
/*  69 */     this.qe.setNpcQuestData(700419).addOnTalkEvent(1929);
/*  70 */     this.qe.setNpcQuestData(205111).addOnTalkEvent(1929);
/*  71 */     this.qe.setQuestMovieEndIds(155).add(1929);
/*  72 */     this.qe.setNpcQuestData(212992).addOnKillEvent(1929);
/*  73 */     this.qe.setNpcQuestData(203701).addOnTalkEvent(1929);
/*  74 */     this.qe.setNpcQuestData(203711).addOnTalkEvent(1929);
/*  75 */     this.qe.addOnEnterWorld(1929);
/*  76 */     this.qe.addOnDie(1929);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  82 */     final Player player = env.getPlayer();
/*  83 */     final int instanceId = player.getInstanceId();
/*  84 */     final QuestState qs = player.getQuestStateList().getQuestState(1929);
/*  85 */     if (qs == null) {
/*  86 */       return false;
/*     */     }
/*  88 */     int var = qs.getQuestVars().getQuestVars();
/*  89 */     int targetId = 0;
/*  90 */     if (env.getVisibleObject() instanceof Npc) {
/*  91 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  93 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  95 */       switch (targetId) {
/*     */         
/*     */         case 203752:
/*  98 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 101 */               if (var == 0)
/* 102 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */             case 10000:
/* 104 */               if (var == 0) {
/*     */                 
/* 106 */                 qs.setQuestVarById(0, var + 1);
/* 107 */                 updateQuestStatus(player, qs);
/* 108 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 109 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203852:
/* 114 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 117 */               if (var == 1)
/* 118 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */             case 10001:
/* 120 */               if (var == 1) {
/*     */                 
/* 122 */                 qs.setQuestVarById(0, var + 1);
/* 123 */                 updateQuestStatus(player, qs);
/* 124 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 125 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203164:
/* 130 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 133 */               if (var == 2)
/* 134 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 135 */               if (var == 8)
/* 136 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */               break;
/*     */             case 10002:
/* 139 */               if (var == 2) {
/*     */                 
/* 141 */                 qs.setQuestVar(93);
/* 142 */                 updateQuestStatus(player, qs);
/* 143 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 144 */                 WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(310070000);
/* 145 */                 InstanceService.registerPlayerWithInstance(newInstance, player);
/* 146 */                 TeleportService.teleportTo(player, 310070000, newInstance.getInstanceId(), 338.0F, 101.0F, 1191.0F, 0);
/* 147 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 10006:
/* 151 */               if (var == 8) {
/*     */                 
/* 153 */                 removeStigma(player);
/* 154 */                 qs.setQuestVarById(0, var + 1);
/* 155 */                 updateQuestStatus(player, qs);
/* 156 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 157 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 205110:
/* 162 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 165 */               if (var == 93)
/* 166 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */             case 10003:
/* 168 */               if (var == 93) {
/*     */                 
/* 170 */                 qs.setQuestVar(94);
/* 171 */                 updateQuestStatus(player, qs);
/* 172 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 173 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 31001, 0));
/* 174 */                 return true;
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           break;
/*     */         case 700419:
/* 180 */           if (qs.getQuestVars().getQuestVars() == 94 && env.getDialogId().intValue() == -1) {
/*     */             
/* 182 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 183 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 185 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 187 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 191 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/* 193 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 195 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */ 
/*     */                     
/* 198 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 155));
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 205111:
/* 205 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case -1:
/* 208 */               if (var == 98) {
/*     */                 
/* 210 */                 int itemId = getStoneId(player);
/* 211 */                 if (player.getEquipment().getEquippedItemsByItemId(itemId).size() != 0) {
/*     */                   
/* 213 */                   qs.setQuestVar(96);
/* 214 */                   updateQuestStatus(player, qs);
/*     */                 } 
/* 216 */                 return false;
/*     */               } 
/*     */               break;
/*     */             case 25:
/* 220 */               if (var == 98)
/* 221 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 222 */               if (var == 96)
/* 223 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */               break;
/*     */             case 2546:
/* 226 */               if (var == 98) {
/*     */                 
/* 228 */                 int itemId = getStoneId(player);
/* 229 */                 if (player.getInventory().getItemCountByItemId(itemId) > 0L) {
/*     */                   
/* 231 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 1));
/* 232 */                   return true;
/*     */                 } 
/* 234 */                 List<QuestItems> items = new ArrayList<QuestItems>();
/* 235 */                 items.add(new QuestItems(itemId, 1));
/* 236 */                 items.add(new QuestItems(141000001, 60));
/* 237 */                 if (ItemService.addItems(player, items))
/*     */                 {
/* 239 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 1));
/*     */                 }
/* 241 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 2720:
/* 245 */               if (var == 96) {
/*     */                 
/* 247 */                 Npc npc = (Npc)env.getVisibleObject();
/* 248 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 249 */                 npc.getController().delete();
/* 250 */                 ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/* 254 */                         QuestService.addNewSpawn(310070000, instanceId, 212992, 191.9F, 267.68F, 1374.0F, (byte)0, true);
/* 255 */                         qs.setQuestVar(97);
/* 256 */                         _1929ASliverofDarkness.this.updateQuestStatus(player, qs);
/*     */                       }
/*     */                     }5000L);
/* 259 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203701:
/* 264 */           if (var == 9)
/*     */           {
/* 266 */             switch (env.getDialogId().intValue()) {
/*     */               
/*     */               case 25:
/* 269 */                 if (var == 9)
/* 270 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/*     */               case 10007:
/* 272 */                 if (var == 9) {
/*     */                   
/* 274 */                   qs.setStatus(QuestStatus.REWARD);
/* 275 */                   updateQuestStatus(player, qs);
/* 276 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 277 */                   return true;
/*     */                 } 
/*     */                 break;
/*     */             } 
/*     */           }
/*     */           break;
/*     */       } 
/* 284 */     } else if (qs.getStatus() == QuestStatus.REWARD && targetId == 203711) {
/*     */       
/* 286 */       if (env.getDialogId().intValue() == -1)
/* 287 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 288 */       return defaultQuestEndDialog(env);
/*     */     } 
/*     */     
/* 291 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMovieEndEvent(QuestEnv env, int movieId) {
/* 297 */     if (movieId != 155)
/* 298 */       return false; 
/* 299 */     Player player = env.getPlayer();
/* 300 */     int instanceId = player.getInstanceId();
/* 301 */     env.setQuestId(Integer.valueOf(1929));
/* 302 */     QuestState qs = player.getQuestStateList().getQuestState(1929);
/* 303 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 94)
/* 304 */       return false; 
/* 305 */     QuestService.addNewSpawn(310070000, instanceId, 205111, 197.6F, 265.9F, 1374.0F, (byte)0, true);
/* 306 */     qs.setQuestVar(98);
/* 307 */     updateQuestStatus(player, qs);
/* 308 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 314 */     Player player = env.getPlayer();
/* 315 */     QuestState qs = player.getQuestStateList().getQuestState(1929);
/* 316 */     if (qs != null)
/* 317 */       return false; 
/* 318 */     boolean lvlCheck = QuestService.checkLevelRequirement(1929, player.getCommonData().getLevel());
/* 319 */     if (!lvlCheck)
/* 320 */       return false; 
/* 321 */     env.setQuestId(Integer.valueOf(1929));
/* 322 */     QuestService.startQuest(env, QuestStatus.START);
/* 323 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 329 */     final Player player = env.getPlayer();
/* 330 */     QuestState qs = player.getQuestStateList().getQuestState(1929);
/* 331 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 332 */       return false;
/*     */     }
/* 334 */     int targetId = 0;
/* 335 */     if (env.getVisibleObject() instanceof Npc) {
/* 336 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 338 */     if (targetId == 212992 && qs.getQuestVars().getQuestVars() == 97) {
/*     */       
/* 340 */       qs.setQuestVar(8);
/* 341 */       updateQuestStatus(player, qs);
/* 342 */       ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 346 */               TeleportService.teleportTo(player, 210030000, 1, 2315.9F, 1800.0F, 195.2F, 0);
/*     */             }
/*     */           }5000L);
/* 349 */       return true;
/*     */     } 
/* 351 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDieEvent(QuestEnv env) {
/* 357 */     Player player = env.getPlayer();
/* 358 */     QuestState qs = player.getQuestStateList().getQuestState(1929);
/* 359 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/* 360 */       return false; 
/* 361 */     int var = qs.getQuestVars().getQuestVars();
/* 362 */     if (var > 90) {
/*     */       
/* 364 */       removeStigma(player);
/* 365 */       qs.setQuestVar(2);
/* 366 */       updateQuestStatus(player, qs);
/* 367 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, new Object[] { DataManager.QUEST_DATA.getQuestById(1929).getName() }));
/*     */     } 
/* 369 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterWorldEvent(QuestEnv env) {
/* 375 */     Player player = env.getPlayer();
/* 376 */     QuestState qs = player.getQuestStateList().getQuestState(1929);
/* 377 */     if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */       
/* 379 */       int var = qs.getQuestVars().getQuestVars();
/* 380 */       if (var > 90)
/*     */       {
/* 382 */         if (player.getWorldId() != 310070000) {
/*     */           
/* 384 */           removeStigma(player);
/* 385 */           qs.setQuestVar(2);
/* 386 */           updateQuestStatus(player, qs);
/* 387 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, new Object[] { DataManager.QUEST_DATA.getQuestById(1929).getName() }));
/*     */         } 
/*     */       }
/*     */     } 
/* 391 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getStoneId(Player player) {
/* 396 */     switch (player.getCommonData().getPlayerClass()) {
/*     */       
/*     */       case GLADIATOR:
/* 399 */         return 140000008;
/*     */       case TEMPLAR:
/* 401 */         return 140000027;
/*     */       case RANGER:
/* 403 */         return 140000047;
/*     */       case ASSASSIN:
/* 405 */         return 140000076;
/*     */       case SORCERER:
/* 407 */         return 140000131;
/*     */       case SPIRIT_MASTER:
/* 409 */         return 140000147;
/*     */       case CLERIC:
/* 411 */         return 140000098;
/*     */       case CHANTER:
/* 413 */         return 140000112;
/*     */     } 
/* 415 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeStigma(Player player) {
/* 420 */     int itemId = getStoneId(player);
/* 421 */     List<Item> items = player.getEquipment().getEquippedItemsByItemId(itemId);
/* 422 */     Equipment equipment = player.getEquipment();
/* 423 */     for (Item item : items)
/*     */     {
/* 425 */       equipment.unEquipItem(item.getObjectId(), 0);
/*     */     }
/* 427 */     ItemService.removeItemFromInventoryByItemId(player, itemId);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_1929ASliverofDarkness.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */