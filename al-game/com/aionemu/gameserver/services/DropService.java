/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.dao.DropListDAO;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
/*     */ import com.aionemu.gameserver.model.drop.DropItem;
/*     */ import com.aionemu.gameserver.model.drop.DropList;
/*     */ import com.aionemu.gameserver.model.drop.DropTemplate;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.DropNpc;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemQuality;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_LOOT;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOT_ITEMLIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOT_STATUS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.stats.DropRewardEnum;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javolution.util.FastMap;
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
/*     */ public class DropService
/*     */ {
/*  60 */   private static final Logger log = Logger.getLogger(DropService.class);
/*     */   
/*     */   private DropList dropList;
/*     */   
/*  64 */   private Map<Integer, Set<DropItem>> currentDropMap = (Map<Integer, Set<DropItem>>)(new FastMap()).shared();
/*  65 */   private Map<Integer, DropNpc> dropRegistrationMap = (Map<Integer, DropNpc>)(new FastMap()).shared();
/*     */ 
/*     */   
/*     */   public static final DropService getInstance() {
/*  69 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private DropService() {
/*  74 */     this.dropList = ((DropListDAO)DAOManager.getDAO(DropListDAO.class)).load();
/*  75 */     log.info(this.dropList.getSize() + " npc drops loaded");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropList getDropList() {
/*  83 */     return this.dropList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerDrop(Npc npc, Player player, int lvl) {
/*  94 */     if (player == null) {
/*     */       return;
/*     */     }
/*  97 */     int npcUniqueId = npc.getObjectId();
/*  98 */     int npcTemplateId = npc.getObjectTemplate().getTemplateId();
/*     */     
/* 100 */     Set<DropItem> droppedItems = new HashSet<DropItem>();
/* 101 */     Set<DropTemplate> templates = this.dropList.getDropsFor(npcTemplateId);
/*     */     
/* 103 */     int index = 1;
/*     */     
/* 105 */     int dropPercentage = 100;
/* 106 */     if (!CustomConfig.DISABLE_DROP_REDUCTION) {
/* 107 */       dropPercentage = DropRewardEnum.dropRewardFrom(npc.getLevel() - lvl);
/*     */     }
/* 109 */     float dropRate = (player.getRates().getDropRate() * dropPercentage) / 100.0F;
/*     */     
/* 111 */     if (templates != null)
/*     */     {
/* 113 */       for (DropTemplate dropTemplate : templates) {
/*     */         
/* 115 */         DropItem dropItem = new DropItem(dropTemplate);
/* 116 */         dropItem.calculateCount(dropRate);
/*     */         
/* 118 */         if (dropItem.getCount() > 0L) {
/*     */           
/* 120 */           dropItem.setIndex(index++);
/* 121 */           droppedItems.add(dropItem);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 126 */     QuestService.getQuestDrop(droppedItems, index, npc, player);
/* 127 */     this.currentDropMap.put(Integer.valueOf(npcUniqueId), droppedItems);
/*     */     
/* 129 */     List<Player> dropPlayers = new ArrayList<Player>();
/* 130 */     if (player.isInAlliance()) {
/*     */ 
/*     */       
/* 133 */       List<Integer> dropMembers = new ArrayList<Integer>();
/* 134 */       for (PlayerAllianceMember member : player.getPlayerAlliance().getMembers()) {
/*     */         
/* 136 */         dropMembers.add(Integer.valueOf(member.getObjectId()));
/* 137 */         dropPlayers.add(member.getPlayer());
/*     */       } 
/* 139 */       this.dropRegistrationMap.put(Integer.valueOf(npcUniqueId), new DropNpc(dropMembers));
/* 140 */       ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcUniqueId))).setGroupSize(player.getPlayerAlliance().size());
/*     */     }
/* 142 */     else if (player.isInGroup()) {
/*     */       
/* 144 */       this.dropRegistrationMap.put(Integer.valueOf(npcUniqueId), new DropNpc(GroupService.getInstance().getMembersToRegistrateByRules(player, player.getPlayerGroup(), npc)));
/*     */ 
/*     */       
/* 147 */       DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcUniqueId));
/* 148 */       dropNpc.setInRangePlayers(GroupService.getInstance().getInRangePlayers());
/* 149 */       dropNpc.setGroupSize(dropNpc.getInRangePlayers().size());
/* 150 */       for (Player member : player.getPlayerGroup().getMembers()) {
/*     */         
/* 152 */         if (dropNpc.containsKey(member.getObjectId())) {
/* 153 */           dropPlayers.add(member);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 158 */       List<Integer> singlePlayer = new ArrayList<Integer>();
/* 159 */       singlePlayer.add(Integer.valueOf(player.getObjectId()));
/* 160 */       dropPlayers.add(player);
/* 161 */       this.dropRegistrationMap.put(Integer.valueOf(npcUniqueId), new DropNpc(singlePlayer));
/*     */     } 
/*     */     
/* 164 */     for (Player p : dropPlayers)
/*     */     {
/* 166 */       PacketSendUtility.sendPacket(p, (AionServerPacket)new SM_LOOT_STATUS(npcUniqueId, 0));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterDrop(Npc npc) {
/* 177 */     int npcUniqueId = npc.getObjectId();
/* 178 */     this.currentDropMap.remove(Integer.valueOf(npcUniqueId));
/* 179 */     if (this.dropRegistrationMap.containsKey(Integer.valueOf(npcUniqueId)))
/*     */     {
/* 181 */       this.dropRegistrationMap.remove(Integer.valueOf(npcUniqueId));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestDropList(Player player, int npcId) {
/* 193 */     if (player == null || !this.dropRegistrationMap.containsKey(Integer.valueOf(npcId))) {
/*     */       return;
/*     */     }
/* 196 */     DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
/* 197 */     if (!dropNpc.containsKey(player.getObjectId())) {
/*     */       
/* 199 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_LOOT_NO_RIGHT());
/*     */       
/*     */       return;
/*     */     } 
/* 203 */     if (dropNpc.isBeingLooted()) {
/*     */       
/* 205 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_LOOT_FAIL_ONLOOTING());
/*     */       
/*     */       return;
/*     */     } 
/* 209 */     dropNpc.setBeingLooted(player);
/*     */     
/* 211 */     Set<DropItem> dropItems = this.currentDropMap.get(Integer.valueOf(npcId));
/*     */     
/* 213 */     if (dropItems == null)
/*     */     {
/* 215 */       dropItems = Collections.emptySet();
/*     */     }
/*     */     
/* 218 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LOOT_ITEMLIST(npcId, dropItems, player));
/*     */     
/* 220 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LOOT_STATUS(npcId, 2));
/* 221 */     player.unsetState(CreatureState.ACTIVE);
/* 222 */     player.setState(CreatureState.LOOTING);
/* 223 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, npcId), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestDropList(Player player, int npcId, boolean close) {
/* 234 */     if (!this.dropRegistrationMap.containsKey(Integer.valueOf(npcId))) {
/*     */       return;
/*     */     }
/* 237 */     DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
/* 238 */     dropNpc.setBeingLooted(null);
/*     */     
/* 240 */     player.unsetState(CreatureState.LOOTING);
/* 241 */     player.setState(CreatureState.ACTIVE);
/* 242 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.END_LOOT, 0, npcId), true);
/*     */     
/* 244 */     Set<DropItem> dropItems = this.currentDropMap.get(Integer.valueOf(npcId));
/* 245 */     Npc npc = (Npc)World.getInstance().findAionObject(npcId);
/* 246 */     if (npc != null) {
/*     */       
/* 248 */       if (dropItems == null || dropItems.size() == 0) {
/*     */         
/* 250 */         npc.getController().onDespawn(true);
/*     */         
/*     */         return;
/*     */       } 
/* 254 */       PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_LOOT_STATUS(npcId, 0));
/* 255 */       dropNpc.setFreeLooting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestDropItem(Player player, int npcId, int itemIndex) {
/* 261 */     Set<DropItem> dropItems = this.currentDropMap.get(Integer.valueOf(npcId));
/*     */     
/* 263 */     DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
/*     */ 
/*     */     
/* 266 */     if (dropItems == null || dropNpc == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     DropItem requestedItem = null;
/*     */     
/* 275 */     synchronized (dropItems) {
/*     */       
/* 277 */       for (DropItem dropItem : dropItems) {
/*     */         
/* 279 */         if (dropItem.getIndex() == itemIndex) {
/*     */           
/* 281 */           requestedItem = dropItem;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 287 */     if (requestedItem != null) {
/*     */       
/* 289 */       int itemId = requestedItem.getDropTemplate().getItemId();
/*     */       
/* 291 */       ItemQuality quality = DataManager.ITEM_DATA.getItemTemplate(itemId).getItemQuality();
/*     */       
/* 293 */       if (!requestedItem.isDistributeItem() && !requestedItem.isFreeForAll()) {
/*     */         
/* 295 */         if (player.isInGroup()) {
/*     */           
/* 297 */           if (dropNpc.getGroupSize() > 1) {
/* 298 */             dropNpc.setDistributionType(player.getPlayerGroup().getLootGroupRules().getQualityRule(quality));
/*     */           } else {
/* 300 */             dropNpc.setDistributionType(0);
/*     */           } 
/* 302 */           if (dropNpc.getDistributionType() > 1 && !dropNpc.isInUse()) {
/*     */             
/* 304 */             dropNpc.setCurrentIndex(itemIndex);
/* 305 */             for (Player member : dropNpc.getInRangePlayers()) {
/*     */               
/* 307 */               if (member.isOnline()) {
/*     */                 
/* 309 */                 dropNpc.addPlayerStatus(member);
/* 310 */                 PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_LOOT(member.getPlayerGroup().getGroupId(), itemId, npcId, dropNpc.getDistributionType()));
/*     */               } 
/*     */             } 
/*     */             
/* 314 */             dropNpc.isInUse(true);
/*     */           } 
/*     */         } 
/* 317 */         if (player.isInAlliance()) {
/*     */           
/* 319 */           dropNpc.setDistributionType(0);
/*     */           
/* 321 */           if (dropNpc.getDistributionType() > 1 && !dropNpc.isInUse()) {
/*     */             
/* 323 */             dropNpc.setCurrentIndex(itemIndex);
/* 324 */             for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
/*     */               
/* 326 */               Player member = allianceMember.getPlayer();
/* 327 */               if (member.isOnline()) {
/*     */                 
/* 329 */                 dropNpc.addPlayerStatus(member);
/* 330 */                 PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_LOOT(member.getPlayerAlliance().getPlayerAllianceGroupForMember(member.getObjectId()).getAllianceId(), itemId, npcId, dropNpc.getDistributionType()));
/*     */               } 
/*     */             } 
/*     */             
/* 334 */             dropNpc.isInUse(true);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 341 */       if ((!player.isInGroup() && !player.isInAlliance()) || dropNpc.getDistributionType() == 0 || requestedItem.isFreeForAll())
/*     */       {
/*     */         
/* 344 */         if (ItemService.addItem(player, itemId, requestedItem.getCount())) {
/* 345 */           requestedItem.setCount(0L);
/*     */         }
/*     */       }
/*     */       
/* 349 */       if (requestedItem.isDistributeItem()) {
/*     */         
/* 351 */         dropNpc.isInUse(false);
/*     */         
/* 353 */         if (player != requestedItem.getWinningPlayer() && requestedItem.isItemWonNotCollected()) {
/*     */           
/* 355 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_LOOT_ANOTHER_OWNER_ITEM());
/*     */           return;
/*     */         } 
/* 358 */         if (requestedItem.getWinningPlayer().getInventory().isFull()) {
/*     */           
/* 360 */           PacketSendUtility.sendPacket(requestedItem.getWinningPlayer(), (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_INVEN_ERROR);
/* 361 */           requestedItem.isItemWonNotCollected(true);
/*     */           
/*     */           return;
/*     */         } 
/* 365 */         if (ItemService.addItem(requestedItem.getWinningPlayer(), itemId, requestedItem.getCount())) {
/* 366 */           requestedItem.setCount(0L);
/*     */         }
/* 368 */         switch (dropNpc.getDistributionType()) {
/*     */           
/*     */           case 2:
/* 371 */             winningRollActions(requestedItem.getWinningPlayer(), itemId, npcId);
/*     */             break;
/*     */           case 3:
/* 374 */             winningBidActions(requestedItem.getWinningPlayer(), itemId, npcId, requestedItem.getHighestValue());
/*     */             break;
/*     */         } 
/*     */       } 
/* 378 */       if (requestedItem.getCount() == 0L)
/*     */       {
/* 380 */         dropItems.remove(requestedItem);
/*     */       }
/*     */ 
/*     */       
/* 384 */       resendDropList(dropNpc.getBeingLooted(), npcId, dropItems);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void resendDropList(Player player, int npcId, Set<DropItem> dropItems) {
/* 390 */     if (dropItems.size() != 0) {
/*     */       
/* 392 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LOOT_ITEMLIST(npcId, dropItems, player));
/*     */     }
/*     */     else {
/*     */       
/* 396 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LOOT_STATUS(npcId, 3));
/* 397 */       player.unsetState(CreatureState.LOOTING);
/* 398 */       player.setState(CreatureState.ACTIVE);
/* 399 */       PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.END_LOOT, 0, npcId), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleRoll(Player player, int roll, int itemId, int npcId) {
/*     */     int luck;
/* 408 */     switch (roll) {
/*     */       
/*     */       case 0:
/* 411 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_GIVEUP_ME());
/* 412 */         if (player.isInGroup())
/*     */         {
/* 414 */           for (Player member : ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcId))).getInRangePlayers()) {
/*     */             
/* 416 */             if (!player.equals(member))
/* 417 */               PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_GIVEUP_OTHER(player.getName())); 
/*     */           } 
/*     */         }
/* 420 */         if (player.isInAlliance())
/*     */         {
/* 422 */           for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
/*     */             
/* 424 */             Player member = allianceMember.getPlayer();
/* 425 */             if (!player.equals(member))
/* 426 */               PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_GIVEUP_OTHER(player.getName())); 
/*     */           } 
/*     */         }
/* 429 */         distributeLoot(player, 0L, itemId, npcId);
/*     */         break;
/*     */       case 1:
/* 432 */         luck = Rnd.get(1, 100);
/* 433 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_RESULT_ME(luck));
/* 434 */         if (player.isInGroup())
/*     */         {
/* 436 */           for (Player member : ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcId))).getInRangePlayers()) {
/*     */             
/* 438 */             if (!player.equals(member))
/* 439 */               PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_RESULT_OTHER(player.getName(), luck)); 
/*     */           } 
/*     */         }
/* 442 */         if (player.isInAlliance())
/*     */         {
/* 444 */           for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
/*     */             
/* 446 */             Player member = allianceMember.getPlayer();
/* 447 */             if (!player.equals(member))
/* 448 */               PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_RESULT_OTHER(player.getName(), luck)); 
/*     */           } 
/*     */         }
/* 451 */         distributeLoot(player, luck, itemId, npcId);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleBid(Player player, long bid, int itemId, int npcId) {
/* 461 */     long kinahAmount = player.getInventory().getKinahItem().getItemCount();
/* 462 */     if (bid > 0L) {
/*     */       
/* 464 */       if (kinahAmount < bid)
/*     */       {
/* 466 */         bid = 0L;
/*     */       }
/* 468 */       distributeLoot(player, bid, itemId, npcId);
/*     */     } else {
/*     */       
/* 471 */       distributeLoot(player, 0L, itemId, npcId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void distributeLoot(Player player, long luckyPlayer, int itemId, int npcId) {
/* 479 */     DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
/*     */     
/* 481 */     Set<DropItem> dropItems = this.currentDropMap.get(Integer.valueOf(npcId));
/* 482 */     DropItem requestedItem = null;
/*     */     
/* 484 */     synchronized (dropItems) {
/*     */       
/* 486 */       for (DropItem dropItem : dropItems) {
/*     */         
/* 488 */         if (dropItem.getIndex() == dropNpc.getCurrentIndex()) {
/*     */           
/* 490 */           requestedItem = dropItem;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 497 */     if (dropNpc.containsPlayerStatus(player))
/*     */     {
/* 499 */       dropNpc.delPlayerStatus(player);
/*     */     }
/*     */     
/* 502 */     if (luckyPlayer > requestedItem.getHighestValue()) {
/*     */       
/* 504 */       requestedItem.setHighestValue(luckyPlayer);
/* 505 */       requestedItem.setWinningPlayer(player);
/*     */     } 
/*     */     
/* 508 */     if (dropNpc.getPlayerStatus().size() != 0) {
/*     */       return;
/*     */     }
/*     */     
/* 512 */     if (requestedItem.getWinningPlayer() == null) {
/*     */       
/* 514 */       requestedItem.isFreeForAll(true);
/* 515 */       ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcId))).isInUse(false);
/*     */       return;
/*     */     } 
/* 518 */     requestedItem.isDistributeItem(true);
/* 519 */     requestDropItem(player, npcId, dropNpc.getCurrentIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void winningRollActions(Player player, int itemId, int npcId) {
/* 527 */     int nameId = DataManager.ITEM_DATA.getItemTemplate(itemId).getNameId();
/* 528 */     PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_LOOT_GET_ITEM_ME(new DescriptionId(nameId)));
/*     */     
/* 530 */     if (player.isInGroup())
/*     */     {
/* 532 */       for (Player member : ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcId))).getInRangePlayers()) {
/*     */         
/* 534 */         if (!player.equals(member)) {
/* 535 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_LOOT_GET_ITEM_OTHER(player.getName(), new DescriptionId(nameId)));
/*     */         }
/*     */       } 
/*     */     }
/* 539 */     if (player.isInAlliance())
/*     */     {
/* 541 */       for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
/*     */         
/* 543 */         Player member = allianceMember.getPlayer();
/* 544 */         if (!player.equals(member)) {
/* 545 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_LOOT_GET_ITEM_OTHER(player.getName(), new DescriptionId(nameId)));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void winningBidActions(Player player, int itemId, int npcId, long highestValue) {
/* 557 */     DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
/*     */     
/* 559 */     PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_ACCOUNT_ME(highestValue));
/* 560 */     ItemService.decreaseKinah(player, highestValue);
/*     */     
/* 562 */     if (player.isInGroup())
/*     */     {
/* 564 */       for (Player member : dropNpc.getInRangePlayers()) {
/*     */         
/* 566 */         if (!player.equals(member)) {
/*     */           
/* 568 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_ACCOUNT_OTHER(player.getName(), highestValue));
/* 569 */           long distributeKinah = highestValue / (dropNpc.getGroupSize() - 1);
/* 570 */           ItemService.increaseKinah(member, distributeKinah);
/* 571 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_DISTRIBUTE(highestValue, dropNpc.getGroupSize() - 1, distributeKinah));
/*     */         } 
/*     */       } 
/*     */     }
/* 575 */     if (player.isInAlliance())
/*     */     {
/* 577 */       for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
/*     */         
/* 579 */         Player member = allianceMember.getPlayer();
/* 580 */         if (!player.equals(member)) {
/*     */           
/* 582 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_ACCOUNT_OTHER(player.getName(), highestValue));
/* 583 */           long distributeKinah = highestValue / (dropNpc.getGroupSize() - 1);
/* 584 */           ItemService.increaseKinah(member, distributeKinah);
/* 585 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_DISTRIBUTE(highestValue, dropNpc.getGroupSize() - 1, distributeKinah));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 595 */     protected static final DropService instance = new DropService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\DropService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */