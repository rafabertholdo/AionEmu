/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.StorageType;
/*     */ import com.aionemu.gameserver.model.templates.WarehouseExpandTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_WAREHOUSE_INFO;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.List;
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
/*     */ public class WarehouseService
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(WarehouseService.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_EXPAND = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_EXPAND = 10;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void expandWarehouse(final Player player, Npc npc) {
/*  54 */     WarehouseExpandTemplate expandTemplate = DataManager.WAREHOUSEEXPANDER_DATA.getWarehouseExpandListTemplate(npc.getNpcId());
/*     */ 
/*     */     
/*  57 */     if (expandTemplate == null) {
/*     */       
/*  59 */       log.error("Warehouse Expand Template could not be found for Npc ID: " + npc.getObjectId());
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     if (npcCanExpandLevel(expandTemplate, player.getWarehouseSize() + 1) && validateNewSize(player.getWarehouseSize() + 1))
/*     */     {
/*     */       
/*  66 */       if (validateNewSize(player.getWarehouseSize() + 1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  71 */         final int price = getPriceByLevel(expandTemplate, player.getWarehouseSize() + 1);
/*  72 */         RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)npc)
/*     */           {
/*     */             public void acceptRequest(Creature requester, Player responder)
/*     */             {
/*  76 */               if (!ItemService.decreaseKinah(responder, price)) {
/*     */                 
/*  78 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300831, new Object[0]));
/*     */                 return;
/*     */               } 
/*  81 */               WarehouseService.expand(responder);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void denyRequest(Creature requester, Player responder) {}
/*     */           };
/*  91 */         boolean result = player.getResponseRequester().putRequest(900686, responseHandler);
/*  92 */         if (result)
/*     */         {
/*  94 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(900686, 0, new Object[] { String.valueOf(price) }));
/*     */         }
/*     */       } else {
/*     */         
/*  98 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300430, new Object[0]));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void expand(Player player) {
/* 107 */     if (!validateNewSize(player.getWarehouseSize() + 1))
/*     */       return; 
/* 109 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300433, new Object[] { "8" }));
/* 110 */     player.setWarehouseSize(player.getWarehouseSize() + 1);
/*     */     
/* 112 */     sendWarehouseInfo(player, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean validateNewSize(int level) {
/* 124 */     if (level < 0 || level > 10)
/* 125 */       return false; 
/* 126 */     return true;
/*     */   }
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
/*     */   private static boolean npcCanExpandLevel(WarehouseExpandTemplate clist, int level) {
/* 139 */     if (!clist.contains(level))
/* 140 */       return false; 
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getPriceByLevel(WarehouseExpandTemplate clist, int level) {
/* 153 */     return clist.get(level).getPrice();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendWarehouseInfo(Player player, boolean sendAccountWh) {
/* 164 */     List<Item> items = player.getStorage(StorageType.REGULAR_WAREHOUSE.getId()).getStorageItems();
/*     */     
/* 166 */     int whSize = player.getWarehouseSize();
/* 167 */     int itemsSize = items.size();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     boolean firstPacket = true;
/* 173 */     if (itemsSize != 0) {
/*     */       
/* 175 */       int index = 0;
/*     */       
/* 177 */       while (index + 10 < itemsSize) {
/*     */         
/* 179 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_WAREHOUSE_INFO(items.subList(index, index + 10), StorageType.REGULAR_WAREHOUSE.getId(), whSize, firstPacket));
/*     */         
/* 181 */         index += 10;
/* 182 */         firstPacket = false;
/*     */       } 
/* 184 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_WAREHOUSE_INFO(items.subList(index, itemsSize), StorageType.REGULAR_WAREHOUSE.getId(), whSize, firstPacket));
/*     */     } 
/*     */ 
/*     */     
/* 188 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_WAREHOUSE_INFO(null, StorageType.REGULAR_WAREHOUSE.getId(), whSize, false));
/*     */ 
/*     */     
/* 191 */     if (sendAccountWh)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 196 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_WAREHOUSE_INFO(player.getStorage(StorageType.ACCOUNT_WAREHOUSE.getId()).getAllItems(), StorageType.ACCOUNT_WAREHOUSE.getId(), 0, true));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_WAREHOUSE_INFO(null, StorageType.ACCOUNT_WAREHOUSE.getId(), 0, false));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\WarehouseService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */