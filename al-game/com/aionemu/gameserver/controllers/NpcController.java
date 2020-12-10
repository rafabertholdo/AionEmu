/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.ai.events.Event;
/*     */ import com.aionemu.gameserver.controllers.attack.AttackResult;
/*     */ import com.aionemu.gameserver.controllers.attack.AttackUtil;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.TaskId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.NpcGameStats;
/*     */ import com.aionemu.gameserver.model.templates.TradeListTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SELL_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TRADELIST;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.services.CraftSkillUpdateService;
/*     */ import com.aionemu.gameserver.services.CubeExpandService;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.LegionService;
/*     */ import com.aionemu.gameserver.services.RespawnService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.services.TradeService;
/*     */ import com.aionemu.gameserver.services.WarehouseService;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Future;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NpcController
/*     */   extends CreatureController<Npc>
/*     */ {
/*  75 */   private static final Logger log = Logger.getLogger(NpcController.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public void notSee(VisibleObject object, boolean isOutOfRange) {
/*  80 */     super.notSee(object, isOutOfRange);
/*  81 */     if (object instanceof Player || object instanceof com.aionemu.gameserver.model.gameobjects.Summon) {
/*  82 */       getOwner().getAi().handleEvent(Event.NOT_SEE_PLAYER);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void see(VisibleObject object) {
/*  88 */     super.see(object);
/*  89 */     Npc owner = getOwner();
/*  90 */     owner.getAi().handleEvent(Event.SEE_CREATURE);
/*  91 */     if (object instanceof Player) {
/*     */       
/*  93 */       owner.getAi().handleEvent(Event.SEE_PLAYER);
/*     */ 
/*     */       
/*  96 */       if (owner.getMoveController().isWalking()) {
/*  97 */         PacketSendUtility.sendPacket((Player)object, (AionServerPacket)new SM_EMOTION((Creature)owner, EmotionType.WALK));
/*     */       }
/*  99 */     } else if (object instanceof com.aionemu.gameserver.model.gameobjects.Summon) {
/*     */       
/* 101 */       owner.getAi().handleEvent(Event.SEE_PLAYER);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRespawn() {
/* 108 */     super.onRespawn();
/*     */     
/* 110 */     cancelTask(TaskId.DECAY);
/* 111 */     Npc owner = getOwner();
/*     */ 
/*     */     
/* 114 */     if (owner.getObjectTemplate().getState() != 0) {
/* 115 */       owner.setState(owner.getObjectTemplate().getState());
/*     */     } else {
/* 117 */       owner.setState(CreatureState.NPC_IDLE);
/*     */     } 
/* 119 */     owner.getLifeStats().setCurrentHpPercent(100);
/* 120 */     if (owner.getSpawn().getNpcFlyState() != 0)
/*     */     {
/* 122 */       owner.setState(CreatureState.FLYING);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDespawn(boolean forced) {
/* 128 */     if (forced) {
/* 129 */       cancelTask(TaskId.DECAY);
/*     */     }
/* 131 */     Npc owner = getOwner();
/* 132 */     if (owner == null || !owner.isSpawned()) {
/*     */       return;
/*     */     }
/* 135 */     World.getInstance().despawn((VisibleObject)owner);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDie(Creature lastAttacker) {
/* 141 */     super.onDie(lastAttacker);
/* 142 */     Npc owner = getOwner();
/*     */     
/* 144 */     addTask(TaskId.DECAY, RespawnService.scheduleDecayTask(getOwner()));
/* 145 */     scheduleRespawn();
/*     */     
/* 147 */     PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_EMOTION((Creature)owner, EmotionType.DIE, 0, (lastAttacker == null) ? 0 : lastAttacker.getObjectId()));
/*     */ 
/*     */ 
/*     */     
/* 151 */     doReward();
/*     */     
/* 153 */     owner.getAi().stop();
/*     */ 
/*     */     
/* 156 */     owner.setTarget(null);
/* 157 */     PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_LOOKATOBJECT((VisibleObject)owner));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Npc getOwner() {
/* 163 */     return (Npc)super.getOwner();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogRequest(Player player) {
/* 169 */     getOwner().getAi().handleEvent(Event.TALK);
/*     */     
/* 171 */     if (QuestEngine.getInstance().onDialog(new QuestEnv((VisibleObject)getOwner(), player, Integer.valueOf(0), Integer.valueOf(-1)))) {
/*     */       return;
/*     */     }
/* 174 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(getOwner().getObjectId(), 10));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDelete() {
/* 182 */     if (getOwner().isInWorld()) {
/*     */       
/* 184 */       getOwner().getAi().clearDesires();
/* 185 */       onDespawn(true);
/* 186 */       delete();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDialogSelect(int dialogId, final Player player, int questId) {
/*     */     TradeListTemplate tradeListTemplate;
/*     */     final long expLost;
/*     */     double factor;
/*     */     final int price;
/*     */     RequestResponseHandler responseHandler;
/* 197 */     Npc npc = getOwner();
/* 198 */     if (!MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F))
/*     */       return; 
/* 200 */     int targetObjectId = npc.getObjectId();
/*     */     
/* 202 */     if (QuestEngine.getInstance().onDialog(new QuestEnv((VisibleObject)npc, player, Integer.valueOf(questId), Integer.valueOf(dialogId))))
/*     */       return; 
/* 204 */     switch (dialogId) {
/*     */       
/*     */       case 2:
/* 207 */         tradeListTemplate = DataManager.TRADE_LIST_DATA.getTradeListTemplate(npc.getNpcId());
/* 208 */         if (tradeListTemplate == null) {
/*     */           
/* 210 */           PacketSendUtility.sendMessage(player, "Buy list is missing!!");
/*     */         } else {
/*     */           
/* 213 */           int tradeModifier = tradeListTemplate.getSellPriceRate();
/* 214 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_TRADELIST(npc, TradeService.getTradeListData().getTradeListTemplate(npc.getNpcId()), player.getPrices().getVendorBuyModifier() * tradeModifier / 100));
/*     */         } 
/*     */ 
/*     */       
/*     */       case 3:
/* 219 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SELL_ITEM(targetObjectId, player.getPrices().getVendorSellModifier()));
/*     */ 
/*     */       
/*     */       case 4:
/* 223 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 1));
/*     */ 
/*     */       
/*     */       case 5:
/* 227 */         if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {
/*     */           
/* 229 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 2));
/*     */         }
/*     */         else {
/*     */           
/* 233 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CREATE_TOO_FAR_FROM_NPC());
/*     */         } 
/*     */ 
/*     */       
/*     */       case 6:
/* 238 */         if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {
/*     */           
/* 240 */           LegionService.getInstance().requestDisbandLegion((Creature)npc, player);
/*     */         }
/*     */         else {
/*     */           
/* 244 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPERSE_TOO_FAR_FROM_NPC());
/*     */         } 
/*     */ 
/*     */       
/*     */       case 7:
/* 249 */         if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {
/*     */ 
/*     */           
/* 252 */           LegionService.getInstance().recreateLegion(npc, player);
/*     */         }
/*     */         else {
/*     */           
/* 256 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPERSE_TOO_FAR_FROM_NPC());
/*     */         } 
/*     */ 
/*     */       
/*     */       case 20:
/* 261 */         if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {
/*     */ 
/*     */           
/* 264 */           if (!RestrictionsManager.canUseWarehouse(player)) {
/*     */             return;
/*     */           }
/* 267 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 26));
/* 268 */           WarehouseService.sendWarehouseInfo(player, true);
/*     */         } 
/*     */ 
/*     */       
/*     */       case 27:
/* 273 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 13));
/*     */ 
/*     */       
/*     */       case 29:
/* 277 */         expLost = player.getCommonData().getExpRecoverable();
/* 278 */         factor = (expLost < 1000000L) ? (0.25D - 1.5E-7D * expLost) : 0.1D;
/*     */ 
/*     */         
/* 281 */         price = (int)(expLost * factor);
/*     */         
/* 283 */         responseHandler = new RequestResponseHandler((Creature)npc)
/*     */           {
/*     */             public void acceptRequest(Creature requester, Player responder)
/*     */             {
/* 287 */               if (ItemService.decreaseKinah(player, price)) {
/*     */                 
/* 289 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.EXP(String.valueOf(expLost)));
/* 290 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.SOUL_HEALED());
/* 291 */                 player.getCommonData().resetRecoverableExp();
/*     */               }
/*     */               else {
/*     */                 
/* 295 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.NOT_ENOUGH_KINAH(price));
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void denyRequest(Creature requester, Player responder) {}
/*     */           };
/* 305 */         if (player.getCommonData().getExpRecoverable() > 0L) {
/*     */           
/* 307 */           boolean result = player.getResponseRequester().putRequest(160011, responseHandler);
/*     */           
/* 309 */           if (result)
/*     */           {
/* 311 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(160011, 0, new Object[] { String.valueOf(price) }));
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 318 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.DONT_HAVE_RECOVERED_EXP());
/*     */         } 
/*     */       
/*     */       case 30:
/* 322 */         switch (npc.getNpcId()) {
/*     */           
/*     */           case 204089:
/* 325 */             TeleportService.teleportTo(player, 120010000, 1, 984.0F, 1543.0F, 222.1F, 0);
/*     */             break;
/*     */           case 203764:
/* 328 */             TeleportService.teleportTo(player, 110010000, 1, 1462.5F, 1326.1F, 564.1F, 0);
/*     */             break;
/*     */           case 203981:
/* 331 */             TeleportService.teleportTo(player, 210020000, 1, 439.3F, 422.2F, 274.3F, 0);
/*     */             break;
/*     */         } 
/*     */       
/*     */       case 31:
/* 336 */         switch (npc.getNpcId()) {
/*     */           
/*     */           case 204087:
/* 339 */             TeleportService.teleportTo(player, 120010000, 1, 1005.1F, 1528.9F, 222.1F, 0);
/*     */             break;
/*     */           case 203875:
/* 342 */             TeleportService.teleportTo(player, 110010000, 1, 1470.3F, 1343.5F, 563.7F, 21);
/*     */             break;
/*     */           case 203982:
/* 345 */             TeleportService.teleportTo(player, 210020000, 1, 446.2F, 431.1F, 274.5F, 0);
/*     */             break;
/*     */         } 
/*     */       
/*     */       
/*     */       case 35:
/* 351 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 21));
/*     */ 
/*     */       
/*     */       case 36:
/* 355 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 20));
/*     */ 
/*     */       
/*     */       case 37:
/* 359 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 19));
/*     */ 
/*     */       
/*     */       case 38:
/* 363 */         TeleportService.showMap(player, targetObjectId, npc.getNpcId());
/*     */ 
/*     */ 
/*     */       
/*     */       case 39:
/*     */       case 40:
/* 369 */         CraftSkillUpdateService.getInstance().learnSkill(player, npc);
/*     */ 
/*     */       
/*     */       case 41:
/* 373 */         CubeExpandService.expandCube(player, npc);
/*     */       
/*     */       case 42:
/* 376 */         WarehouseService.expandWarehouse(player, npc);
/*     */ 
/*     */       
/*     */       case 47:
/* 380 */         if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {
/* 381 */           LegionService.getInstance().openLegionWarehouse(player);
/*     */         }
/*     */       
/*     */       case 50:
/*     */         return;
/*     */       case 52:
/* 387 */         if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F))
/*     */         {
/* 389 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 28));
/*     */         }
/*     */ 
/*     */       
/*     */       case 53:
/* 394 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, "This feature is not available yet", ChatType.ANNOUNCEMENTS));
/*     */ 
/*     */ 
/*     */       
/*     */       case 60:
/* 399 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 29));
/*     */ 
/*     */       
/*     */       case 61:
/* 403 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 30));
/*     */     } 
/*     */     
/* 406 */     if (questId > 0) {
/* 407 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, dialogId, questId));
/*     */     }
/* 409 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, dialogId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
/* 417 */     if (getOwner().getLifeStats().isAlreadyDead()) {
/*     */       return;
/*     */     }
/* 420 */     super.onAttack(creature, skillId, type, damage);
/*     */     
/* 422 */     Npc npc = getOwner();
/*     */     
/* 424 */     Creature actingCreature = creature.getActingCreature();
/* 425 */     if (actingCreature instanceof Player && 
/* 426 */       QuestEngine.getInstance().onAttack(new QuestEnv((VisibleObject)npc, (Player)actingCreature, Integer.valueOf(0), Integer.valueOf(0)))) {
/*     */       return;
/*     */     }
/* 429 */     AI<?> ai = npc.getAi();
/* 430 */     if (ai instanceof com.aionemu.gameserver.ai.npcai.DummyAi) {
/*     */       
/* 432 */       log.warn("CHECKPOINT: npc attacked without ai " + npc.getObjectTemplate().getTemplateId());
/*     */       return;
/*     */     } 
/* 435 */     for (VisibleObject obj : getOwner().getKnownList().getKnownObjects().values()) {
/*     */       
/* 437 */       if (obj instanceof Npc) {
/*     */         
/* 439 */         Npc tmp = (Npc)obj;
/* 440 */         if (getOwner().isSupportFrom(tmp) && MathUtil.isInRange((VisibleObject)getOwner(), obj, 10.0F))
/*     */         {
/* 442 */           tmp.getAggroList().addHate(creature, 10);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 447 */     npc.getLifeStats().reduceHp(damage, actingCreature);
/*     */     
/* 449 */     PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_ATTACK_STATUS((Creature)npc, type, skillId, damage));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackTarget(Creature target) {
/* 455 */     Npc npc = getOwner();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 460 */     if (npc == null || npc.getLifeStats().isAlreadyDead() || !npc.isSpawned()) {
/*     */       return;
/*     */     }
/* 463 */     if (!npc.canAttack()) {
/*     */       return;
/*     */     }
/* 466 */     AI<?> ai = npc.getAi();
/* 467 */     NpcGameStats gameStats = npc.getGameStats();
/*     */     
/* 469 */     if (target == null || target.getLifeStats().isAlreadyDead()) {
/*     */       
/* 471 */       ai.handleEvent(Event.MOST_HATED_CHANGED);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 478 */     super.attackTarget(target);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 483 */     List<AttackResult> attackList = AttackUtil.calculateAttackResult((Creature)npc, target);
/*     */     
/* 485 */     int damage = 0;
/* 486 */     for (AttackResult result : attackList)
/*     */     {
/* 488 */       damage += result.getDamage();
/*     */     }
/*     */     
/* 491 */     int attackType = 0;
/* 492 */     PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_ATTACK((Creature)npc, target, gameStats.getAttackCounter(), 274, attackType, attackList));
/*     */ 
/*     */     
/* 495 */     target.getController().onAttack((Creature)npc, damage);
/* 496 */     gameStats.increaseAttackCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scheduleRespawn() {
/* 505 */     if (getOwner().isInInstance()) {
/*     */       return;
/*     */     }
/* 508 */     int instanceId = getOwner().getInstanceId();
/* 509 */     if (!getOwner().getSpawn().isNoRespawn(instanceId)) {
/*     */       
/* 511 */       Future<?> respawnTask = RespawnService.scheduleRespawnTask((VisibleObject)getOwner());
/* 512 */       addTask(TaskId.RESPAWN, respawnTask);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\NpcController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */