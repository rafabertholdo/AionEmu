/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.dataholders.GoodsListData;
/*     */ import com.aionemu.gameserver.dataholders.TradeListData;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import com.aionemu.gameserver.model.templates.TradeListTemplate;
/*     */ import com.aionemu.gameserver.model.templates.goods.GoodsList;
/*     */ import com.aionemu.gameserver.model.trade.TradeItem;
/*     */ import com.aionemu.gameserver.model.trade.TradeList;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class TradeService
/*     */ {
/*  47 */   private static final Logger log = Logger.getLogger(TradeService.class);
/*     */   
/*  49 */   private static final TradeListData tradeListData = DataManager.TRADE_LIST_DATA;
/*  50 */   private static final GoodsListData goodsListData = DataManager.GOODSLIST_DATA;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean performBuyFromShop(Player player, TradeList tradeList) {
/*  61 */     if (!validateBuyItems(tradeList, player)) {
/*     */       
/*  63 */       PacketSendUtility.sendMessage(player, "Some items are not allowed to be sold by this npc.");
/*  64 */       return false;
/*     */     } 
/*     */     
/*  67 */     Storage inventory = player.getInventory();
/*     */     
/*  69 */     Npc npc = (Npc)World.getInstance().findAionObject(tradeList.getSellerObjId());
/*  70 */     int tradeModifier = tradeListData.getTradeListTemplate(npc.getNpcId()).getSellPriceRate();
/*     */ 
/*     */     
/*  73 */     if (!tradeList.calculateBuyListPrice(player, tradeModifier)) {
/*  74 */       return false;
/*     */     }
/*     */     
/*  77 */     int freeSlots = inventory.getLimit() - inventory.getAllItems().size() + 1;
/*  78 */     if (freeSlots < tradeList.size()) {
/*  79 */       return false;
/*     */     }
/*  81 */     long tradeListPrice = tradeList.getRequiredKinah();
/*     */     
/*  83 */     for (TradeItem tradeItem : tradeList.getTradeItems()) {
/*     */       
/*  85 */       if (!ItemService.addItem(player, tradeItem.getItemTemplate().getTemplateId(), tradeItem.getCount())) {
/*     */         
/*  87 */         if (OptionsConfig.LOG_AUDIT) {
/*  88 */           log.warn(String.format("[AUDIT] Itemservice couldnt add all items on buy: %d %d %d %d", new Object[] { Integer.valueOf(player.getObjectId()), Integer.valueOf(tradeItem.getItemTemplate().getTemplateId()), Long.valueOf(tradeItem.getCount()), Long.valueOf(tradeItem.getCount()) }));
/*     */         }
/*  90 */         ItemService.decreaseKinah(player, tradeListPrice);
/*  91 */         return false;
/*     */       } 
/*     */     } 
/*  94 */     ItemService.decreaseKinah(player, tradeListPrice);
/*     */     
/*  96 */     return true;
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
/*     */   public static boolean performBuyFromAbyssShop(Player player, TradeList tradeList) {
/* 109 */     if (!validateBuyItems(tradeList, player)) {
/*     */       
/* 111 */       PacketSendUtility.sendMessage(player, "Some items are not allowed to be selled from this npc");
/* 112 */       return false;
/*     */     } 
/* 114 */     Storage inventory = player.getInventory();
/* 115 */     int freeSlots = inventory.getLimit() - inventory.getAllItems().size() + 1;
/*     */ 
/*     */     
/* 118 */     if (!tradeList.calculateAbyssBuyListPrice(player)) {
/* 119 */       return false;
/*     */     }
/* 121 */     if (tradeList.getRequiredAp() < 0) {
/*     */       
/* 123 */       if (OptionsConfig.LOG_AUDIT)
/* 124 */         log.warn("[AUDIT] Player: " + player.getName() + " posible client hack. tradeList.getRequiredAp() < 0"); 
/* 125 */       return false;
/*     */     } 
/*     */     
/* 128 */     if (freeSlots < tradeList.size()) {
/* 129 */       return false;
/*     */     }
/* 131 */     for (TradeItem tradeItem : tradeList.getTradeItems()) {
/*     */       
/* 133 */       if (!ItemService.addItem(player, tradeItem.getItemTemplate().getTemplateId(), tradeItem.getCount())) {
/*     */         
/* 135 */         if (OptionsConfig.LOG_AUDIT) {
/* 136 */           log.warn(String.format("[AUDIT] Itemservice couldnt add all items on buy: %d %d %d %d", new Object[] { Integer.valueOf(player.getObjectId()), Integer.valueOf(tradeItem.getItemTemplate().getTemplateId()), Long.valueOf(tradeItem.getCount()), Long.valueOf(tradeItem.getCount()) }));
/*     */         }
/* 138 */         player.getCommonData().addAp(-tradeList.getRequiredAp());
/* 139 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     player.getCommonData().addAp(-tradeList.getRequiredAp());
/* 144 */     Map<Integer, Integer> requiredItems = tradeList.getRequiredItems();
/* 145 */     for (Integer itemId : requiredItems.keySet())
/*     */     {
/* 147 */       ItemService.decreaseItemCountByItemId(player, itemId.intValue(), ((Integer)requiredItems.get(itemId)).intValue());
/*     */     }
/*     */ 
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean validateBuyItems(TradeList tradeList, Player player) {
/* 159 */     Npc npc = (Npc)World.getInstance().findAionObject(tradeList.getSellerObjId());
/* 160 */     TradeListTemplate tradeListTemplate = tradeListData.getTradeListTemplate(npc.getObjectTemplate().getTemplateId());
/*     */ 
/*     */     
/* 163 */     Set<Integer> allowedItems = new HashSet<Integer>();
/* 164 */     for (TradeListTemplate.TradeTab tradeTab : tradeListTemplate.getTradeTablist()) {
/*     */       
/* 166 */       GoodsList goodsList = goodsListData.getGoodsListById(tradeTab.getId());
/* 167 */       if (goodsList != null && goodsList.getItemIdList() != null)
/*     */       {
/* 169 */         allowedItems.addAll(goodsList.getItemIdList());
/*     */       }
/*     */     } 
/*     */     
/* 173 */     for (TradeItem tradeItem : tradeList.getTradeItems()) {
/*     */       
/* 175 */       if (tradeItem.getCount() < 1L) {
/*     */         
/* 177 */         if (OptionsConfig.LOG_AUDIT)
/* 178 */           log.warn("[AUDIT] Player: " + player.getName() + " posible client hack. Trade count < 1"); 
/* 179 */         return false;
/*     */       } 
/*     */       
/* 182 */       if (tradeItem.getItemTemplate().getMaxStackCount() < tradeItem.getCount()) {
/*     */         
/* 184 */         if (OptionsConfig.LOG_AUDIT)
/* 185 */           log.warn("[AUDIT] Player: " + player.getName() + " posible client hack. item count > MaxStackCount"); 
/* 186 */         return false;
/*     */       } 
/*     */       
/* 189 */       if (!allowedItems.contains(Integer.valueOf(tradeItem.getItemId()))) {
/*     */         
/* 191 */         if (OptionsConfig.LOG_AUDIT)
/* 192 */           log.warn("[AUDIT] Player: " + player.getName() + " posible client hack. Tade item not in GoodsList"); 
/* 193 */         return false;
/*     */       } 
/*     */     } 
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean performSellToShop(Player player, TradeList tradeList) {
/* 207 */     Storage inventory = player.getInventory();
/*     */     
/* 209 */     long kinahReward = 0L;
/* 210 */     for (TradeItem tradeItem : tradeList.getTradeItems()) {
/*     */       
/* 212 */       Item item = inventory.getItemByObjId(tradeItem.getItemId());
/*     */       
/* 214 */       if (item == null) {
/* 215 */         return false;
/*     */       }
/* 217 */       if (item.getItemCount() - tradeItem.getCount() < 0L) {
/*     */         
/* 219 */         if (OptionsConfig.LOG_AUDIT)
/* 220 */           log.warn("[AUDIT] Trade exploit, sell item count big: " + player.getName()); 
/* 221 */         return false;
/*     */       } 
/* 223 */       if (ItemService.decreaseItemCount(player, item, tradeItem.getCount()) == 0L)
/*     */       {
/*     */         
/* 226 */         kinahReward += item.getItemTemplate().getPrice() * tradeItem.getCount();
/*     */       }
/*     */     } 
/*     */     
/* 230 */     kinahReward = player.getPrices().getKinahForSell(kinahReward);
/* 231 */     ItemService.increaseKinah(player, kinahReward);
/*     */     
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TradeListData getTradeListData() {
/* 241 */     return tradeListData;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\TradeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */